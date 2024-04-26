package com.example.basic.Repository;

import com.example.basic.Entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    //제목 조회
    Page<BoardEntity> findByBoardSubjectContaining(String keyword, Pageable pageable);

    //내용 조회
    Page<BoardEntity> findByBoardContentContaining(String keyword, Pageable pageable);

    //작성자조회
    Page<BoardEntity> findByBoardWriterContaining(String keyword, Pageable pageable);

    //제목+내용 조회
    @Query("SELECT s FROM BoardEntity s WHERE s.boardSubject LIKE %:keyword%" +
            " or s.boardContent LIKE %:keyword%")
    Page<BoardEntity> findByBoardSCContaining(String keyword, Pageable pageable);

    @Query("select w from BoardEntity w where w.memberEntity.memberId = :memberId")
    List<BoardEntity> findByMemberId(Integer memberId);

    @Query("select w from BoardEntity w where w.memberEntity.memberId = :memberId")
    BoardEntity findByMember(Integer memberId);

}
