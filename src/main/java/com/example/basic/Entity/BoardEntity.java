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
@Table(name = "board")
@SequenceGenerator(name = "board_SEQ", sequenceName = "board_SEQ", initialValue = 1,
        allocationSize = 1)
public class BoardEntity extends BaseEntity{
    //고객센터 게시판 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_SEQ")
    private Integer boardId;

    //고객센터 제목
    @Column(nullable = false, length = 20)
    private String boardSubject;

    //고객센터 내용
    @Column(length = 100)
    private String boardContent;

    //고객센터 이미지
    private String boardImage;

    //고객센터  작성자
    private String boardWriter;

    //추가사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    //추가사항
    //비밀글 체크 여부
    private boolean secret;

    //추가사항
    //비밀글 비밀번호
    private String boardPassword;
}
