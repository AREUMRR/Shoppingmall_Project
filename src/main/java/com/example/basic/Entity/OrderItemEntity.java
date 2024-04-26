package com.example.basic.Entity;

/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 :
 */

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orderitem")
@SequenceGenerator(name = "orderitem_SEQ", sequenceName = "orderitem_SEQ", initialValue = 1,
        allocationSize = 1)
public class OrderItemEntity extends BaseEntity {
    //장바구니 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_SEQ")
    private Integer orderItemId;

    //외래키 정의
    //장바구니상품 테이블은 장바구니 테이블과 연관되어 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    //외래키 정의
    //장바구니상품 테이블은 상품 테이블과 연관되어 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    //장바구니 상품 수량
    private Integer quantity;

    //주문 상품 금액
    private  Integer orderPrice;

    //주문 상태
    private boolean orderStatus;

}
