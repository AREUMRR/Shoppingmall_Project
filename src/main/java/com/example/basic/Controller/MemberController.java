
/*
    설명 : 회원관리의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /member/list, /member/insert, /member/update, /member/delete, /member/detail
    출력값 : member/list, member/insert, member/update, member/detail
    작성일 : 24.02.22
    작성자 : 정아름
    수정사항 : 회원관리의 전체 목록 페이지는 page 처리 하기로 함
 */

package com.example.basic.Controller;

import com.example.basic.Constant.RoleType;
import com.example.basic.DTO.BoardDTO;
import com.example.basic.DTO.MemberDTO;
import com.example.basic.DTO.ReviewDTO;
import com.example.basic.Service.*;
import com.example.basic.Util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //주입
    private final MemberService memberService;

    private final BoardService boardService;

    private final ReviewService reviewService;

    //전체 조회
    @GetMapping("/member/list")
    public String listForm(@PageableDefault(page = 1) Pageable pageable,
                           Model model) {
        //페이지처리
        Page<MemberDTO> memberDTOS = memberService.memberList(pageable);

        Map<String, Integer> page = PaginationUtil.Pagination(memberDTOS);

        model.addAllAttributes(page);
        model.addAttribute("list", memberDTOS);

        return "member/list";
    }

    //삽입
    @GetMapping("/member/insert")
    public String insertForm() {

        return "member/insert";
    }

    @PostMapping("/member/insert")
    public String insertProc(@Valid MemberDTO memberDTO, BindingResult bindingResult) {
        //회원 검증
        if (bindingResult.hasErrors()) {
            //오류가 있으면 회원 가입페이지로 이동
            return "redirect:/member/insert";
        }

        try {
            memberDTO.setRoleType(RoleType.USER);

            memberService.memberInsert(memberDTO);
        } catch (Exception e) {
            return "redirect:/member/insert";
        }

        return "redirect:/login";
    }

    //수정
    @GetMapping("/member/update")
    public String updateForm(Integer id, Model model) {
        MemberDTO memberDTO = memberService.memberDetail(id);

        model.addAttribute("data", memberDTO);

        return "member/update";
    }

    @PostMapping("/member/update")
    public String updateProc(MemberDTO memberDTO) {
        memberService.memberUpdate(memberDTO);

        return "redirect:/";
    }

    //삭제
    @GetMapping("/member/delete")
    public String deleteProc(Integer id) {
        memberService.memberDelete(id);

        return "redirect:/";
    }

    //개별 조회
    @GetMapping("/member/detail")
    public String readProc(Integer id, Model model) {
        MemberDTO memberDTO = memberService.memberDetail(id);

        model.addAttribute("data", memberDTO);

        return "member/detail";
    }

    //마이페이지
    @GetMapping("/mypage")
    public String myPageForm(@AuthenticationPrincipal User user,
                             Model model) {

        //보안 인증 된 유저의 이메일로 회원 정보 찾기
        String memberEmail = user.getUsername();
        MemberDTO memberDTO = memberService.detail(memberEmail);

        List<BoardDTO> boardDTO = boardService.memberBoard(memberDTO.getMemberId());

        List<ReviewDTO> reviewDTO = reviewService.memberReview(memberDTO.getMemberId());

        model.addAttribute("board", boardDTO);
        model.addAttribute("review", reviewDTO);
        model.addAttribute("data", memberDTO);

        return "member/mypage";
    }
}
