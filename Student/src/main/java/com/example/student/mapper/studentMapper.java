package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface studentMapper extends BaseMapper<Student> {
    Student findStudentByStudentIdAndPassword(long studentId, String password);
}
