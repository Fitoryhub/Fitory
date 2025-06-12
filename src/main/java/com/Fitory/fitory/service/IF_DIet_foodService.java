package com.Fitory.fitory.service;

import java.util.List;

import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.entity.Diet_nutrition;

public interface IF_DIet_foodService {
	public void insert(int did, int[] fnid, List<Diet_food> dflist);

	public List<Diet_food> getdflist(int did);

    List<Diet_food> findBy(int dietId);

	Diet_food findByfoodname(String name);
}
