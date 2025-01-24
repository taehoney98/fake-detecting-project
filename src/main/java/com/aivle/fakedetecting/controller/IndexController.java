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

    @GetMapping("/sign_up")
    public String signup(){return "sign-up";}

    @GetMapping("/login")
    public String login(){return "login";}

}
