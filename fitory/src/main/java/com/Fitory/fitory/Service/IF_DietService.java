package com.Fitory.fitory.Service;

import com.Fitory.fitory.Entity.Diet;

public interface IF_DietService {
	
	public void insert(Diet diet);
	public int getid(String title);
	public void delete();
}
