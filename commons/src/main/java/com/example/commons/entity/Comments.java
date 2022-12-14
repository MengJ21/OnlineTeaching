package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
public class Comments {
    private long commentId;
    private String commentText;
    private long parentId;
    private long studentId;
    private Integer likes;
}
