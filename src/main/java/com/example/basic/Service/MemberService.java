package com.example.basic.Service;

import com.example.basic.DTO.MemberDTO;
import com.example.basic.Entity.CartEntity;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Repository.CartRepository;
import com.example.basic.Repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    //삽입
    public void memberInsert(MemberDTO memberDTO) throws IllegalAccessException {
        //기존 사용하는 아이디를 조회
        Optional<MemberEntity> member = Optional.ofNullable(memberRepository
                .findByMemberEmail(memberDTO.getMemberEmail()));

        if (member.isEmpty()) {
            //읽어온 값이 없으면(기존 사용하는 아이디가 존재하지 않으면)
            MemberEntity memberEntity = modelMapper.map(memberDTO,
                    MemberEntity.class);
            //저장
            memberRepository.save(memberEntity);
        } else {
            throw new IllegalAccessException("이미 가입된 회원입니다.");
        }
    }

    //수정
    public void memberUpdate(MemberDTO memberDTO) {
        MemberEntity memberEntity = modelMapper.map(memberDTO,
                MemberEntity.class);

        memberRepository.save(memberEntity);
    }

    //삭제
    public void memberDelete(Integer memberId) {

        //회원의 장바구니 찾기
        CartEntity cart = cartRepository.findByMemberId(memberId);
        //장바구니 삭제
        cartRepository.deleteById(cart.getCartId());

        //회원 계정 삭제
        memberRepository.deleteById(memberId);
    }

    //전체 조회
    public Page<MemberDTO> memberList(Pageable pageable) {
        int cutPage = pageable.getPageNumber() - 1;
        int pageCnt = 10;

        Pageable page = PageRequest.of(cutPage, pageCnt,
                Sort.by(Sort.Direction.DESC, "memberId"));

        Page<MemberEntity> memberEntities = memberRepository.findAll(page);

        Page<MemberDTO> memberDTOS = memberEntities.map(data ->
                modelMapper.map(data, MemberDTO.class));

        return memberDTOS;
    }

    //개별 조회
    public MemberDTO memberDetail(Integer memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow();

        MemberDTO memberDTO = modelMapper.map(memberEntity, MemberDTO.class);

        return memberDTO;
    }

    //마이페이지 조회
    public MemberDTO detail(String memberEmail) {

        MemberEntity member = memberRepository.findByMemberEmail(memberEmail);
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);

        return memberDTO;
    }
}
