package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet;
import com.Fitory.fitory.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DietService implements IF_DietService{

	private final DietRepository dietrespo;
	
	public void insert(Diet diet) {
		dietrespo.save(diet);
	}
	
	public int getid(String title) {
		return dietrespo.findBytitle(title);
	}
	
	public void delete() {
		dietrespo.delete(null);
	}
}
