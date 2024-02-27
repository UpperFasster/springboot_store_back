package com.UpperFasster.Magazine.store.services;

import com.UpperFasster.Magazine.store.repositories.CartItemRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    @Autowired
    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void addItemToCart(UUID userId, UUID productId, short quantity) {
        if (quantity < 1 || quantity > 5) {
            throw new RuntimeException("Too many items in cart. Allows only less or equal 5");
        }
        try {
            cartItemRepository.insertItemToCart(userId, productId, quantity);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof EntityExistsException) {
                throw new RuntimeException("Item already exists in the cart.");
            } else if (e.getCause() instanceof EntityNotFoundException) {
                throw new RuntimeException("That item didn't exists");
            }
        }
    }

    public List<UUID> getAllProductsInCartForUser(UUID user_id) {
        return cartItemRepository.getProductsInUserCart(user_id);
    }

    public void deleteItemFromCart(UUID user_id, UUID product_id) {
        int deleteItemCount = cartItemRepository.deleteProductFromCart(user_id, product_id);
        if (deleteItemCount == 0 || deleteItemCount < 0) {
            throw new RuntimeException("That item didn't exists");
        }
    }

    public void updateQuantityToCartItem(UUID userId, UUID productId, short quantity) {
        if (quantity > 5 || quantity < 1) {
            throw new RuntimeException("Too many items in cart. Allows only less or equal 5");
        }
        int updatedColumns = cartItemRepository.updateQuantityOfItemInCart(userId, productId, quantity);
        if (updatedColumns > 0) {
            // todo make return successful update
        }
    }

}
