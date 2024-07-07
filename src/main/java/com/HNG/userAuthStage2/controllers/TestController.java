package com.HNG.userAuthStage2.controllers;

import com.HNG.userAuthStage2.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "/keepAlive")
    public void test() {
        System.out.println("========= keep alive 2  =================");
        userService.findByEmail("unyime10@gmail.com");
//        return ResponseEntity.ok().build();
    }
}
