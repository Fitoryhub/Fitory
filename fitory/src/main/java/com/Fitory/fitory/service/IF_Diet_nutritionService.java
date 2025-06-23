package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_nutrition;

import java.util.List;

public interface IF_Diet_nutritionService {
	
	public void insert(int did, List<FoodlistDTO> flist);
	public Diet_nutrition getone(int did);

    public void delete(int did);
	Diet_nutrition finddid(int did);
}
