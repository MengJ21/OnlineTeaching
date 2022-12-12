package com.example.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.*;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ITeacherService extends IService<Teacher> {
    Teacher login(String teacherId,String password);
    String creatCourse(Course course);
    Student getStudentById(String studentId);
    List<Course> myCourse(String teacherId);
    List<Student> myCourseStudent(String courseId);
    String deleteCourse(String courseId);
    String createChapter(chapter chapter);
    String createExperiment(experiment experiment);
    boolean createFile(String experimentId,String fileUrl,String fileName);
    public void downLoad(@PathVariable String experimentId, HttpServletResponse response);
}
