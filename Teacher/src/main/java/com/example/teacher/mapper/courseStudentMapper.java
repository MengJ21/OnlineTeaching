package com.example.teacher.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Student;
import com.example.commons.entity.courseStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface courseStudentMapper extends BaseMapper<courseStudent> {
    courseStudent selectRelation(String courseId, String studentId);
    List<Student> selectStudentIdByCourseId(String courseId);
    Boolean deleteRelation(String courseId);
}
