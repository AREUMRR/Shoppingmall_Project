package com.example.basic.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InfoController {
    //information 페이지
    @GetMapping("/info/information")
    public String informationForm() {
        return "info/information";
    }

    //privacy 페이지
    @GetMapping("/info/privacy")
    public String privacyForm() {
        return "info/privacy";
    }

    //terms 페이지
    @GetMapping("/info/terms")
    public String termsForm() {
        return "info/terms";
    }

    //about 페이지
    @GetMapping("/info/about")
    public String aboutForm() {
        return "info/about";
    }
}
