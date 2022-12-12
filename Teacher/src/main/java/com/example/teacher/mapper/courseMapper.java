package com.example.teacher.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface courseMapper extends BaseMapper<Course> {
    List<Course> getCourseByTeacherId(String teacherId);
    boolean updateStudent(String courseId, int courseStudentNum);
    List<Course> getCourseByType(String courseType);
    List<Course> getMyCourse(String studentId);
    int selectCourseNumByExperiment(String experimentId);
}
