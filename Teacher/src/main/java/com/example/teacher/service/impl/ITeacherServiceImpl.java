package com.example.teacher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.entity.*;
import com.example.teacher.mapper.*;
import com.example.teacher.service.ITeacherService;
import com.example.teacher.service.OssDownloadService;
import com.example.teacher.service.client.StudentClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ITeacherServiceImpl extends ServiceImpl<teacherMapper, Teacher> implements ITeacherService {

    @Resource
    teacherMapper teacherMapper;
    @Resource
    StudentClient studentClient;
    @Resource
    courseMapper courseMapper;
    @Resource
    chapterMapper chapterMapper;
    @Resource
    courseStudentMapper courseStudentMapper;
    @Resource
    experimentMapper experimentMapper;
    @Resource
    OssDownloadService ossDownloadService;
    @Resource
    studentExperimentMapper studentExperimentMapper;
    @Override
    public Teacher login(String teacherId, String password) {
        return teacherMapper.findTeacherByTeacherIdAndPassword(teacherId,password);
    }

    @Override
    public String creatCourse(Course course) {
        int i = courseMapper.insert(course);
        if(i != 0){
            return "创建课程成功！";
        }else
            return "创建课程失败！";
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentClient.getStudent(studentId);
    }

    @Override
    public List<Course> myCourse(String teacherId) {
        return courseMapper.getCourseByTeacherId(teacherId);
    }

    @Override
    public List<Student> myCourseStudent(String courseId) {
        List<Student> students = courseStudentMapper.selectStudentIdByCourseId(courseId);
        return students;
    }

    @Override
    public String deleteCourse(String courseId) {
        if(courseMapper.selectById(courseId).getCourseStudentNum()==0){
            int i = courseMapper.deleteById(courseId);
            if(i!=0){
                return "删除课程成功！";
            }else {
                return "删除课程失败！";
            }
        }else {
            int i = courseMapper.deleteById(courseId);
            if(i!=0&&courseStudentMapper.deleteRelation(courseId)){
                return "删除课程成功！";
            }else {
                return "删除课程失败！";
            }
        }
    }

    @Override
    public String createChapter(chapter chapter) {
        if(chapterMapper.insert(chapter) != 0){
            return "创建章节成功！";
        }else {
            return "创建章节失败！";
        }
    }

    @Override
    public String createExperiment(experiment experiment) {
        if(experimentMapper.insert(experiment)!=0){
            return "创建实验成功！";
        }else {
            return "创建实验失败！";
        }
    }

    @Override
    public boolean createFile(String experimentId, String fileUrl, String fileName) {
        return experimentMapper.setFile(experimentId,fileUrl,fileName);
    }

    @Override
    public void downLoad(String experimentId,String studentId, HttpServletResponse response) {
        StudentExperiment studentExperiment = studentExperimentMapper.ifStudentExperiment(experimentId,studentId);
        ossDownloadService.download(response,studentExperiment.getFileUrl(),studentExperiment.getFileName());
    }

    @Override
    public List<chapter> getCourseChapter(String courseId) {
        return chapterMapper.getChapterByCourseId(courseId);
    }

    @Override
    public List<experiment> getChapterExperiment(String chapterId) {
        return experimentMapper.getExperimentByChapterId(chapterId);
    }

    @Override
    public experiment getExperimentInfo(String experimentId) {
        return experimentMapper.selectById(experimentId);
    }

    @Override
    public List<StudentExperiment> getStudentExperiment(String experimentId) {
        return studentExperimentMapper.getStudentExperimentByExperimentId(experimentId);
    }

    @Override
    public int getNumOfNotExperiment(String experimentId) {
        return courseMapper.selectCourseNumByExperiment(experimentId)-studentExperimentMapper.getStudentNum(experimentId);
    }

    @Override
    public String deleteChapter(String chapterId) {
        if(chapterMapper.deleteById(chapterId)!=0){
            return "删除章节成功";
        }else {
            return "删除章节失败";
        }
    }

    @Override
    public String deleteExperiment(String experimentId) {
        if(experimentMapper.deleteById(experimentId)!=0){
            return "删除实验成功";
        }else {
            return "删除实验失败";
        }
    }

    @Override
    public String updateScoreOfExperiment(String experimentId, String studentId,int score) {
        StudentExperiment studentExperiment = studentExperimentMapper.ifStudentExperiment(experimentId,studentId);
        studentExperiment.setScore(score);
        studentExperiment.setState(true);
        if(studentExperimentMapper.updateById(studentExperiment)!=0){
            return "批改成功";
        }else {
            return "批改失败";
        }
    }
}
