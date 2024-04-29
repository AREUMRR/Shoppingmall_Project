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
@Table(name = "cart")
@SequenceGenerator(name = "cart_SEQ", sequenceName = "cart_SEQ", initialValue = 1,
        allocationSize = 1)
public class CartEntity extends BaseEntity {
    //장바구니 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_SEQ")
    private Integer cartId;

    //다대일(N:1)관계를 정의
    //여러개의 주문이 하나의 회원 또는 상품에 해당할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    //외래키 정의
    //장바구니 테이블은 회원 테이블과 연관되어 있다.
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

}
