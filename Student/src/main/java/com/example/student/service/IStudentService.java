package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.*;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletResponse;
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
    String submitExperiment(StudentExperiment studentExperiment);
    public void downLoad(@PathVariable String experimentId, HttpServletResponse response);
    public List<chapter> getCourseChapter(@PathVariable String courseId);
    public List<experiment> getChapterExperiment(@PathVariable String chapterId);
    public experiment getExperimentInfo(@PathVariable String experimentId);
    public StudentExperiment ifStudentExperiment(String experimentId,String studentId);
    boolean createFile(String experimentId,String studentId,String fileUrl,String fileName);
    public StudentExperiment getMyExperiment(String experimentId,String studentId);
    public Course selectCourseByName(@PathVariable String courseName);
}
