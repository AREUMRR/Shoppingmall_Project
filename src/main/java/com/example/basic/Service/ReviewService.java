package com.example.basic.Service;

import com.example.basic.DTO.ReviewDTO;
import com.example.basic.DTO.ReviewDTO;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Entity.ReviewcmtEntity;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Repository.MemberRepository;
import com.example.basic.Repository.ReviewRepository;
import com.example.basic.Repository.ReviewcmtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewcmtRepository reviewcmtRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    //삽입
    public void reviewInsert(ReviewDTO reviewDTO, Integer memberId) {
        reviewDTO.setReviewCount(0);
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow();

        ReviewEntity reviewEntity = modelMapper.map(reviewDTO,
                ReviewEntity.class);

        reviewEntity.setMemberEntity(memberEntity);
        reviewRepository.save(reviewEntity);
    }

    //수정
    public void reviewUpdate(ReviewDTO reviewDTO, Integer memberId) {
        Integer id = reviewDTO.getReviewId();

        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        ReviewEntity review = reviewRepository.findById(id).orElseThrow();

        if (review.getMemberEntity().getMemberId().equals(memberId)) {
            ReviewEntity reviewEntity = modelMapper.map(reviewDTO, ReviewEntity.class);

            reviewEntity.setMemberEntity(member);
            reviewRepository.save(reviewEntity);
        }
    }

    //삭제
    public void reviewDelete(Integer reviewId) {

        List<ReviewcmtEntity> reviewcmtEntity = reviewcmtRepository.findByReviewId(reviewId);

        for (ReviewcmtEntity reviewcmt : reviewcmtEntity) {
            reviewcmtRepository.deleteById(reviewcmt.getReviewcmtId());
        }

        reviewRepository.deleteById(reviewId);
        
    }

    //전체 조회
    public Page<ReviewDTO> reviewList(Pageable pageable) {
        int currentPage = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Pageable page;
        page = PageRequest.of(currentPage, pageLimit,
                Sort.by(Sort.Direction.DESC, "reviewId"));

        Page<ReviewEntity> reviewEntities = reviewRepository.findAll(page);
        Page<ReviewDTO> reviewDTOS = reviewEntities.map(data -> modelMapper.
                map(data, ReviewDTO.class));

        return reviewDTOS;
    }

    //개별 조회
    public ReviewDTO reviewDetail(Integer reviewId, Integer memberId, String pandan) {
        //조회수 증가
        if (pandan.equals("R")) {
            reviewRepository.reviewCount(reviewId);
        }

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElseThrow();

        ReviewDTO reviewDTO = null;

        if (reviewEntity.getMemberEntity().getMemberId().equals(memberId)) {
            reviewDTO = modelMapper.map(reviewEntity, ReviewDTO.class);
        }

        return reviewDTO;
    }

    //회원의 게시글 조회
    public List<ReviewDTO> memberReview(Integer memberId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByMemberId(memberId);
        List<ReviewDTO> reviewDTOS = Arrays.asList(modelMapper.map(reviewEntities, ReviewDTO[].class));

        return reviewDTOS;
    }
}