package com.example.student.controller;

import com.example.student.config.JwtConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class test {
    @Resource
    JwtConfig jwtConfig;
    @GetMapping("/test")
    public String test() {
        return "hello!";
    }


    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("passWord") String password) {
        String userId = 5 + "";
        String token = jwtConfig.createToken(userId);
        return token;
    }
}
