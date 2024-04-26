/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 상품 구현
확인사항 : 테스트 해야함
 */

package com.example.basic.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    //상품 번호
    private Integer productId;

    //상품 이름
    @NotBlank(message = "상품명은 필수입니다.")
    private String productName;

    //상품 상세 내용
    @NotBlank(message = "상세내용은 필수입니다.")
    private String productContent;

    //상품 이미지명
    private String productImage;

    //상품 가격
    private Integer productPrice;

    //상품 수량(출고량)
    private Integer quantity;

    //상품 입고량
    private Integer quantityIn;

    //상품 재고량
    private Integer quantityCount;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;

    //장바구니 상품 번호
    private Integer cartItemId;

}
