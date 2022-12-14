package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_experiment")
public class StudentExperiment {
    @TableId("student_experiment_id")
    private long studentExperimentId;
    private long experimentId;
    private long studentId;
    private String experiment_content;
    private String fileUrl;
    private String fileName;
    private int score;
    private int state;
    private String codeFilePath;
    private String codeFileName;
}
