package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comments")
public class comments {
    private String commentId;
    private String commentText;
    private String experimentId;
    private String studentId;
}
