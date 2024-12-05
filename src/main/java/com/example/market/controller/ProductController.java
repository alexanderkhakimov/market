package com.example.market.controller;

import com.example.market.model.Product;
import com.example.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public String getAllProducts(Model model){
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products",products);
            return "/products/products";
    }

    @GetMapping("/users/admin/addProducts")
    public String showAddProductForm(Model model){
        model.addAttribute("product", new Product());
        return "/users/admin/addProducts/addProducts";
    }
    @PostMapping("/users/admin/addProducts")
    public String addProduct(@ModelAttribute("product") Product product){
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/products/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
    @GetMapping("/users/admin/updateProducts/{productId}")
    public String showUpdateProductForm(@PathVariable Long productId, Model model){
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "/users/admin/updateProducts/updateProducts";
    }
    @PostMapping("/users/admin/updateProducts/{productId}")
    public String updateProduct(@PathVariable Long productId,@ModelAttribute("product")Product updateProduct) {
        Product product = productService.getProductById(productId);
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setQuantity(updateProduct.getQuantity());
        productService.saveProduct(product);
        return "redirect:/products";
    }
}
