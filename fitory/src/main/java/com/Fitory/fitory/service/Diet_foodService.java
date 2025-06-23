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
	
	public void insert(int did, List<Integer> fnid, List<Diet_food> dflist) {
		for(int i=0; i<fnid.size(); i++) {
			Diet_food df=new Diet_food();
			df.setDietId(did);
			df.setFood_nutrition_id(fnid.get(i));
			df.setFoodname(dflist.get(i).getFoodname());
			dfrepo.save(df);
		}
	}

	public List<Diet_food> getdflist(int did){
		List<Diet_food> dflist=dfrepo.findAllByDiet_id(did);
		return dflist;
	}

	public void delete(int did) {
		dfrepo.deleteAllByDietId(did);
	}

	@Override
	public List<Diet_food> findBy(int dietId) {
		return dfrepo.findByDietId(dietId);
	}

	@Override
	public Diet_food findByfoodname(String name) {
		return dfrepo.findByFoodname(name);
	}
}