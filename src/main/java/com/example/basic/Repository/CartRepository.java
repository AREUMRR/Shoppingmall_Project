package com.example.basic.Repository;

import com.example.basic.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    //회원 찾기
    @Query(value = "SELECT s FROM CartEntity s WHERE s.memberEntity.memberId=:memberId")
    CartEntity findByMemberId(@Param("memberId") Integer memberId);

    @Query(value = "SELECT s FROM CartEntity s WHERE s.memberEntity.memberId=:memberId")
    List<CartEntity> findByMember(@Param("memberId") Integer memberId);
}
