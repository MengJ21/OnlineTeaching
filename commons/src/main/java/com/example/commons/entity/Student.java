package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.LinkOption;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
public class Student {
    @TableId("student_id")
    private long studentId;
    private String studentName;
    @TableField("student_age")
    private String studentAge;
    private String studentGender;
    private String studentPhone;
    private String password;

}
