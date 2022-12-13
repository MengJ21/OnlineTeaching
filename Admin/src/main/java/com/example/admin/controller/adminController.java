package com.example.admin.controller;

import com.example.admin.dto.LoginDTO;
import com.example.admin.entity.Admin;
import com.example.admin.mapper.courseMapper;
import com.example.admin.mapper.courseStudentMapper;
import com.example.admin.service.IAdminService;
import com.example.commons.config.JwtConfig;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
        String token = jwtConfig.createToken(loginDTO.getAdminId(),"管理员");
        // 根据用户名密码获取学生具体信息。
        Admin admin = iAdminService.login(loginDTO.getAdminId(),loginDTO.getPassword());
        // 将具体信息和token一起返回给前端。
        Map<String,Object> userInfo = new HashMap<>(2) {{
            put("token", token);
            put("user", admin);
        }};
        if(admin==null){
            // 这里用ResponseEntity来封装返回值。
            return ResponseEntity.ok("登录失败！");
        }
        else {
            return  ResponseEntity.ok(userInfo);
        }
    }
    //为老师注册账号
    @PostMapping("/admin/registerForTeacher/")
    public String registerForTeacher(@RequestBody Teacher teacher, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.registerForTeacher(teacher);
    }
    //删除课程
    @PostMapping("/admin/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.deleteCourse(courseId);
    }
    //查看某个老师的课程
    @PostMapping("/admin/getCourseByTeacherId/{teacherId}")
    public List<Course> getCourseByTeacherId(@PathVariable String teacherId, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.getCourseByTeacherId(teacherId);
    }
    //查看所有课程
    @GetMapping("/admin/getAllCourse")
    public List<Course> getAllCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.getAllList();
    }
    //通过类型查看课程
    @GetMapping("/admin/getCourseByType/{courseType}")
    public List<Course> getCourseByType(@PathVariable String courseType,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.getCourseByType(courseType);
    }
    //通过课程名进行搜索
    @GetMapping("/admin/selectCourseByName/{courseName}")
    public Course selectCourseByName(@PathVariable String courseName,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.selectCourseByName(courseName);
    }
    //让某个学生加入某个课程
    @PostMapping("/admin/joinCourse/{courseId}/{studentId}")
    public String joinCourse(@PathVariable String courseId,@PathVariable String studentId, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return iAdminService.joinCourse(courseId,studentId);
        }
        return null;
    }
    //帮助某个学生退课
    @PostMapping("/admin/quitCourse/{courseId}/{studentId}")
    public String quitCourse(@PathVariable String courseId,@PathVariable String studentId, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("管理员")){
            return null;
        }
        return iAdminService.quitCourse(courseId,studentId);
    }
}
