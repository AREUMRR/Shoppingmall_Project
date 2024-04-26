
/*
    설명 : home 페이지 영역
    입력값 : /
    출력값 : index
    작성일 : 24.02.22
    작성자 : 정아름
    수정사항 : index 페이지도 안열림...
 */

package com.example.basic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    //main
    @GetMapping( "/")
    public String mainForm() {
        return "index";
    }
}
