package com.lifeflow.api.common.bootstrap;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lifeflow.api.common.config.SuperAdminProperties;
import com.lifeflow.api.user.entities.UserEntity;
import com.lifeflow.api.user.enums.Role;
import com.lifeflow.api.user.repositories.UserRepository;

@Component
public class SuperAdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SuperAdminProperties props;

    public SuperAdminSeeder(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        SuperAdminProperties props
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.props = props;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(props.getEmail()).isEmpty()) {
            UserEntity superadmin = UserEntity.builder()
                .name(props.getName())
                .email(props.getEmail())
                .phone(props.getPhone())
                .password(passwordEncoder.encode(props.getPassword()))
                .role("SUPER_ADMIN")
                .roles(Set.of(Role.ROLE_SUPER_ADMIN))
                .build();

            userRepository.save(superadmin);
            System.out.println("✅ Superadmin seeded: " + props.getEmail());
        }
    }
}

