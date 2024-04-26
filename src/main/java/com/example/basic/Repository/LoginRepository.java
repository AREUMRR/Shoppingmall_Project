package com.example.basic.Repository;

import com.example.basic.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<MemberEntity, Integer> {

    //회원 이메일 조회
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    
    //회원 이메일 비밀번호 조회
    //Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    //회원 비밀번호, 이름 조회
    Optional<MemberEntity> findByMemberPasswordAndMemberName(String memberPassword, String memberName);

    //회원 아이디, 이름 조회
    Optional<MemberEntity> findByMemberEmailAndMemberName(String memberEmail, String memberName);
}