package com.example.teacher.controller;
import com.example.commons.config.JwtConfig;
import com.example.commons.entity.*;
import com.example.commons.util.UploadFileUtil;
import com.example.teacher.entity.dto.LoginDTO;
import com.example.teacher.service.OssDownloadService;
import com.example.teacher.service.OssUploadService;
import com.example.teacher.service.impl.ITeacherServiceImpl;
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
    //老师登录
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
    //老师创建课程
    @PostMapping("/teacher/createCourse")
    public String createCourse(@RequestBody Course course,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.creatCourse(course);
    }
    //老师查看学生信息
    @GetMapping("/teacher/getStudentById/{studentId}")
    public Student getStudentById(@PathVariable String studentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getStudentById(studentId);
    }
    //查看我的课程
    @GetMapping("/teacher/myCourse")
    public List<Course> myCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.myCourse(jwtConfig.getUserIdFromToken(token));
    }
    //查看我的课程中的学生
    @GetMapping("/teacher/myCourseStudent/{courseId}")
    public List<Student> myCourseStudent(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.myCourseStudent(courseId);
    }
    //删除课程
    @PostMapping("/teacher/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.deleteCourse(courseId);
    }
    //创建章节
    @PostMapping("/teacher/createChapter/")
    public String createCourse(@RequestBody chapter chapter,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.createChapter(chapter);
    }
    //删除章节
    @PostMapping("/teacher/deleteChapter/{chapterId}")
    public String deleteChapter(@PathVariable String chapterId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.deleteChapter(chapterId);
    }
    //创建实验
    @PostMapping("/teacher/createExperiment/")
    public String createExperiment(@RequestBody experiment experiment,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.createExperiment(experiment);
    }
    //删除实验
    @PostMapping("/teacher/deleteExperiment/{experimentId}")
    public String deleteExperiment(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.deleteExperiment(experimentId);
    }
    //上传实验指导
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
    //下载学生上传的文件
    @GetMapping("/teacher/download/file/{experimentId}/{studentId}")
    public void downLoad(@PathVariable String experimentId,@PathVariable String studentId, HttpServletResponse response){
        iTeacherService.downLoad(experimentId,studentId,response);
    }
    //提供获取老师信息的接口
    @GetMapping("/student/getTeacher/{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId){
        return iTeacherService.getById(teacherId);
    }
    //获取每个课程的章节
    @GetMapping("/teacher/getChapter/{courseId}")
    public List<chapter> getCourseChapter(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getCourseChapter(courseId);
    }
    //获取每个章节下的实验
    @GetMapping("/teacher/getExperiment/{chapterId}")
    public List<experiment> getChapterExperiment(@PathVariable String chapterId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getChapterExperiment(chapterId);
    }
    //获取单个实验的详细信息
    @GetMapping("/teacher/getExperimentInfo/{experimentId}")
    public experiment getExperimentInfo(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getExperimentInfo(experimentId);
    }
    //获取单个实验中已经提交了报告的学生报告
    @GetMapping("/teacher/getStudentExperiment/{experimentId}")
    public List<StudentExperiment> getStudentExperiment(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.getStudentExperiment(experimentId);
    }
    //获取的单个实验中未提交报告的人数
    @GetMapping("/teacher/getNumOfNotExperiment/{experimentId}")
    public int getNumOfNotExperiment(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return -1;
        }
        return iTeacherService.getNumOfNotExperiment(experimentId);
    }
    //给学生的实验打分并更改状态为已批改
    @PostMapping("/teacher/updateScoreOfExperiment/{experimentId}/{studentId}/{score}")
    public String updateScoreOfExperiment(@PathVariable String experimentId,@PathVariable String studentId,@PathVariable int score,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("老师")){
            return null;
        }
        return iTeacherService.updateScoreOfExperiment(experimentId,studentId,score);
    }
}
