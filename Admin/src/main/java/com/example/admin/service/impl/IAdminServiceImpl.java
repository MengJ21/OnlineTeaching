package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.mapper.courseMapper;
import com.example.admin.mapper.courseStudentMapper;
import com.example.admin.mapper.teacherMapper;
import com.example.admin.service.IAdminService;
import com.example.commons.entity.Course;
import com.example.commons.entity.Student;
import com.example.commons.entity.Teacher;
import com.example.commons.entity.courseStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    com.example.admin.mapper.courseMapper courseMapper;
    @Autowired
    com.example.admin.mapper.courseStudentMapper courseStudentMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    teacherMapper teacherMapper;
    @Override
    public Admin login(String adminId, String password) {
        return adminMapper.findAdminByIdAndPassword(adminId,password);
    }

    @Override
    public String registerForTeacher(Teacher teacher) {
        if(teacherMapper.insert(teacher)!=0){
            return "创建成功";
        }else {
            return "创建失败";
        }
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
    public List<Course> getCourseByTeacherId(String teacherId) {
        return courseMapper.getCourseByTeacherId(teacherId);
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
    public Course selectCourseByName(String courseName) {
        return courseMapper.selectCourseByName(courseName);
    }

    @Override
    public String joinCourse(String courseId, String studentId) {
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
}
