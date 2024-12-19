package com.example.market.controller;

import com.example.market.model.Cart;
import com.example.market.service.CartService;
import com.example.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/showCartForm")
    public String showCartsForm(Model model) {
        model.addAttribute("cart", new Cart());
        return "/carts/carts";
    }

    @PostMapping("/addCart")
    public String addCart(@RequestParam Long productId, @RequestParam Integer quantity, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            // Получаем userId по username
            Long userId = userService.getUserId(username);
            Cart cart = cartService.findByUserIdAndProductId(userId, productId);
            if (cart != null) {
                updateQuantity(cart.getId(), cart.getQuantity() + quantity, productId, redirectAttributes);
                redirectAttributes.addFlashAttribute("successMessage", "Товар успешно добавлен в корзину!");
            } else {
                cartService.addProductToCart(userId, productId, quantity);
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/getAllCarts")
    public String getAllCarts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Получаем userId по username
        Long userId = userService.getUserId(username);
        List<Cart> carts = cartService.getCartItemsByUserId(userId);
        model.addAttribute("carts", carts);
        return "/carts/carts";
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam Long cartId, @RequestParam Integer quantity, @RequestParam Long productId,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.updateQuantity(cartId, quantity, productId);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/carts/getAllCarts";
        }

        return "redirect:/carts/getAllCarts";
    }

    @PostMapping("/removeCart")
    public String removeCart(@RequestParam Long productId) {
        cartService.removeCart(productId);
        return "redirect:/carts/getAllCarts";
    }

}
