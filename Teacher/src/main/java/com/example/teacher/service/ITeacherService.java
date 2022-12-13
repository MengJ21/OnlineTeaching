package com.example.teacher.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public void downLoad(String experimentId,String studentId, HttpServletResponse response);
    public List<chapter> getCourseChapter(@PathVariable String courseId);
    public List<experiment> getChapterExperiment(@PathVariable String chapterId);
    public experiment getExperimentInfo(@PathVariable String experimentId);
    public List<StudentExperiment> getStudentExperiment(@PathVariable String experimentId);
    public int getNumOfNotExperiment(@PathVariable String experimentId);
    public String deleteChapter(@PathVariable String chapterId);
    public String deleteExperiment(@PathVariable String experimentId);
    public String updateScoreOfExperiment(@PathVariable String experimentId,@PathVariable String studentId,@PathVariable int score);
}
