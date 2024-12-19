package com.example.market.controller;


import com.example.market.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String showProfile(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("balance",userService.getUserAccountBalance(auth.getName()));


        // Проверяем, было ли уже приветствие
        Boolean greeted = (Boolean) session.getAttribute("greeted");
        System.out.println("Session ID: " + session.getId());
        System.out.println("Greeted: " + greeted);
        if (greeted == null || !greeted) {
            model.addAttribute("showGreeting", true); // Показываем приветствие
            session.setAttribute("greeted", true); // Устанавливаем флаг, что приветствие было показано
            System.out.println("Приветствие показано");
        } else {
            model.addAttribute("showGreeting", false); // Не показываем приветствие
            System.out.println("Приветствие не показано");
        }

        String role = userService.getUserRole(auth.getName());
        switch (role) {
            case "ROLE_ADMIN":
                return "redirect:/users/admin/adminProf";
            case "ROLE_USER":
                return "/users/user/profile";
        }

        return "/profile";
    }
}
