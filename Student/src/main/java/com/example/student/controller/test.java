package com.example.student.controller;

import com.example.student.config.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class test {
    @Resource
    JwtConfig jwtConfig;
    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        log.info("进入");
        String token = request.getHeader(jwtConfig.getHeader());
        return jwtConfig.getUserIdFromToken(token);
    }


    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("passWord") String password) {
        String userId = 5 + "";
        String token = jwtConfig.createToken(userId);
        jwtConfig.getUserIdFromToken(token);
        return token;
    }
}
