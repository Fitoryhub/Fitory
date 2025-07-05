package com.Fitory.fitory.mapper;

import com.Fitory.fitory.dto.detailfood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietMapper {

    List<detailfood> findDietDetailsByUserIdAndDate( String userid, String date);
}
