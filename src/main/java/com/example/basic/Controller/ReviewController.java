
/*
    설명 : 구매후기 게시판의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /review/list, /review/insert, /review/update, /review/delete, /review/detail
    출력값 : review/list, review/insert, review/update, review/detail
    작성일 : 24.02.21
    작성자 : 정아름
    수정사항 : 구매후기 게시판의 전체 목록 페이지는 page 처리 하기로 함
 */

package com.example.basic.Controller;

import com.example.basic.DTO.BoardDTO;
import com.example.basic.DTO.MemberDTO;
import com.example.basic.DTO.ReviewDTO;
import com.example.basic.DTO.ReviewcmtDTO;
import com.example.basic.Service.MailService;
import com.example.basic.Service.MemberService;
import com.example.basic.Service.ReviewService;
import com.example.basic.Service.ReviewcmtService;
import com.example.basic.Util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    //주입
    private final ReviewService reviewService;

    private final ReviewcmtService reviewcmtService;

    private final MemberService memberService;

    //전체 조회
    @GetMapping("/review/list")
    public String listForm(@PageableDefault(page = 1) Pageable pageable,
                           Model model) {
        //페이지처리
        Page<ReviewDTO> reviewDTOS = reviewService.reviewList(pageable);

        Map<String, Integer> page = PaginationUtil.Pagination(reviewDTOS);

        model.addAllAttributes(page);
        model.addAttribute("list", reviewDTOS);

        return "review/list";
    }

    //삽입
    @GetMapping("/review/insert")
    public String insertForm(@AuthenticationPrincipal User user, Model model) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        model.addAttribute("member",memberDTO);

        return "review/insert";
    }

    @PostMapping("/review/insert")
    public String insertProc(ReviewDTO reviewDTO,  @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        reviewService.reviewInsert(reviewDTO, memberDTO.getMemberId());

        redirectAttributes.addFlashAttribute("successMessage",
                "게시글이 등록되었습니다.");

        System.out.println(memberDTO);
        System.out.println(reviewDTO);

        return "redirect:/review/list";
    }

    //수정
    @GetMapping("/review/update")
    public String updateForm(Integer id, @AuthenticationPrincipal User user,
                             Model model, RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        ReviewDTO reviewDTO = reviewService.reviewDetail(id, memberDTO.getMemberId(),"M");

        if (!reviewDTO.getMemberId().equals(memberDTO.getMemberId())) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "권한이 없습니다.");
            return "redirect:/review/list";
        }

        model.addAttribute("data", reviewDTO);

        System.out.println(memberDTO);
        System.out.println(reviewDTO);

        return "review/update";
    }

    @PostMapping("/review/update")
    public String updateProc(ReviewDTO reviewDTO, @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        ReviewDTO reviewDTO1 = reviewService.reviewDetail(reviewDTO.getReviewId(), memberDTO.getMemberId(), "M");

        if (reviewDTO1 != null) {
            reviewService.reviewUpdate(reviewDTO, memberDTO.getMemberId());
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "게시글이 수정되었습니다.");

        System.out.println(memberDTO);
        System.out.println(reviewDTO1);

        return "redirect:/review/list";
    }

    //삭제
    @GetMapping("/review/delete")
    public String deleteProc(Integer id, RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal User user) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        ReviewDTO reviewDTO = reviewService.reviewDetail(id, memberDTO.getMemberId(), "M");

        if (!reviewDTO.getMemberId().equals(memberDTO.getMemberId())) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "권한이 없습니다.");
            return "redirect:/review/list";
        }

        reviewService.reviewDelete(id);

        redirectAttributes.addFlashAttribute("successMessage",
                "게시글이 삭제되었습니다.");

        return "redirect:/review/list";
    }

    //개별 조회
    @GetMapping("/review/detail")
    public String detailProc(Integer id, Model model,
                             @AuthenticationPrincipal User user) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        ReviewDTO reviewDTO = reviewService.reviewDetail(id, memberDTO.getMemberId(),"R");
        List<ReviewcmtDTO> reviewcmtDTOS = reviewcmtService.reviewcmtlist(id);


        model.addAttribute("member", memberDTO);
        model.addAttribute("data", reviewDTO);
        model.addAttribute("list", reviewcmtDTOS);

        System.out.println(memberDTO);
        System.out.println(reviewDTO);
        System.out.println(reviewcmtDTOS);

        return "review/detail";
    }
    //확인해라!!
}
