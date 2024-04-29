package com.example.basic.Service;

import com.example.basic.DTO.EmailDTO;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final LoginRepository loginRepository;
    private final JavaMailSender javaMailSender;

    //임시 비밀번호 생성
    public String createRandomPw() {
        String[] strings = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
        };

        //임시 비밀번호 변수
        StringBuilder password = new StringBuilder();

        //랜덤으로 값을 6개를 뽑아 조합
        for (int i = 0; i < 6; i++) {
            int random = (int) (strings.length * Math.random());
            password.append(strings[random]);
        }
        return password.toString();
    }

    //html 메일 전송
    public void sendEmail(EmailDTO emailDTO) {
        //단순 문자 메일 보낼 수 있는 객체
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            //메일 제목
            message.setSubject(emailDTO.getTitle());
            //수신자 메일 주소
            message.setText(emailDTO.getEmail());
            //메일 내용
            message.setText(emailDTO.getContent());

            javaMailSender.send(message);
            log.info("SUCCESS");

        } catch (Exception e) {
            log.info("FAIL");
            throw new RuntimeException(e);
        }
    }

    //비밀번호 찾기
    public Map<String, String> findPassword(String memberEmail, String memberName) {
        //이메일과 이름이 존재하는지 조회
        Optional<MemberEntity> memberEntity = loginRepository.
                findByMemberEmailAndMemberName(memberEmail, memberName);

        //조회한 데이터가 없으면
        if (memberEntity.isEmpty()) {
            //예외처리
            throw new UsernameNotFoundException("이메일 또는 이름이 일치하지 않습니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("memberPassword", memberEntity.get().getMemberPassword());

        return result;
    }

    public void memberCheck(String memberEmail) {
        Optional<MemberEntity> member = loginRepository.findByMemberEmail(memberEmail);

        if (member.isEmpty() && !member.get().getMemberEmail().equals(memberEmail)) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }
    }
}
