package com.UpperFasster.Magazine.store.repositories;

import com.UpperFasster.Magazine.store.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemJpaRepository extends JpaRepository<CartItem, UUID> {
}
