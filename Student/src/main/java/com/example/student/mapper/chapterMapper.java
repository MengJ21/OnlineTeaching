package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Course;
import com.example.commons.entity.chapter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface chapterMapper extends BaseMapper<chapter> {
    List<chapter> getChapterByCourseId(String courseId);
}
