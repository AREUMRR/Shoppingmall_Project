/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 장바구니 구현
확인사항 : 테스트 해야함, 변수 추가 사항 확인
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
public class CartDTO {
    //장바구니 번호
    private Integer cartId;

    //회원 번호
    private Integer memberId;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;


    //추가사항
    //장바구니 상품 번호
    private Integer cartItemId;
}
