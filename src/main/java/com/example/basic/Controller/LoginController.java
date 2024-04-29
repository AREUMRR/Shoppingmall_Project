
/*
    설명 : login 페이지 영역
    입력값 : /login
    출력값 : login/form
    작성일 : 24.03.05
    작성자 : 정아름
    수정사항 : 아이디 찾기 & 비밀번호 찾기 - 이메일 전송 방식으로 수정.
              구글 API 또는 네이버 API 로 변경하기.
              build까지 해놓은 상태.
 */

package com.example.basic.Controller;

import com.example.basic.DTO.MemberDTO;
import com.example.basic.Service.LoginService;
import com.example.basic.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    //로그인 페이지
    @GetMapping("/login")
    public String loginForm() {

        return "login/form";
    }

    //아이디 찾기
    @GetMapping("/find/email")
    public String email() {

        return "login/email";
    }

    @PostMapping("/find/email")
    public String emailProc(Model model, String memberName, String memberPassword,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/find/email";
        }
        //회원 비밀번호, 이름 조회
        MemberDTO memberDTO = (MemberDTO) loginService.findEmail(memberPassword, memberName);
        if (memberDTO == null) {
            bindingResult.reject("Not found", "비밀번호 또는 이름이 일치하지 않습니다.");
            //이메일 찾기 페이지로 이동
            return "redirect:/find/email";
        }
        model.addAttribute("data", memberDTO);
        //조회하는 값이 있으면 이메일 확인 페이지로 이동
        return "login/resultemail";
    }
}
