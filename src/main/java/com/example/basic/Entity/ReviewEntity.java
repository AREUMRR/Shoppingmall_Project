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
@Table(name = "review")
@SequenceGenerator(name = "review_SEQ", sequenceName = "review_SEQ",
        allocationSize = 1)
public class ReviewEntity extends BaseEntity{
    //구매후기 게시판 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_SEQ")
    private Integer reviewId;

    //구매후기 제목
    @Column(length = 20, nullable = false)
    private String reviewSubject;

    //구매후기 내용
    @Column(length = 100)
    private String reviewContent;

    //구매후기 이미지
    private String reviewImage;

    //구매후기 평점
    @Column(nullable = false)
    private Integer reviewScore;

    //구매후기 조회수
    private Integer reviewCount;

    //구매후기 작성자
    private String reviewWriter;

    //추가사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;
}
