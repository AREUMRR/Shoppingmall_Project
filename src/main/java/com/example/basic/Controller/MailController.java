package com.example.basic.Controller;

import com.example.basic.DTO.EmailDTO;
import com.example.basic.DTO.MemberDTO;
import com.example.basic.Service.MailService;
import com.example.basic.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final MemberService memberService;

    //java mail sender 로 변경
    //테스트 해야 됨!!!!!!!!!!!!
    //비밀번호 찾기
    @GetMapping("/find/password")
    public String findPassword() {
        return "login/password";
    }

    @PostMapping("/find/password")
    public String sendPassword(String memberEmail, String memberName) {

        //회원 이메일, 이름 조회
        MemberDTO memberDTO = (MemberDTO) mailService.findPassword(memberEmail, memberName);

        //임시 비밀번호 생성
        String password = mailService.createRandomPw();
        //임시 비밀번호로 변경
        memberDTO.setMemberPassword(password);
        memberService.memberUpdate(memberDTO);

        //회원 정보가 있으면
        if (memberDTO != null) {
            String content = "login/sendmail";
            EmailDTO emailDTO = new EmailDTO();

            //이메일 전송
            emailDTO.setTitle("임시 비밀번호 발송(Test)");
            emailDTO.setEmail(memberDTO.getMemberEmail());
            emailDTO.setContent("임시 비밀번호는 '" + password +
                    "' 입니다. 로그인 후 반드시 비밀번호를 변경하세요!");
            //emailDTO.setContent(content);

            mailService.sendEmail(emailDTO);
        }

        //조회하는 값이 있으면 비밀번호 확인 페이지로 이동
        return "login/resultpassword";
    }

//    @PostMapping("/check-duplicate-email")
//    public boolean checkDuplicateEmail(String email) {
//        MemberDTO memberDTO = memberService.detail(email);
//        if (memberDTO == null) {
//            return true;
//        } else {
//            return false;
//        }
//    }

}
