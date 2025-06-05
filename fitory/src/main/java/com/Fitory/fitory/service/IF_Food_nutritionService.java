package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.VO.FoodlistVO;

import java.util.List;

public interface IF_Food_nutritionService {
	
	public List<Diet_food> insert(List<FoodlistVO> flist, int did);
	public int[] getidlist(int id);
}
