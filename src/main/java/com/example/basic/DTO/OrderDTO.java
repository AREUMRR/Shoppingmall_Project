/*
작성자 : 정아름
작성일 : 24.04.12
작성내용 : 주문 구현
확인사항 :
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
public class OrderDTO {
    //주문 번호
    private Integer orderId;

    //회원 번호
    private Integer memberId;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;


    //추가사항
    //주문 상품 번호
    private Integer orderItemId;
}
