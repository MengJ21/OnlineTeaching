package com.example.teacher.controller;
import com.example.commons.config.JwtConfig;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import com.example.commons.entity.chapter;
import com.example.teacher.entity.dto.LoginDTO;
import com.example.teacher.service.OssUploadService;
import com.example.teacher.service.impl.ITeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class teacherController {
    @Autowired
    ITeacherServiceImpl iTeacherService;
    @Autowired
    OssUploadService ossUploadService;
    @Autowired
    JwtConfig jwtConfig;

    Teacher teacher = new Teacher();

    @PostMapping ("/teacher/login")
    public ResponseEntity<Object> teacherLogin(@RequestBody LoginDTO loginDTO){
        // 将学生的id存入token中，作为以后访问接口的凭证。
        String token = jwtConfig.createToken(loginDTO.getTeacherId());
        // 根据用户名密码获取学生具体信息。
        Teacher teacher = iTeacherService.login(loginDTO.getTeacherId(),loginDTO.getPassword());
        // 将具体信息和token一起返回给前端。
        Map<String,Object> userInfo = new HashMap<>(2) {{
            put("token", token);
            put("user", teacher);
        }};
        if(teacher == null){
            // 这里用ResponseEntity来封装返回值。
            return ResponseEntity.ok("登录失败！");
        }
        else {
            return  ResponseEntity.ok(userInfo);
        }
    }
    @PostMapping("/teacher/createCourse")
    public String createCourse(@RequestBody Course course){
        return iTeacherService.creatCourse(course);
    }

    @GetMapping("/teacher/getStudentById/{studentId}")
    public Student getStudentById(@PathVariable String studentId){
        return iTeacherService.getStudentById(studentId);
    }

    @GetMapping("/teacher/myCourse")
    public List<Course> myCourse(){
        return iTeacherService.myCourse(this.teacher.getTeacherId());
    }

    @GetMapping("/teacher/myCourseStudent/{courseId}")
    public List<Student> myCourseStudent(@PathVariable String courseId){
        return iTeacherService.myCourseStudent(courseId);
    }
    @PostMapping("/teacher/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId){
        return iTeacherService.deleteCourse(courseId);
    }
    private String url;
    @PostMapping("/teacher/createChapter/")
    public String createCourse(@RequestBody chapter chapter){
        return iTeacherService.createChapter(chapter);
    }
    @PostMapping("/teacher/upload/file/{courseId}/{chapterId}/{experimentId}")
    public String upload(@RequestPart("file")MultipartFile multipartFile,@PathVariable String courseId,@PathVariable String chapterId,@PathVariable String experimentId){
        if(multipartFile.isEmpty()){
            return "文件有误！";
        }
        long size = multipartFile.getSize();
        String originFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        return ossUploadService.uploadFile(multipartFile,courseId,chapterId,experimentId);
    }
    //提供获取老师信息的接口
    @GetMapping("/student/getTeacher/{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId){
        return iTeacherService.getById(teacherId);
    }
}
