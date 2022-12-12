package com.example.admin.controller;

import com.example.admin.dto.LoginDTO;
import com.example.admin.service.IAdminService;
import com.example.commons.config.JwtConfig;
import com.example.commons.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class adminController {
    @Autowired
    IAdminService iAdminService;
    @Autowired
    JwtConfig jwtConfig;
    //登录
    @GetMapping("/admin/login/")
    public ResponseEntity<Object> studentLogin(@RequestBody LoginDTO loginDTO){
        // 将学生的id存入token中，作为以后访问接口的凭证。
        String token = jwtConfig.createToken(loginDTO.getStudentId(),"管理员");
        // 根据用户名密码获取学生具体信息。
        Student student = iAdminService.login(loginDTO.getStudentId(),loginDTO.getPassword());
        // 将具体信息和token一起返回给前端。
        Map<String,Object> userInfo = new HashMap<>(2) {{
            put("token", token);
            put("user", student);
        }};
        if(student==null){
            // 这里用ResponseEntity来封装返回值。
            return ResponseEntity.ok("登录失败！");
        }
        else {
            return  ResponseEntity.ok(userInfo);
        }
    }
}
