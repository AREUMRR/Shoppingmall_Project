package com.example.basic.Repository;

import com.example.basic.Entity.BoardEntity;
import com.example.basic.Entity.BoardcmtEntity;
import com.example.basic.Entity.ReviewcmtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewcmtRepository extends JpaRepository<ReviewcmtEntity, Integer> {

    @Query("SELECT s FROM ReviewcmtEntity s WHERE s.reviewEntity.reviewId = :reviewId")
    List<ReviewcmtEntity> findByReviewId(Integer reviewId);

    @Query("select s from ReviewcmtEntity s where s.memberEntity.memberId = :memberId")
    ReviewcmtEntity findByMemberId(Integer memberId);

}
