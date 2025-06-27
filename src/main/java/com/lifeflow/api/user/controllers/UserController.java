package com.lifeflow.api.user.controllers;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lifeflow.api.user.dtos.CreateUserRequest;
import com.lifeflow.api.user.dtos.UpdateUserRequest;
import com.lifeflow.api.user.dtos.UserDetailsDto;
import com.lifeflow.api.user.enums.Role;
import com.lifeflow.api.user.mappers.UserMapper;
import com.lifeflow.api.user.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public Iterable<UserDetailsDto> getAllAUsers(
        @RequestHeader(name = "x-auth-token", required = false) String authToken,
        @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        System.out.println(authToken);
        if(!Set.of("name","email").contains(sort)) {
            sort = "id";
        }
        return userRepository
            .findAll(Sort.by(sort))
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "User Not found",
                "status", 404,
                "timestamp", Instant.now()
            ));
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<?> createUser(
        @Valid @RequestBody CreateUserRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Email already in use"));
        }

        if ((request.getRole() == Role.ROLE_ADMIN || request.getRole() == Role.ROLE_SUPER_ADMIN) && !currentUserIsAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Only admins can assign admin roles"));
        }

        var userData = userMapper.toEntity(request);
        userData.setPassword(passwordEncoder.encode(request.getPassword()));

        // 🟢 Assign role from the request
        userData.setRoles(Set.of(request.getRole()));

        var user = userRepository.save(userData);
        var userDto = userMapper.toDto(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    private boolean currentUserIsAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_SUPER_ADMIN"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
        @RequestBody UpdateUserRequest request,
        UriComponentsBuilder uriBuilder,
        @PathVariable Long id
    ) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "User Not found",
                "status", 404,
                "timestamp", Instant.now()
            ));
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }


}
