package com.example.basic.Entity;

/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 : 테이블 생성되고 테스트까지 완료했는데 왜 오류 떠있는지 모르겠음
 */

import com.example.basic.Constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "member")
@SequenceGenerator(name = "member_SEQ", sequenceName = "member_SEQ", initialValue = 1,
        allocationSize = 1)
public class MemberEntity extends BaseEntity{
    //회원 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_SEQ")
    private Integer memberId;

    //회원 이메일
    @Column(length = 20, nullable = false)
    private String memberEmail;

    //회원 비밀번호
    @Column(length = 20, nullable = false)
    private String memberPassword;

    //회원 이름
    @Column(length = 10)
    private String memberName;

    //회원 전화번호
    @Column(length = 20)
    private String memberPhone;

    //회원 주소
    @Column(length = 30)
    private String memberAddress;

    //회원 분류
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
