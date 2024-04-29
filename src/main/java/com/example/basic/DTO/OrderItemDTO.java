/*
작성자 : 정아름
작성일 : 24.04.12
작성내용 : 주문
확인사항 : 테스트 완료
 */

package com.example.basic.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    //주문 상품 번호
    private Integer orderItemId;

    //주문 상품 수량
    private Integer quantity;

    //주문 상품 금액
    private  Integer orderPrice;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;

    //주문 상태
    private boolean orderStatus;

    //추가사항
    //주문 번호
    private Integer orderId;

    //상품 번호
    private Integer productId;

    //상품 이름
    private String productName;

    //상품 이미지
    private String productImage;

    //상품 가격
    private Integer productPrice;

}
