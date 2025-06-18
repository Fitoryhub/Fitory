package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_food;

import java.util.List;

public interface IF_Food_nutritionService {
	
	public List<Diet_food> insert(List<FoodlistDTO> flist, int did);
	public int[] getidlist(int id);
	public int getCal(int id);
}
