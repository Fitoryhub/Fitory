package com.Fitory.fitory.Service;

import org.springframework.stereotype.Service;

import com.Fitory.fitory.Entity.Diet;
import com.Fitory.fitory.Repository.DietRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DietService {

	private final DietRepository dietrespo;
	
	public void insert(Diet diet) {
		dietrespo.save(diet);
	}
	
	public int getid(String title) {
		return dietrespo.findBytitle(title);
	}
}
