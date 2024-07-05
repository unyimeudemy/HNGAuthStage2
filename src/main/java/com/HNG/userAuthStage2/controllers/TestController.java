package com.HNG.userAuthStage2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

    @GetMapping(path = "/test1")
    public String test(){
        return "this is a test";
    }
}
