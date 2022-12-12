package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.entity.*;
import com.example.student.mapper.*;
import com.example.student.service.IStudentService;
import com.example.student.service.OssDownloadService;
import com.example.student.service.client.TeacherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    studentExperimentMapper studentExperimentMapper;
    @Resource
    OssDownloadService ossDownloadService;
    @Resource
    experimentMapper experimentMapper;
    @Resource
    chapterMapper chapterMapper;
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

    @Override
    public String submitExperiment(StudentExperiment studentExperiment) {
        if(studentExperimentMapper.insert(studentExperiment)!=1){
            return "提交实验成功";
        }else {
            return "提交实验失败";
        }
    }

    @Override
    public void downLoad(String experimentId, HttpServletResponse response) {
        experiment experiment = experimentMapper.selectById(experimentId);
        ossDownloadService.download(response,experiment.getFileUrl(),experiment.getFileName());
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
    public StudentExperiment ifStudentExperiment(String experimentId, String studentId) {
        return studentExperimentMapper.ifStudentExperiment(experimentId,studentId);
    }

    @Override
    public boolean createFile(String experimentId,String studentId, String fileUrl, String fileName) {
        return studentExperimentMapper.setFile(experimentId,studentId,fileUrl,fileName);
    }

    @Override
    public StudentExperiment getMyExperiment(String experimentId, String studentId) {
        return studentExperimentMapper.ifStudentExperiment(experimentId,studentId);
    }

    @Override
    public Course selectCourseByName(String courseName) {
        return courseMapper.selectCourseByName(courseName);
    }

    public String upload(@RequestPart("file") MultipartFile multipartFile, @PathVariable String courseId, @PathVariable String chapterId, @PathVariable String experimentId){
        return teacherClient.upload(multipartFile,courseId,chapterId,experimentId);
    }

}
