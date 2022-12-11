package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.experiment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface experimentMapper extends BaseMapper<experiment> {
    List<experiment> getExperimentByChapterId(String chapterId);
}
