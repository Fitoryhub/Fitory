package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_food;

import java.util.List;

public interface IF_DIet_foodService {
	public void insert(int did, List<Integer> fnid, List<Diet_food> dflist, List<FoodlistDTO> flist);

	public List<Diet_food> getdflist(int did);

    public void delete(int did);

	List<Diet_food> findBy(int dietId);

	Diet_food findByfoodname(String name);


}
