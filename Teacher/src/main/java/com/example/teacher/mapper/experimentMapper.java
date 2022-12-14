package com.example.teacher.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.experiment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface experimentMapper extends BaseMapper<experiment> {
    public boolean setFile(String experimentId,String fileUrl,String fileName);
    List<experiment> getExperimentByChapterId(String chapterId);
}
