package com.Fitory.fitory.service;

import java.util.List;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_nutrition;

public interface IF_Diet_nutritionService {
	
	public void insert(int did, List<FoodlistDTO> flist);
	public Diet_nutrition getone(int did);

    public void delete(int did);
}
