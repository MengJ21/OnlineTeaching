package com.example.teacher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.entity.*;
import com.example.teacher.mapper.*;
import com.example.teacher.service.ITeacherService;
import com.example.teacher.service.client.StudentClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        int i = courseMapper.deleteById(courseId);
        if(i!=0&&courseStudentMapper.deleteRelation(courseId)){
            return "删除课程成功！";
        }else {
            return "删除课程失败！";
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
    public String createChapter(experiment experiment) {
        if(experimentMapper.insert(experiment)!=0){
            return "创建实验成功！";
        }else {
            return "创建实验失败！";
        }
    }

}
