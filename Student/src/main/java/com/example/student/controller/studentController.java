package com.example.student.controller;

import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import com.example.student.service.impl.IStudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class studentController {
    @Autowired
    IStudentServiceImpl iStudentService;

    Student student = new Student();

    @GetMapping("/student/login/{studentId}/{password}")
    public String studentLogin(@PathVariable String studentId,@PathVariable String password){
        this.student = (Student)iStudentService.login(studentId,password);
        if(this.student==null){
            return "please login in first";
        }
        else
            return  "welcome "+student.getStudentName();
    }

    @GetMapping("/student/getTeacherById/{teacherId}")
    public Teacher getTeacherById(@PathVariable String teacherId){
        return iStudentService.getTeacherById(teacherId);
    }

    @PostMapping("/student/getCourseByTeacherId/{teacherId}")
    public List<Course> getCourseByTeacherId(@PathVariable String teacherId){
        return iStudentService.getCourseByTeacherId(teacherId);
    }

    @GetMapping("/student/getAllCourse")
    public List<Course> getAllCourse(){
        return iStudentService.getAllList();
    }

    @PostMapping("/student/joinCourse/{courseId}")
    public String joinCourse(@PathVariable String courseId){
        return iStudentService.joinCourse(courseId,this.student.getStudentId());
    }

    @GetMapping("/student/getCourseByType/{courseType}")
    public List<Course> getCourseByType(@PathVariable String courseType){
        return iStudentService.getCourseByType(courseType);
    }

    @PostMapping("/student/quitCourse/{courseId}")
    public String quitCourse(@PathVariable String courseId){
        return iStudentService.quitCourse(courseId,this.student.getStudentId());
    }

    @GetMapping("/student/getCourseScore/{courseId}")
    public String getCourseScore(@PathVariable String courseId){
        return iStudentService.getScore(courseId,this.student.getStudentId());
    }

    @GetMapping("/student/getMyCourse")
    public List<Course> getMyCourse(){
        return iStudentService.getMyCourse(this.student.getStudentId());
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
