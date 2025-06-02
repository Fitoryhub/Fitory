package com.Fitory.fitory.Service;

import java.util.List;

import com.Fitory.fitory.Entity.Diet_food;
import com.Fitory.fitory.VO.FoodlistVO;

public interface IF_Food_nutritionService {
	
	public List<Diet_food> insert(List<FoodlistVO> flist, int did);
	public int[] getidlist(int id);
}
