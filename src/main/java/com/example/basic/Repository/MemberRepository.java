package com.example.basic.Repository;

import com.example.basic.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    //회원 이메일 조회
    MemberEntity findByMemberEmail(String memberEmail);

}
