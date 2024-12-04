package com.example.market.service;

import com.example.market.model.Role;
import com.example.market.model.User;
import com.example.market.repository.RoleRepository;
import com.example.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user, List<String> roleNames) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        Optional<Role> roleOptional = roleRepository.findByRoleName("ROLE_USER");
        Role role = roleOptional.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName(user.getRole().getRoleName());
            return roleRepository.save(newRole);
        });
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByUserEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }
    public String getUserRole(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Role role = user.getRole();
        if (role!=null) {
            return role.getRoleName();
        }
        return "ROLE_USER";
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}