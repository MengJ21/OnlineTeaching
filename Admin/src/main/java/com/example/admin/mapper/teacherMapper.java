package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface teacherMapper extends BaseMapper<Teacher> {
    Teacher findTeacherByTeacherIdAndPassword(String teacherId, String password);
}
