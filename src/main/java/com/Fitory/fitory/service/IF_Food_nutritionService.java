package com.Fitory.fitory.service;

import java.util.List;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_food;

public interface IF_Food_nutritionService {
	
	public List<Diet_food> insert(List<FoodlistDTO> flist, int did);
	public int[] getidlist(int id);
}
