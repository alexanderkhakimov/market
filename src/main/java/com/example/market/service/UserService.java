package com.example.market.service;

import com.example.market.model.Account;
import com.example.market.model.Role;
import com.example.market.model.User;
import com.example.market.repository.CartRepository;
import com.example.market.repository.RoleRepository;
import com.example.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CartRepository cartRepository;
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

    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Role role = user.getRole();
        if (role != null) {
            return role.getRoleName();
        }
        return "ROLE_USER";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        cartRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    public Long getUserId(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Пользователь с таким именем не существует")).getId();
    }

    public User getUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("Пользователь с таким id не существует"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Double getUserAccountBalance(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Account account = user.getAccount();
        if (account != null) {
            return account.getBalance();
        }
        return 0.0;
    }


}