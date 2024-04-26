package com.example.basic.Repository;

import com.example.basic.Entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    //조회수 증가
    @Modifying
    @Query("UPDATE NoticeEntity u SET u.noticeCount= u.noticeCount+1" +
            " WHERE u.noticeId=:noticeId")
    void noticeCount(Integer noticeId);
}
