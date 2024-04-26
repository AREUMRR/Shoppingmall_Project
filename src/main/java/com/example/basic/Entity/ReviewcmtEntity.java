package com.example.basic.Entity;

/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 :
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
@Table(name = "reviewcmt")
@SequenceGenerator(name = "reviewcmt_SEQ", sequenceName = "reviewcmt_SEQ", initialValue = 1,
        allocationSize = 1)
public class ReviewcmtEntity extends BaseEntity{
    //구매후기 댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewcmt_SEQ")
    private Integer reviewcmtId;

    //구매후기 댓글 내용
    @Column(nullable = false, length = 100)
    private String reviewcmtBody;

    //구매후기 댓글 작성자
    private String reviewcmtWriter;

    //구매후기 게시판 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reveiw_id")
    private ReviewEntity reviewEntity;

    //추가사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;
}
