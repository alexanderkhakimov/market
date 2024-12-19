package com.example.market.service;

import com.example.market.model.Cart;
import com.example.market.model.Product;
import com.example.market.model.User;
import com.example.market.repository.CartRepository;
import com.example.market.repository.ProductRepository;
import com.example.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Cart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void addProductToCart(Long userId, Long productId, Integer quantity) {

        User user = userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findProductById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    @Transactional
    public void removeCart(Long productId) {
        //cartRepository.deleteCartById(cartId);
        cartRepository.deleteCartByProductId(productId);
    }

    public void updateQuantity(Long cartId, Integer quantity, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("нет такой карты в корзине"));
        Integer quantityInBD= productRepository.findProductById(productId).orElse(null).getQuantity();
        if (quantity <= quantityInBD) {
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        }else {
            throw new RuntimeException("Недостаточно товара! Осталось: "+ quantityInBD +" штук!");
        }

    }
    public Cart getCartByProductId(Long productId){
        return cartRepository.getCartByProductId(productId);
    }
    public Cart findByUserIdAndProductId(Long userId, Long productId){
        return cartRepository.findByUserIdAndProductId(userId,productId);
    }

}
