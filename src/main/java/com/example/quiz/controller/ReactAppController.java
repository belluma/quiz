package com.example.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ReactAppController {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String getIndex() {
        return "forward:/";
    }

}
