package com.example.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.*;

import java.util.List;

public interface ITeacherService extends IService<Teacher> {
    Teacher login(String teacherId,String password);
    String creatCourse(Course course);
    Student getStudentById(String studentId);
    List<Course> myCourse(String teacherId);
    List<Student> myCourseStudent(String courseId);
    String deleteCourse(String courseId);
    String createChapter(chapter chapter);
    String createChapter(experiment experiment);
}
