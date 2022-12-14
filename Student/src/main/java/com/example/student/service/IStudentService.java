package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.*;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IStudentService extends IService<Student> {
    Student login(long studentId, String password);
    Teacher getTeacherById(String teacherId);
    List<Course> getCourseByTeacherId(String teacherId);
    String joinCourse(long courseId,long studentId);
    List<Course> getAllList();
    List<Course> getCourseByType(String courseType);
    String quitCourse(long courseId ,long studentId);
    String getScore(long courseId, long studentId);
    List<Course> getMyCourse(long studentId);
    String submitExperiment(StudentExperiment studentExperiment);
    public void downLoad(@PathVariable long experimentId, HttpServletResponse response);
    public List<chapter> getCourseChapter(@PathVariable long courseId);
    public List<experiment> getChapterExperiment(@PathVariable long chapterId);
    public experiment getExperimentInfo(@PathVariable long experimentId);
    public StudentExperiment ifStudentExperiment(long experimentId,long studentId);
    boolean createFile(long experimentId,long studentId,String fileUrl,String fileName);
    boolean createCodeFile(long experimentId,long studentId,String fileUrl,String fileName);
    public StudentExperiment getMyExperiment(long experimentId,long studentId);
    public Course selectCourseByName(@PathVariable String courseName);
    long findChapterIdByExperimentId(long experimentId);
    long findCourseIdByChapterId(long chapterId);
    boolean insertComments(Comments comments);
    Integer addLikes(long commentId);
    Integer deleteLikes(long commentId);
    Integer getLikes(long commentId);
}
