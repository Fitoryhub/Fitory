package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet_food;

import java.util.List;

public interface IF_DIet_foodService {
	public void insert(int did, int[] fnid, List<Diet_food> dflist);

	public List<Diet_food> getdflist(int did);
}
