package com.example.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReactAppController {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String getIndex(HttpServletRequest request) {
        return "/index.html";
    }

}
