package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.entity.Food_nutrition;
import com.Fitory.fitory.repository.Food_nutritionRepository;
import com.Fitory.fitory.VO.FoodlistVO;
import com.Fitory.fitory.entity.Diet_food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Food_nutritionService implements IF_Food_nutritionService{
	
	private final Food_nutritionRepository fnrepo; 
	
	
	public List<Diet_food> insert(List<FoodlistVO> flist, int did) {
		List<Diet_food> tempdf=new ArrayList<>();
		for(int i=0; i<flist.size(); i++) {
			Food_nutrition temp_f=new Food_nutrition();
			FoodlistVO fvo=flist.get(i);
			Diet_food dfvo=new Diet_food();
			dfvo.setFood_name(fvo.getFood_name());
			temp_f.setDiet_id(did);
			temp_f.setCalories(fvo.getCalories());
			temp_f.setProtein(fvo.getProtein());
			temp_f.setCarbohydrate(fvo.getCarbohydrate());
			temp_f.setFat(fvo.getFat());
			temp_f.setSodium(fvo.getSodium());
			temp_f.setSugar(fvo.getSugar());
			fnrepo.save(temp_f);
			tempdf.add(dfvo);
		}
		return tempdf;
	}
	
	public int[] getidlist(int id) {
		return fnrepo.findBydiet_id(id);
	}

}
