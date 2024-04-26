/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 고객센터(1:1)게시판 구현
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
public class BoardDTO {
    //고객센터 게시판 번호
    private Integer boardId;

    //고객센터 게시판 제목
    @NotBlank(message = "제목은 필수입니다.")
    private String boardSubject;

    //고객센터 게시판 내용
    private String boardContent;

    //고객센터 게시판 이미지
    private String boardImage;

    //고객센터 게시판 작성자
    private String boardWriter;

    //추가사항
    //회원 번호
    private Integer memberId;

    //추가사항
    //비밀글 체크 여부
    private boolean secret;

    //추가사항
    //비밀글 비밀번호
    private String boardPassword;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;
}
