package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.repository.Diet_foodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class Diet_foodService implements IF_DIet_foodService{
	
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

	public List<Diet_food> getdflist(int did){
		List<Diet_food> dflist=dfrepo.findAllByDiet_id(did);
		return dflist;
	}
}