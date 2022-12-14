package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course")
public class Course {
    @TableId("course_id")
    private long courseId;
    private String courseName;
    private String teacherId;
    private String courseHours;
    private String courseCredit;
    private String courseType;
    private int courseStudentNum;

}
