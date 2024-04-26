
/*
    설명 : 구매후기 게시판의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /board/list, /board/insert, /board/update, /board/delete, /board/detail
    출력값 : board/list, board/insert, board/update, board/detail
    작성일 : 24.02.21
    작성자 : 정아름
    수정사항 : 구매후기 게시판의 전체 목록 페이지는 page 처리 하기로 함
 */

package com.example.basic.Controller;

import com.example.basic.DTO.BoardDTO;
import com.example.basic.DTO.BoardcmtDTO;
import com.example.basic.DTO.MemberDTO;
import com.example.basic.Entity.BoardEntity;
import com.example.basic.Repository.BoardRepository;
import com.example.basic.Service.BoardService;
import com.example.basic.Service.BoardcmtService;
import com.example.basic.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardcmtController {
    //주입
    private final BoardcmtService boardcmtService;

    private final BoardService boardService;

    private final BoardRepository boardRepository;

    private final MemberService memberService;

    //삽입
    //댓글 상세보기 하단에 입력폼 존재 (페이지가 따로 존재 하지 않음)
    //게시글번호, 댓글작업 후 상세페이지로 다시출력
    @PostMapping("/boardcmt/insert")
    public String insertProc(BoardcmtDTO boardcmtDTO, Integer id, @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        boardcmtService.boardcmtInsert(boardcmtDTO, id, memberDTO.getMemberId());

        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addFlashAttribute("successMessage",
                "댓글이 등록되었습니다.");

        return "redirect:/board/detail";
    }

    //수정
    @GetMapping("/boardcmt/update")
    public String updateForm(Integer cid, Integer id, @AuthenticationPrincipal User user,
                             Model model, RedirectAttributes redirectAttributes) {

        MemberDTO memberDTO = memberService.detail(user.getUsername());

        BoardcmtDTO boardcmtDTO = boardcmtService.boardcmtDetail(cid, id, memberDTO.getMemberId());

        if (!boardcmtDTO.getMemberId().equals(memberDTO.getMemberId())) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "권한이 없습니다.");
            redirectAttributes.addAttribute("id", id);
            return "redirect:/board/detail";
        }

        model.addAttribute("data", boardcmtDTO);

        System.out.println(memberDTO);
        System.out.println(boardcmtDTO);

        return "boardcmt/update";
    }


    @PostMapping("/boardcmt/update")
    public String updateProc(BoardcmtDTO boardcmtDTO,
                             @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {

        MemberDTO memberDTO = memberService.detail(user.getUsername());

        Integer id = boardcmtDTO.getBoardId();

        if (boardcmtDTO.getMemberId().equals(memberDTO.getMemberId())) {
            boardcmtService.boardcmtUpdate(boardcmtDTO);
        }

        redirectAttributes.addAttribute("id", id);

        redirectAttributes.addFlashAttribute("successMessage",
                "댓글이 수정되었습니다.");

        System.out.println(memberDTO);
        System.out.println(boardcmtDTO);
        System.out.println(id);

        return "redirect:/board/detail";
    }

    //삭제
    @GetMapping("/boardcmt/delete")
    public String deleteProc(Integer id, Integer cid, @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        BoardcmtDTO boardcmtDTO = boardcmtService.boardcmtDetail(cid, id, memberDTO.getMemberId());

        if (boardcmtDTO.getMemberId().equals(memberDTO.getMemberId())) {
            boardcmtService.boardcmtDelete(cid);
        }

        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addFlashAttribute("successMessage",
                "댓글이 삭제되었습니다.");

        System.out.println(memberDTO);
        System.out.println(boardcmtDTO);

        return "redirect:/board/detail";
    }
}
