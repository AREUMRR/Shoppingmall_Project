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
@Table(name = "boardcmt")
@SequenceGenerator(name = "boardcmt_SEQ", sequenceName = "boardcmt_SEQ", initialValue = 1,
        allocationSize = 1)
public class BoardcmtEntity extends BaseEntity{
    //고객센터 댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardcmt_SEQ")
    private Integer boardcmtId;

    //고객센터 댓글 내용
    @Column(nullable = false, length = 100)
    private String boardcmtBody;

    //고객센터 댓글 작성자
    private String boardcmtWriter;

    //고객센터 게시판 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;
}
