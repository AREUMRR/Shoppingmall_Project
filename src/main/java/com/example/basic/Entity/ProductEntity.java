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
@Table(name = "product")
@SequenceGenerator(name = "product_SEQ", sequenceName = "product_SEQ", initialValue = 1,
        allocationSize = 1)
public class ProductEntity extends BaseEntity{
    //상품 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_SEQ")
    private Integer productId;

    //상품 이름
    @Column(length = 20, nullable = false)
    private String productName;

    //상품 상세 내용
    private String productContent;

    //상품 이미지
    private String productImage;

    //상품 가격
    @Column(nullable = false)
    private Integer productPrice;

    //상품 수량(출고량)
    private Integer quantity;

    //상품 입고량
    private Integer quantityIn;

    //상품 재고량
    private Integer quantityCount;

}
