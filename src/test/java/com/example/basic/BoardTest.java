package com.example.basic;

import com.example.basic.Entity.BoardEntity;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Entity.NoticeEntity;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Repository.BoardRepository;
import com.example.basic.Repository.NoticeRepository;
import com.example.basic.Repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void boardInsertTest() {
        for(int i=1; i<=10; i++) {
            BoardEntity boardEntity = BoardEntity.builder()
                    .boardSubject("Test "+i+" 입니다.")
                    .boardContent("Test 입니다.")
                    .memberEntity(MemberEntity.builder()
                            .memberId(2)
                            .build())
                    .boardWriter("김나나")
                    .build();
            boardRepository.save(boardEntity);
        }
    }

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void setReviewRepository() {
        for (int i=1; i<=10; i++) {
            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .reviewSubject("Test "+i+" 입니다.")
                    .reviewContent("Test 입니다.")
                    .reviewCount(100)
                    .reviewScore(5)
                    .memberEntity(MemberEntity.builder()
                            .memberId(2)
                            .build())
                    .reviewWriter("김나나")
                    .build();
            reviewRepository.save(reviewEntity);
        }
    }

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void setNoticeRepository() {
        for (int i=1; i<=20; i++) {
            NoticeEntity noticeEntity = NoticeEntity.builder()
                    .noticeSubject("공지사항 Test "+i+" 입니다.")
                    .noticeContent("Test 입니다.")
                    .noticeWriter("김라라")
                    .noticeCount(100)
                    .build();
            noticeRepository.save(noticeEntity);
        }
    }
}
