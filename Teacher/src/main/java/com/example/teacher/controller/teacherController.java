package com.example.teacher.controller;
import com.example.commons.config.JwtConfig;
import com.example.commons.entity.*;
import com.example.commons.util.UploadFileUtil;
import com.example.teacher.entity.dto.LoginDTO;
import com.example.teacher.service.OssDownloadService;
import com.example.teacher.service.OssUploadService;
import com.example.teacher.service.impl.ITeacherServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    UploadFileUtil uploadFile;
    @Autowired
    OssDownloadService ossDownloadService;
    Teacher teacher = new Teacher();

    @PostMapping ("/teacher/login")
    public ResponseEntity<Object> teacherLogin(@RequestBody LoginDTO loginDTO){
        // 将学生的id存入token中，作为以后访问接口的凭证。
        String token = jwtConfig.createToken(loginDTO.getTeacherId(),"老师");
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

    @PostMapping("/teacher/uploadImage")
    public String uploadImage(@RequestPart("file") MultipartFile file,HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return uploadFile.uploadFile(file);
    }
    @PostMapping("/teacher/createCourse")
    public String createCourse(@RequestBody Course course,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.creatCourse(course);
    }

    @GetMapping("/teacher/getStudentById/{studentId}")
    public Student getStudentById(@PathVariable String studentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getStudentById(studentId);
    }

    @GetMapping("/teacher/myCourse")
    public List<Course> myCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.myCourse(this.teacher.getTeacherId());
    }

    @GetMapping("/teacher/myCourseStudent/{courseId}")
    public List<Student> myCourseStudent(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.myCourseStudent(courseId);
    }
    @PostMapping("/teacher/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.deleteCourse(courseId);
    }
    private String url;
    @PostMapping("/teacher/createChapter/")
    public String createCourse(@RequestBody chapter chapter,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.createChapter(chapter);
    }
    @PostMapping("/teacher/createExperiment/")
    public String createExperiment(@RequestBody experiment experiment,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.createExperiment(experiment);
    }

    @PostMapping("/teacher/upload/file/{courseId}/{chapterId}/{experimentId}")
    public String upload(HttpServletRequest request,@RequestPart("file")MultipartFile multipartFile,@PathVariable String courseId,@PathVariable String chapterId,@PathVariable String experimentId){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        if(multipartFile.isEmpty()){
            return "文件有误！";
        }
        long size = multipartFile.getSize();
        String originFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        if(iTeacherService.createFile(experimentId,ossUploadService.uploadFile(multipartFile,courseId,chapterId,experimentId),multipartFile.getOriginalFilename())){
            return "上传成功！";
        }else {
            return "上传失败！";
        }
    }

    @GetMapping("/teacher/download/file/{experimentId}")
    public void downLoad(@PathVariable String experimentId, HttpServletResponse response){
        iTeacherService.downLoad(experimentId,response);
    }

    //提供获取老师信息的接口
    @GetMapping("/student/getTeacher/{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId){
        return iTeacherService.getById(teacherId);
    }
}
