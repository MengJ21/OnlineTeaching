package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Admin;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IAdminService extends IService<Admin> {
    Admin login(String adminId, String password);
    public String registerForTeacher( Teacher teacher);
    String deleteCourse(String courseId);
    List<Course> getCourseByTeacherId(String teacherId);
    List<Course> getAllList();
    List<Course> getCourseByType(String courseType);
    public Course selectCourseByName(@PathVariable String courseName);
    String joinCourse(String courseId,String studentId);
    String quitCourse(String courseId ,String studentId);
}
