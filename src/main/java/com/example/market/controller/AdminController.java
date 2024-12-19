package com.example.market.controller;

import com.example.market.model.Account;
import com.example.market.model.User;
import com.example.market.service.AccountService;
import com.example.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/adminProf")
    public String showAdminProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "/users/admin/adminProf";
    }

    @PostMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/users/admin/adminProf";
    }

    @PostMapping("/addBalanceUser/{userId}")
    public String addBalanceUser(@PathVariable Long userId) {
        System.out.println("==================1");
        User user = userService.getUserById(userId);
        if (user.getAccount() == null) {
            System.out.println("==================2");
            Account account = new Account();
            account.setBalance(1000.0);
            account.setUser(user);
            System.out.println("==================3");
            accountService.saveAccount(account);
            System.out.println("==================4");
            user.setAccount(account);
            userService.saveUser(user);
            System.out.println("==================5");
        } else {
            user.getAccount().setBalance(user.getAccount().getBalance() + 1000.0);
            accountService.saveAccount(user.getAccount());
        }

        return "redirect:/users/admin/adminProf";
    }

}
