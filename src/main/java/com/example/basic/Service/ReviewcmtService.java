
/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 : 리뷰 댓글이랑 게시판 댓글 repository & service 다시 확인해라!!!!!!!!!!
 */

package com.example.basic.Service;

import com.example.basic.DTO.ReviewcmtDTO;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Entity.ReviewcmtEntity;
import com.example.basic.Repository.ReviewRepository;
import com.example.basic.Repository.ReviewcmtRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewcmtService {
    private final ReviewcmtRepository reviewcmtRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    //삽입
    public void reviewcmtInsert(ReviewcmtDTO reviewcmtDTO, Integer reviewId) {

        Optional<ReviewEntity> data = reviewRepository.findById(reviewId);

        ReviewEntity reviewEntity = data.orElseThrow();

        ReviewcmtEntity reviewcmtEntity = modelMapper.map(reviewcmtDTO, ReviewcmtEntity.class);

        reviewcmtEntity.setReviewEntity(reviewEntity);
        reviewcmtRepository.save(reviewcmtEntity);
    }

    //수정
    public void reviewcmtUpdate(ReviewcmtDTO reviewcmtDTO, Integer reviewId) {

        Optional<ReviewEntity> data = reviewRepository.findById(reviewId);

        ReviewEntity reviewEntity = data.orElseThrow();

        ReviewcmtEntity reviewcmtEntity = modelMapper.map(reviewcmtDTO, ReviewcmtEntity.class);

        reviewcmtEntity.setReviewEntity(reviewEntity);
        reviewcmtRepository.save(reviewcmtEntity);
    }

    //삭제
    public void reviewcmtDelete(Integer reviewcmtId) {
        reviewcmtRepository.deleteById(reviewcmtId);
    }

    //전체 조회
    public List<ReviewcmtDTO> reviewcmtlist(Integer reviewId) {
        List<ReviewcmtEntity> reviewcmtEntities = reviewcmtRepository.findByReviewId(reviewId);

        List<ReviewcmtDTO> reviewcmtDTOS = Arrays.asList(modelMapper.
                map(reviewcmtEntities, ReviewcmtDTO[].class));
        return reviewcmtDTOS;
    }

    //개별조회
    public ReviewcmtDTO reviewcmtDetail(Integer reviewcmtId) {
        Optional<ReviewcmtEntity> reviewcmtEntity = reviewcmtRepository.findById(reviewcmtId);
        ReviewcmtDTO reviewcmtDTO = modelMapper.map(reviewcmtEntity, ReviewcmtDTO.class);

        return reviewcmtDTO;
    }
}
