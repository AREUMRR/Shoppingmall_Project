/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 구매후기 게시판 댓글 구현
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
public class ReviewcmtDTO {
    //구매후기 댓글 번호
    private Integer reviewcmtId;

    //구매후기 댓글 내용
    @NotBlank(message = "내용은 필수입니다.")
    private String reviewcmtBody;

    //구매후기 댓글 작성자
    private String reviewcmtWriter;

    //추가사항
    //회원 번호
    private Integer memberId;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;
    
    //구매후기 게시판 번호
    private Integer reviewId;

}
