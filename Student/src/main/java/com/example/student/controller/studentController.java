package com.example.student.controller;


import com.example.commons.config.JwtConfig;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import com.example.student.entity.dto.LoginDTO;
import com.example.student.service.impl.IStudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    IStudentServiceImpl iStudentService;

    @Autowired
    JwtConfig jwtConfig;

    Student student = new Student();

    @GetMapping("/student/login")
    public ResponseEntity<Object> studentLogin(@RequestBody LoginDTO loginDTO){
        // 将学生的id存入token中，作为以后访问接口的凭证。
        String token = jwtConfig.createToken(loginDTO.getStudentId(),"学生");
        // 根据用户名密码获取学生具体信息。
        Student student = iStudentService.login(loginDTO.getStudentId(),loginDTO.getPassword());
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

    @GetMapping("/student/getTeacherById/{teacherId}")
    public Teacher getTeacherById(@PathVariable String teacherId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getTeacherById(teacherId);
    }

    @PostMapping("/student/getCourseByTeacherId/{teacherId}")
    public List<Course> getCourseByTeacherId(@PathVariable String teacherId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getCourseByTeacherId(teacherId);
    }

    @GetMapping("/student/getAllCourse")
    public List<Course> getAllCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getAllList();
    }

    @PostMapping("/student/joinCourse/{courseId}")
    public String joinCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return iStudentService.joinCourse(courseId,jwtConfig.getUserIdFromToken(token));
        }
        return null;
    }

    @GetMapping("/student/getCourseByType/{courseType}")
    public List<Course> getCourseByType(@PathVariable String courseType,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getCourseByType(courseType);
    }

    @PostMapping("/student/quitCourse/{courseId}")
    public String quitCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.quitCourse(courseId,jwtConfig.getUserIdFromToken(token));
    }

    @GetMapping("/student/getCourseScore/{courseId}")
    public String getCourseScore(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getScore(courseId,jwtConfig.getUserIdFromToken(token));
    }

    @GetMapping("/student/getMyCourse")
    public List<Course> getMyCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getMyCourse(jwtConfig.getUserIdFromToken(token));
    }

    @PostMapping("/student/upload/file/{courseId}/{chapterId}/{experimentId}")
    public String upload(@PathVariable String courseId,@PathVariable String chapterId,@PathVariable String experimentId,@RequestPart("file") MultipartFile multipartFile){
        return iStudentService.upload(multipartFile,courseId,chapterId,experimentId);
    }

    //为老师提供查询学生的接口
    @GetMapping("/teacher/getStudent/{studentId}")
    public Student getStudent(@PathVariable String studentId){
        return iStudentService.getById(studentId);
    }
}
