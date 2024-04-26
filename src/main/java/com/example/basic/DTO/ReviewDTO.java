/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 구매후기 게시판 구현
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
public class ReviewDTO {
    //구매후기 게시판 번호
    private Integer reviewId;

    //구매후기 게시판 제목
    @NotBlank(message = "제목은 필수입니다.")
    private String reviewSubject;

    //구매후기 게시판 내용
    private String reviewContent;

    //구매후기 게시판 이미지
    private String reviewImage;

    //구매후기 게시판 평점
    private Integer reviewScore;

    //구매후기 게시판 조회수
    private Integer reviewCount;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;

    //구매후기 게시판 작성자
    private String reviewWriter;

    //추가사항
    //회원 번호
    private Integer memberId;
}
