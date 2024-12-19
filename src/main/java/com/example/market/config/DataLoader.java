package com.example.market.config;

//import com.example.market.model.Account;
import com.example.market.model.Role;
import com.example.market.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
        }

    }
}
