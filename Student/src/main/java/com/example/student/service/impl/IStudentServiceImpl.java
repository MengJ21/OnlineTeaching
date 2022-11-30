package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import com.example.commons.entity.courseStudent;
import com.example.student.mapper.*;
import com.example.student.service.IStudentService;
import com.example.student.service.client.TeacherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.List;

@Service
public class IStudentServiceImpl extends ServiceImpl<studentMapper, Student> implements IStudentService {
    @Resource
    studentMapper studentMapper;
    @Resource
    TeacherClient teacherClient;
    @Resource
    courseMapper courseMapper;
    @Resource
    courseStudentMapper courseStudentMapper;

    @Override
    public Student login(String studentId, String password) {
        return studentMapper.findStudentByStudentIdAndPassword(studentId, password);
    }

    @Override
    public Teacher getTeacherById(String teacherId) {
        return teacherClient.getTeacher(teacherId);
    }

    @Override
    public List<Course> getCourseByTeacherId(String teacherId) {
        return courseMapper.getCourseByTeacherId(teacherId);
    }

    @Override
    public String joinCourse(String courseId,String studentId) {
        courseStudent courseStudent1 = courseStudentMapper.selectRelation(courseId,studentId);
        if(courseStudent1==null){
            courseStudent courseStudent = new courseStudent();
            courseStudent.setCourseId(courseId);
            courseStudent.setStudentId(studentId);
            int i = courseStudentMapper.insert(courseStudent);
            Course course = courseMapper.selectById(courseId);
            int num = course.getCourseStudentNum();
            num = num + 1;
            if(i != 0 && courseMapper.updateStudent(courseId,num)){
                return "加入班级成功！";
            }else {
                return "加入班级失败！";
            }
        }else {
            return "已经选择过本门课程";
        }
    }

    @Override
    public List<Course> getAllList() {
        return courseMapper.selectList(null);
    }

    @Override
    public List<Course> getCourseByType(String courseType) {
        return courseMapper.getCourseByType(courseType);
    }

    @Override
    public String quitCourse(String courseId, String studentId) {
        courseStudent courseStudent = courseStudentMapper.selectRelation(courseId,studentId);
        if(courseStudent==null){
            return "未选本门课程！";
        }else {
            int i = courseStudentMapper.deleteById(courseStudent.getRelationId());
            Course course = courseMapper.selectById(courseId);
            int num = course.getCourseStudentNum();
            num = num - 1;
            if(i != 0 && courseMapper.updateStudent(courseId,num)){
                return "退出班级成功！";
            }else {
                return "退出班级失败！";
            }
        }
    }

    @Override
    public String getScore(String courseId, String studentId) {
        courseStudent courseStudent = courseStudentMapper.selectRelation(courseId,studentId);
        if(courseStudent==null){
            return "未学习过本门课程或者成绩未出！";
        }else {
            return courseStudent.getCourseScore();
        }
    }

    @Override
    public List<Course> getMyCourse(String studentId) {
        return courseMapper.getMyCourse(studentId);
    }
    public String upload(@RequestPart("file") MultipartFile multipartFile, @PathVariable String courseId, @PathVariable String chapterId, @PathVariable String experimentId){
        return teacherClient.upload(multipartFile,courseId,chapterId,experimentId);
    }
}
