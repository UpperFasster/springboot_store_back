package com.UpperFasster.Magazine.store.controllers;

import com.UpperFasster.Magazine.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestParam UUID userId,
                                                @RequestParam UUID productId,
                                                @RequestParam short quantity) {
        try {
            cartService.addItemToCart(userId, productId, quantity);
            return ResponseEntity.ok("Item added to cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<UUID>> getAllProductsInCartForUser(@RequestParam UUID userId) {
        List<UUID> productsInCart = cartService.getAllProductsInCartForUser(userId);
        return ResponseEntity.ok(productsInCart);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItemFromCart(@RequestParam UUID userId,
                                                     @RequestParam UUID productId) {
        try {
            cartService.deleteItemFromCart(userId, productId);
            return ResponseEntity.ok("Item deleted from cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuantityToCartItem(@RequestParam UUID userId,
                                                           @RequestParam UUID productId,
                                                           @RequestParam short quantity) {
        try {
            cartService.updateQuantityToCartItem(userId, productId, quantity);
            return ResponseEntity.ok("Quantity updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
