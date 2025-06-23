package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet;

import java.util.List;
import java.util.Map;

public interface IF_DietService {
	
	public void insert(Diet diet);
	public void delete(int did);
	public void updatview(Diet diet);
	public int getid(String title);
	public Diet getone(int did);
	public Map<String, Object> serlist(int page);

	List<Diet> selectdiet_id(String userid);

	List<Diet> findbyuserId(String id);
}