package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName
public class experiment {
    @TableId("experiment_id")
    private long experimentId;
    private long chapterId;
    private String experimentName;
    private String experimentText;
    private String fileUrl;
    private String fileName;
    private Date startTime;
    private Date endTime;
    private Boolean submitExperiment;
}
