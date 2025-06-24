package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.entity.Food_nutrition;

import java.util.List;

public interface IF_Food_nutritionService {
	
	public List<Diet_food> insert(List<FoodlistDTO> flist, int did);
	public List<Integer> getidlist(int id);

    public void delete(int did);
	public int getCal(int id);


	Food_nutrition getid(int foodNutritionId);
}
