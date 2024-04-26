/*
작성자 : 정아름
작성일 : 24.02.19
작성내용 : 회원 구현
확인사항 : 테스트 해야함
 */

package com.example.basic.DTO;

import com.example.basic.Constant.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    //회원 번호
    private Integer memberId;

    //회원 이메일
    @NotBlank (message = "이메일은 필수입니다.")
    //@Email
    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message="이메일 형식이 올바르지 않습니다.")
    private String memberEmail;

    //회원 비밀번호
    @NotBlank (message = "비밀번호는 필수입니다.")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String memberPassword;

    //회원 이름
    @NotBlank (message = "이름은 필수입니다.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다.")
    private String memberName;

    //회원 전화번호
    private String memberPhone;

    //회원 주소
    @NotBlank (message = "주소는 필수입니다.")
    private String memberAddress;

    //회원 분류
    private RoleType roleType;

    //등록일
    private LocalDateTime regDate;

    //수정일
    private LocalDateTime modDate;
}
