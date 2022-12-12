package com.example.student.controller;


import com.example.commons.config.JwtConfig;
import com.example.commons.entity.*;
import com.example.student.entity.dto.LoginDTO;
import com.example.student.service.OssUploadService;
import com.example.student.service.impl.IStudentServiceImpl;
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
public class StudentController {
    @Autowired
    IStudentServiceImpl iStudentService;

    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    OssUploadService ossUploadService;

    //学生登录
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
    //学生注册
    @PostMapping("/student/register/")
    public String studentRegister(@RequestBody Student student){
        if(iStudentService.save(student)){
            return "注册成功！";
        }else {
            return "注册失败！";
        }

    }
    //学生查看老师信息
    @GetMapping("/student/getTeacherById/{teacherId}")
    public Teacher getTeacherById(@PathVariable String teacherId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getTeacherById(teacherId);
    }
    //学生查看某个老师的课程
    @PostMapping("/student/getCourseByTeacherId/{teacherId}")
    public List<Course> getCourseByTeacherId(@PathVariable String teacherId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getCourseByTeacherId(teacherId);
    }
    //学生查看所有课程
    @GetMapping("/student/getAllCourse")
    public List<Course> getAllCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getAllList();
    }
    //学生加入课程
    @PostMapping("/student/joinCourse/{courseId}")
    public String joinCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return iStudentService.joinCourse(courseId,jwtConfig.getUserIdFromToken(token));
        }
        return null;
    }
    //学生通过类型查看课程
    @GetMapping("/student/getCourseByType/{courseType}")
    public List<Course> getCourseByType(@PathVariable String courseType,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getCourseByType(courseType);
    }
    //学生退出课程
    @PostMapping("/student/quitCourse/{courseId}")
    public String quitCourse(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.quitCourse(courseId,jwtConfig.getUserIdFromToken(token));
    }
    //学生获取课程的成绩
    @GetMapping("/student/getCourseScore/{courseId}")
    public String getCourseScore(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getScore(courseId,jwtConfig.getUserIdFromToken(token));
    }
    //学生查看我的所有课程
    @GetMapping("/student/getMyCourse")
    public List<Course> getMyCourse(HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getMyCourse(jwtConfig.getUserIdFromToken(token));
    }
    //上传实验报告
    @PostMapping("/student/upload/file/{courseId}/{chapterId}/{experimentId}")
    public String upload(HttpServletRequest request,@RequestPart("file")MultipartFile multipartFile,@PathVariable String courseId,@PathVariable String chapterId,@PathVariable String experimentId){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        if(iStudentService.ifStudentExperiment(experimentId,jwtConfig.getUserIdFromToken(token))!=null){
            if(multipartFile.isEmpty()){
                return "文件有误！";
            }
            long size = multipartFile.getSize();
            String originFilename = multipartFile.getOriginalFilename();
            String contentType = multipartFile.getContentType();
            if(iStudentService.createFile(experimentId,jwtConfig.getUserIdFromToken(token),ossUploadService.uploadFile(multipartFile,courseId,chapterId,experimentId),multipartFile.getOriginalFilename())){
                return "上传成功！";
            }else {
                return "上传失败！";
            }
        }else {
            StudentExperiment studentExperiment = new StudentExperiment();
            studentExperiment.setStudentId(jwtConfig.getUserIdFromToken(token));
            studentExperiment.setExperimentId(experimentId);
            iStudentService.submitExperiment(studentExperiment);
            if(multipartFile.isEmpty()){
                return "文件有误！";
            }
            long size = multipartFile.getSize();
            String originFilename = multipartFile.getOriginalFilename();
            String contentType = multipartFile.getContentType();
            if(iStudentService.createFile(experimentId,jwtConfig.getUserIdFromToken(token),ossUploadService.uploadFile(multipartFile,courseId,chapterId,experimentId),multipartFile.getOriginalFilename())){
                return "上传成功！";
            }else {
                return "上传失败！";
            }
        }
    }
    //为老师提供查询学生的接口
    @GetMapping("/teacher/getStudent/{studentId}")
    public Student getStudent(@PathVariable String studentId){
        return iStudentService.getById(studentId);
    }
    //提交实验
    @PostMapping("/student/submitExperiment/")
    public String submitExperiment(@RequestBody StudentExperiment studentExperiment,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.submitExperiment(studentExperiment);
    }
    //下载老师上传的实验要求文件
    @GetMapping("/student/download/file/{experimentId}")
    public void downLoad(@PathVariable String experimentId, HttpServletResponse response,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            System.exit(0);
        }
        iStudentService.downLoad(experimentId,response);
    }
    //获取每个课程的章节
    @GetMapping("/student/getChapter/{courseId}")
    public List<chapter> getCourseChapter(@PathVariable String courseId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getCourseChapter(courseId);
    }
    //获取每个章节下的实验
    @GetMapping("/student/getExperiment/{chapterId}")
    public List<experiment> getChapterExperiment(@PathVariable String chapterId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getChapterExperiment(chapterId);
    }
    //获取单个实验的详细信息
    @GetMapping("/student/getExperimentInfo/{experimentId}")
    public experiment getExperimentInfo(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getExperimentInfo(experimentId);
    }
    //获取我的实验内容
    @GetMapping("/student/getMyExperiment/{experimentId}")
    public StudentExperiment getMyExperiment(@PathVariable String experimentId,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.getMyExperiment(experimentId,jwtConfig.getUserIdFromToken(token));
    }
    //通过名字来搜索课程
    @GetMapping("/student/selectCourseByName/{courseName}")
    public Course selectCourseByName(@PathVariable String courseName,HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        return iStudentService.selectCourseByName(courseName);
    }
}
