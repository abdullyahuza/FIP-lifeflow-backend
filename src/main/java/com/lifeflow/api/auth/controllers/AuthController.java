package com.lifeflow.api.auth.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lifeflow.api.auth.dtos.LoginRequest;
import com.lifeflow.api.auth.dtos.LoginResponse;
import com.lifeflow.api.auth.dtos.RegisterUserRequest;
import com.lifeflow.api.common.security.JwtService;
import com.lifeflow.api.user.entities.UserEntity;
import com.lifeflow.api.user.mappers.UserMapper;
import com.lifeflow.api.user.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @Valid @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Email already in use"));

        }

        UserEntity user = userMapper.toRegEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity savedUser = userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toDto(savedUser));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(request.getEmail());
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
        }

        UserEntity user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
        }

        String accessToken = jwtService.generateToken(user.getEmail());
        String tokenType = "Bearer";

        LoginResponse response = new LoginResponse(
            accessToken,
            tokenType,
            user.getName(),
            user.getEmail()
        );

        return ResponseEntity.ok(response);
    }


}

