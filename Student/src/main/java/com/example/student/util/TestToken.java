package com.example.student.util;

import com.example.commons.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class TestToken {
    JwtConfig jwtConfig;
    public String testToken(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(jwtConfig.getUserIdFromToken(token).equals(""))
            return "false";
        return jwtConfig.getUserIdFromToken(token);
    }
}
