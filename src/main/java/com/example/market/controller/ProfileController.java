package com.example.market.controller;


import com.example.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());

        String role = userService.getUserRole(auth.getName());
        System.out.println(role);

        switch (role) {
            case "ROLE_ADMIN":
                System.out.println("admin1");
                return "redirect:/users/admin/adminProf";
            case "ROLE_USER":
                System.out.println("user");
                return "/users/user/profile";
        }

        return "/profile";
    }
}
