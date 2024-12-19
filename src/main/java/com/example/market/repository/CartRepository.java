package com.example.market.repository;

import com.example.market.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);

    Cart findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);
    void deleteCartByProductId(Long productId);

    void deleteCartById(Long cartId);
    Cart getCartByProductId(Long productId);

}
