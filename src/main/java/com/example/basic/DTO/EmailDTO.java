package com.example.basic.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDTO {
    //메일 제목
    private String title;
    //수신자 메일 주소
    private String email;
    //메일 내용
    private String content;
}
