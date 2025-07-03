package com.Fitory.fitory.mapper;

import com.Fitory.fitory.dto.RepliesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RepliesMapper {

    List<RepliesDTO> replielistandlike(@Param("uid") String uid , @Param("num") Integer num);
}

