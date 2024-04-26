package com.example.basic.Repository;

import com.example.basic.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    //회원 찾기
    @Query(value = "SELECT s FROM OrderEntity s WHERE s.memberEntity.memberId=:memberId")
    OrderEntity findByMemberId(@Param("memberId") Integer memberId);

    @Query(value = "SELECT s FROM OrderEntity s WHERE s.memberEntity.memberId=:memberId")
    List<OrderEntity> findByMember(@Param("memberId") Integer memberId);
}
