package com.Fitory.fitory.service;

import java.util.List;

import com.Fitory.fitory.entity.Diet_food;

public interface IF_DIet_foodService {
	public void insert(int did, int[] fnid, List<Diet_food> dflist);
}
