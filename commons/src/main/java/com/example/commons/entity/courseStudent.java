package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_student")
public class courseStudent {
    @TableId("relation_id")
    private long relationId;
    private long courseId;
    private long studentId;
    private String courseScore;

}
