package com.example.student.controller;


import com.example.commons.config.JwtConfig;
import com.example.commons.entity.*;
import com.example.commons.util.FileUtils;
import com.example.student.entity.dto.CodeDTO;
import com.example.student.entity.dto.LoginDTO;
import com.example.student.entity.dto.StudentExperimentContent;
import com.example.student.service.OssDownloadService;
import com.example.student.service.OssUploadService;
import com.example.student.service.RunCode;
import com.example.student.service.impl.IStudentServiceImpl;
import com.example.student.util.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class StudentController {
    @Autowired
    IStudentServiceImpl iStudentService;

    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    OssUploadService ossUploadService;

    @Autowired
    OssDownloadService ossDownloadService;

    @Autowired
    RunCode runCode;

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
    public ResponseEntity<StudentExperimentContent> getMyExperiment(@PathVariable String experimentId, HttpServletRequest request){
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        StudentExperiment studentExperiment = iStudentService.getMyExperiment(experimentId,jwtConfig.getUserIdFromToken(token));
        // 获取已经存在的代码文件的路径并提取内容返回给前端。
        String existCodeUrl = studentExperiment.getCodeFilePath();
        String code = ossDownloadService.getFile(existCodeUrl);
        return ResponseEntity.ok(new StudentExperimentContent(studentExperiment,code));
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
    @PostMapping("/student/onlineCoding/{experimentId}")
    public ResponseEntity<Object> onlineCoding(@RequestBody CodeDTO codeDTO, @PathVariable String experimentId, HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());
        if(!jwtConfig.getUserIdentityFromToken(token).equals("学生")){
            return null;
        }
        log.info("获取学生id，章节id和课程id");
        String studentId = jwtConfig.getUserIdFromToken(token);
        String chapterId = iStudentService.findChapterIdByExperimentId(experimentId);
        String courseId = iStudentService.findCourseIdByChapterId(chapterId);
        ProcessResult processResult = null;
        try {
            processResult = runCode.runCode(codeDTO.getType(), codeDTO.getContent());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("获取代码文件，并上传到云端");
        File code = new File(processResult.getCodeFilePath());
        FileItem fileItem = FileUtils.getMultipartFile(code, "Main");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        log.info("multipartFile文件的名称：" + multipartFile.getName());
        log.info("获取存储的文件名称：");
        String originalFilename = multipartFile.getOriginalFilename();
        log.info(originalFilename);
        String path = ossUploadService.uploadFile(multipartFile, courseId, chapterId, experimentId);
        log.info("删除本地的临时文件");
        code.delete();
        log.info("将文件路径与名称存储到数据库。");
        if (iStudentService.ifStudentExperiment(experimentId,jwtConfig.getUserIdFromToken(token))!=null){
            // 学生的实验内容已经创建，只需要更新代码内容即可。
            if (iStudentService.createCodeFile(experimentId,studentId,path,originalFilename)) {
                return ResponseEntity.ok(new CodeDTO("编译成功",processResult.getOutput()));
            } else {
                return ResponseEntity.ok("编译失败。");
            }
        } else {
            // 学生的实验内容未创建，需要新建实验内容。
            StudentExperiment studentExperiment = new StudentExperiment();
            studentExperiment.setCodeFileName(originalFilename);
            studentExperiment.setCodeFilePath(path);
            studentExperiment.setStudentId(studentId);
            studentExperiment.setExperimentId(experimentId);
            String res = iStudentService.submitExperiment(studentExperiment);
            if (Objects.equals(res, "提交实验成功")) {
                return ResponseEntity.ok(new CodeDTO("编译成功",processResult.getOutput()));
            } else {
                return ResponseEntity.ok("编译失败。");
            }

        }
    }

}
