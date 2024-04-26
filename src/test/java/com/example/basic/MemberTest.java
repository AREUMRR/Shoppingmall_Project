package com.example.basic;

import com.example.basic.Constant.RoleType;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void memberInsertTest() {
        for(int i=1; i<=10; i++) {
            MemberEntity memberEntity = MemberEntity.builder()
                    .memberEmail("sksk"+i+"@gmail.com")
                    .memberPassword("1234")
                    .memberName("김나나")
                    .memberPhone("010")
                    .memberAddress("경기도 부천시")
                    .roleType(RoleType.USER)
                    .build();
            memberRepository.save(memberEntity);
        }
    }

    @Test
    public void setMemberRepository() {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail("rkrk@gmail.com")
                .memberPassword("1234")
                .memberName("김가가")
                .memberPhone("010")
                .memberAddress("경기도 부천시")
                .roleType(RoleType.ADMIN)
                .build();
        memberRepository.save(memberEntity);
    }
}
