package com.Fitory.fitory.service;

import java.util.List;

import com.Fitory.fitory.dto.FoodlistDTO;

public interface IF_Diet_nutritionService {
	
	public void insert(int did, List<FoodlistDTO> flist);
}
