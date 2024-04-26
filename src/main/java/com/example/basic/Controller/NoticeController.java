package com.example.basic.Controller;

import com.example.basic.DTO.NoticeDTO;
import com.example.basic.Service.NoticeService;
import com.example.basic.Util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    //주입
    private final NoticeService noticeService;

    //전체조회
    @GetMapping("/notice/list")
    public String listForm(@PageableDefault(page = 1) Pageable pageable,
                           Model model) {
        //페이지처리
        Page<NoticeDTO> noticeDTOS = noticeService.noticeList(pageable);

        Map<String, Integer> page = PaginationUtil.Pagination(noticeDTOS);

        model.addAllAttributes(page);
        model.addAttribute("list", noticeDTOS);

        return "notice/list";
    }

    //삽입
    @GetMapping("/notice/insert")
    public String insertForm() {

        return "notice/insert";
    }

    @PostMapping("/notice/insert")
    public String insertProc(NoticeDTO noticeDTO) {
        noticeService.noticeInsert(noticeDTO);

        return "redirect:/notice/list";
    }

    //수정
    @GetMapping("/notice/update")
    public String updateForm(Integer id, Model model) {
        NoticeDTO noticeDTO = noticeService.noticeDetail(id, "M");
        model.addAttribute("data", noticeDTO);

        return "notice/update";
    }

    @PostMapping("/notice/update")
    public String updateProc(NoticeDTO noticeDTO) {
        noticeService.noticeUpdate(noticeDTO);

        return "redirect:/notice/list";
    }

    //삭제
    @GetMapping("/notice/delete")
    public String deleteProc(Integer id) {
        noticeService.noticeDelete(id);

        return "redirect:/notice/list";
    }

    //개별 조회
    @GetMapping("/notice/detail")
    public String readProc(Integer id, Model model) {
        NoticeDTO noticeDTO=noticeService.noticeDetail(id, "R");
        model.addAttribute("data", noticeDTO);

        return "notice/detail";
    }
}
