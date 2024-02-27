package com.UpperFasster.Magazine.store.repositories;

import com.UpperFasster.Magazine.store.models.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class CartItemRepository{
    @PersistenceContext
    private EntityManager entityManager;

    private final String cartItemTableName = "tbl_cart_items";

    public CartItem findByUserAndProduct(UUID user_id, UUID product_id) {
        return entityManager.createQuery(
                        "SELECT ci FROM :tbl_name ci WHERE ci.user.id = :userId AND ci.product.id = :productId", CartItem.class)
                .setParameter("tbl_name", this.cartItemTableName)
                .setParameter("userId", user_id)
                .setParameter("productId", product_id)
                .getSingleResult();
    }

    public int updateQuantityOfItemInCart(UUID userId, UUID productId, short quantity) {
        return entityManager.createQuery("UPDATE :tbl_name " +
                "SET quantity = quantity + :new_quantity " +
                "WHERE id IN " +
                "(SELECT id " +
                "FROM tbl_cart_items ci " +
                "WHERE (ci.user_id = :user_id) " +
                "AND (ci.product_id = :product_id))")
                .setParameter("tbl_name", this.cartItemTableName)
                .setParameter("new_quantity", quantity)
                .setParameter("user_id", userId)
                .setParameter("product_id", productId)
                .executeUpdate();
    }

    public void insertItemToCart(UUID userId, UUID productId, short quantity) {
        entityManager.createNativeQuery(
                "INSERT INTO :tbl_name (user_id, product_id, quantity) VALUES (?, ?, ?)")
                .setParameter("tbl_name", this.cartItemTableName)
                .setParameter(1, userId.toString())
                .setParameter(2, productId.toString())
                .setParameter(3, quantity)
                .executeUpdate();
    }

    public List<UUID> getProductsInUserCart(UUID userId) {
        return entityManager.createQuery(
                        "SELECT ci.product_id FROM :tbl_name ci WHERE ci.user_id = :userId", UUID.class)
                .setParameter("tbl_name", this.cartItemTableName)
                .setParameter("userId", userId)
                .getResultList();
    }

    public int deleteProductFromCart(UUID userId, UUID productId) {
        return  entityManager.createQuery(
                        "DELETE FROM :tbl_name ci WHERE ci.user_id = :userId AND ci.product_id = :productId")
                .setParameter("tbl_name", this.cartItemTableName)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .executeUpdate();
    }
}
