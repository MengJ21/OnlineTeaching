package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Student;
import com.example.commons.entity.courseStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface courseStudentMapper extends BaseMapper<courseStudent> {
    courseStudent selectRelation(long courseId, long studentId);
    List<Student> selectStudentIdByCourseId(long courseId);
    Boolean deleteRelation(long courseId);
}
