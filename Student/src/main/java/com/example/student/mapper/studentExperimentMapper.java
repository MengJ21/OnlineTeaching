package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.StudentExperiment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface studentExperimentMapper extends BaseMapper<StudentExperiment> {
    StudentExperiment ifStudentExperiment(String experimentId,String studentId);
    boolean setFile(String experimentId,String studentId,String fileUrl,String fileName);
}
