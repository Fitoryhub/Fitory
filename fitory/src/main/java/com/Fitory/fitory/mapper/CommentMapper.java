package com.Fitory.fitory.mapper;

import com.Fitory.fitory.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CommentMapper {
    List<CommentDTO> commentlistandlike(@Param("num") Integer num, @Param("uid") String uid);
}
