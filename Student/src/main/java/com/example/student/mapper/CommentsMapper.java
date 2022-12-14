package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {
    Integer getLikes(long commentId);
    Integer updateLikes(long commentId,int num);
}
