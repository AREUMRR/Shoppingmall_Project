package com.example.basic.Service;

import com.example.basic.DTO.MemberDTO;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final LoginRepository loginRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    //UsernameNotFoundException-사용자가 존재하지 않으면 예외발생(오류)
    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        //회원 이메일이 존재하는지 조회
        Optional<MemberEntity> memberEntity = loginRepository.findByMemberEmail(memberEmail);

        //조회한 데이터가 있으면
        if (memberEntity.isPresent()) {
            return User.withUsername(memberEntity.get().getMemberEmail())
                    //passwordEncoder 비밀번호 보안작업
                    .password(passwordEncoder.encode(memberEntity.get().getMemberPassword()))
                    .roles(memberEntity.get().getRoleType().name())
                    .build();
        } else { //조회된 데이터가 없으면
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }
    }


    //이메일 찾기
    public Map<String, String> findEmail(String memberPassword, String memberName) {
        //비밀번호와 이름이 존재하는지 조회
        Optional<MemberEntity> memberEntity = loginRepository.
                findByMemberPasswordAndMemberName(memberPassword, memberName);

        //조회한 데이터가 없으면
        if (memberEntity.isEmpty()) {
            //예외처리
            throw new UsernameNotFoundException("비밀번호 또는 이름이 일치하지 않습니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("memberEmail", memberEntity.get().getMemberEmail());

        return result;
    }

    //로그인 처리
    public MemberDTO login(String memberEmail) {
        //회원 이메일과 비밀번호를 조회
        Optional<MemberEntity> memberEntity = loginRepository.findByMemberEmail(
                memberEmail);
        //조회한 데이터가 있으면
        if (memberEntity.isPresent()) {
            MemberDTO memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
            return memberDTO;
        } else {
            return null;
        }
    }

}
