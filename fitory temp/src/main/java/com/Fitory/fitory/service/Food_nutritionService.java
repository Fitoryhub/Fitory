package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.entity.Food_nutrition;
import com.Fitory.fitory.repository.Food_nutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Food_nutritionService implements IF_Food_nutritionService{
	
	private final Food_nutritionRepository fnrepo; 
	
	
	public List<Diet_food> insert(List<FoodlistDTO> flist, int did) {
		List<Diet_food> tempdf=new ArrayList<>();
		for(int i=0; i<flist.size(); i++) {
			Food_nutrition temp_f=new Food_nutrition();
			FoodlistDTO fvo=flist.get(i);
			Diet_food dfvo=new Diet_food();
			dfvo.setFoodname(fvo.getFood_name());
			temp_f.setDietId(did);
			temp_f.setCalories(fvo.getCalories());
			temp_f.setProtein(fvo.getProtein());
			temp_f.setCarbohydrate(fvo.getCarbohydrate());
			temp_f.setFat(fvo.getFat());
			temp_f.setStandard(fvo.getStandard());
			fnrepo.save(temp_f);
			tempdf.add(dfvo);
		}
		return tempdf;
	}
	
	public List<Integer> getidlist(int id) {
		return fnrepo.findBydietId(id);
	}

	public void delete(int did) {
		fnrepo.deleteAllBydietId(did);
	}

	@Override
	public int getCal(int id) {
		return fnrepo.getCaloriesByFood_nutrition_id(id);
	}

}
