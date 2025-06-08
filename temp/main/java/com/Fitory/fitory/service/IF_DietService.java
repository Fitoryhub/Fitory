package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet;

public interface IF_DietService {
	
	public void insert(Diet diet);
	public int getid(String title);
	public void delete();
}