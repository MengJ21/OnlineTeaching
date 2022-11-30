package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;


import java.util.List;

public interface IStudentService extends IService<Student> {
    Student login(String studentId, String password);
    Teacher getTeacherById(String teacherId);
    List<Course> getCourseByTeacherId(String teacherId);
    String joinCourse(String courseId,String studentId);
    List<Course> getAllList();
    List<Course> getCourseByType(String courseType);
    String quitCourse(String courseId ,String studentId);
    String getScore(String courseId, String studentId);
    List<Course> getMyCourse(String studentId);
}
