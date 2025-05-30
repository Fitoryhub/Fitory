package com.Fitory.fitory.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Fitory.fitory.Entity.Diet_food;
import com.Fitory.fitory.Repository.Diet_foodRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Diet_foodService {
	
	private final Diet_foodRepository dfrepo;
	
	public void insert(int did, int[] fnid, List<Diet_food> dflist) {
		for(int i=0; i<fnid.length; i++) {
			Diet_food df=new Diet_food();
			df.setDiet_id(did);
			df.setFood_nutrition_id(fnid[i]);
			df.setFood_name(dflist.get(i).getFood_name());
			dfrepo.save(df);
		}
	}
}
