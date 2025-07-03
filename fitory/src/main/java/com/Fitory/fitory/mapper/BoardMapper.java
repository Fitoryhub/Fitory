package com.Fitory.fitory.mapper;

import com.Fitory.fitory.dto.BoardDTO;
import com.Fitory.fitory.entity.Board;
import org.apache.ibatis.annotations.Param;

public interface BoardMapper {
    public BoardDTO detailboard(@Param("uid") String uid, @Param("pnum") Integer pnum);
}
