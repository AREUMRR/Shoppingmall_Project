package com.example.basic;

import com.example.basic.Entity.BoardEntity;
import com.example.basic.Entity.BoardcmtEntity;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Entity.ReviewcmtEntity;
import com.example.basic.Repository.BoardRepository;
import com.example.basic.Repository.BoardcmtRepository;
import com.example.basic.Repository.ReviewcmtRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentTest {
    @Autowired
    private BoardcmtRepository boardcmtRepository;

    @Test
    public void setBoardcmtRepository() {
        BoardcmtEntity boardcmtEntity = BoardcmtEntity.builder()
                .boardEntity(BoardEntity.builder()
                        .boardId(100)
                        .boardSubject("Test")
                        .boardContent("Test")
                        .boardWriter("김나나")
                        .build())
                .boardcmtBody("Test")
                .boardcmtWriter("김가가")
                .build();
        boardcmtRepository.save(boardcmtEntity);
    }

    @Autowired
    private ReviewcmtRepository reviewcmtRepository;

    @Test
    public void setReviewcmtRepository() {
        ReviewcmtEntity reviewcmtEntity = ReviewcmtEntity.builder()
                .reviewEntity(ReviewEntity.builder()
                        .reviewId(100)
                        .reviewSubject("Test")
                        .reviewContent("게시글Test")
                        .reviewCount(10)
                        .build())
                .reviewcmtBody("Test")
                .reviewcmtWriter("김나나")
                .build();
        reviewcmtRepository.save(reviewcmtEntity);
    }
}
