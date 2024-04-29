package com.example.basic.Repository;

import com.example.basic.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {

    //장바구니 상품 찾기
    @Query(value = "SELECT w FROM CartItemEntity w WHERE w.cartEntity.cartId = :cartId")
    List<CartItemEntity> findAllByCartId(@Param("cartId") Integer cartId);

    @Query(value = "SELECT w FROM CartItemEntity w WHERE w.cartEntity.cartId = :cartId AND w.productEntity.productId = :productId")
    CartItemEntity findByCartIdAndProductId(Integer cartId, Integer productId);

    //장바구니 상품 찾기
    @Query(value = "SELECT w FROM CartItemEntity w WHERE w.cartEntity.cartId = :cartId")
    CartItemEntity findByCartId(Integer cartId);

    @Query(value = "select w FROM CartItemEntity w WHERE w.cartItemId = :cartItemId AND w.cartEntity.cartId = :cartId")
    CartItemEntity findByCartItemIdAndCartId(Integer cartItemId, Integer cartId);

    @Query(value = "select w FROM CartItemEntity w WHERE w.cartItemId = :cartItemId AND w.cartEntity.cartId = :cartId")
    CartItemEntity deleteById(Integer cartItemId, Integer cartId);

}
