package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.client.api.FastApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final FastApiClient fastApiClient;

    @GetMapping("/")
    public String index(){ return "index"; }

    @GetMapping("/sign_up")
    public String signup(){return "sign-up";}

    @GetMapping("/login")
    public String login(){return "login";}

}
