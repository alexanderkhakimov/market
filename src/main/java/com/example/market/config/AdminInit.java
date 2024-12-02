package com.example.market.config;

import com.example.market.model.Role;
import com.example.market.model.User;
import com.example.market.repository.RoleRepository;
import com.example.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminInit implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername("admin").isPresent()) {
            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена: ROLE_ADMIN"));
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail("admin@gmail.com");
            adminUser.setRoles(Collections.singleton(adminRole));
            userRepository.save(adminUser);

        }

    }
}
