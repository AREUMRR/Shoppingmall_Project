package com.example.basic.Repository;

import com.example.basic.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    //주문 상품 찾기
    @Query(value = "SELECT w FROM OrderItemEntity w WHERE w.orderEntity.orderId = :orderId")
    List<OrderItemEntity> findAllByOrderId(@Param("orderId") Integer orderId);

    @Query(value = "SELECT w FROM OrderItemEntity w WHERE w.orderEntity.orderId = :orderId AND w.productEntity.productId = :productId")
    OrderItemEntity findByOrderIdAndProductId(Integer orderId, Integer productId);

    //주문 상품 찾기
    @Query(value = "SELECT w FROM OrderItemEntity w WHERE w.orderEntity.orderId = :orderId")
    OrderItemEntity findByOrderId(Integer orderId);

    @Query(value = "select w FROM OrderItemEntity w WHERE w.orderItemId = :orderItemId AND w.orderEntity.orderId = :orderId")
    OrderItemEntity findByOrderItemIdAndOrderId(Integer orderItemId, Integer orderId);

    @Query(value = "select w FROM OrderItemEntity w WHERE w.orderItemId = :orderItemId AND w.orderEntity.orderId = :orderId")
    OrderItemEntity deleteById(Integer orderItemId, Integer orderId);

}
