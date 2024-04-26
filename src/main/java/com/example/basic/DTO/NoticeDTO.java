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
public class NoticeDTO {
    //공지사항 게시판 번호
    private Integer noticeId;

    //공지사항 게시판 제목
    @NotBlank(message = "제목은 필수입니다.")
    private String noticeSubject;

    //공지사항 게시판 내용
    private String noticeContent;
    
    //공지사항 게시판 작성자
    private String noticeWriter;

    //공지사항 게시판 조회수
    private Integer noticeCount;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;
}
