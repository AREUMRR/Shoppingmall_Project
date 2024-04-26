package com.example.basic.Entity;

/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 : 테이블 생성되고 테스트까지 완료했는데 왜 오류 떠있는지 모르겠음
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
@Table(name = "notice")
@SequenceGenerator(name = "notice_SEQ", sequenceName = "notice_SEQ", initialValue = 1,
        allocationSize = 1)
public class NoticeEntity extends BaseEntity{
    //고객센터 게시판 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_SEQ")
    //공지사항 게시판 번호
    private Integer noticeId;

    //공지사항 게시판 제목
    @Column(nullable = false, length = 20)
    private String noticeSubject;

    //공지사항 게시판 내용
    private String noticeContent;

    //공지사항 게시판 작성자
    private String noticeWriter;

    //공지사항 게시판 조회수
    private Integer noticeCount;
}
