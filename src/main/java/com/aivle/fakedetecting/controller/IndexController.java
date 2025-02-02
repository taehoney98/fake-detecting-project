package com.aivle.fakedetecting.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {



    @GetMapping("/")
    public String index(){ return "index"; }

    @GetMapping("/signup")
    public String signup(){return "signup";}

    @GetMapping("/login")
    public String login(){return "login";}

}
