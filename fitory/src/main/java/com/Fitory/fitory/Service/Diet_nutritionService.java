package com.Fitory.fitory.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Fitory.fitory.Entity.Diet_nutrition;
import com.Fitory.fitory.Repository.Diet_nutritionRepository;
import com.Fitory.fitory.VO.FoodlistVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Diet_nutritionService implements IF_Diet_nutritionService{
	
	private final Diet_nutritionRepository dnrepo;
	
	public void insert(int did, List<FoodlistVO> flist) {
		Diet_nutrition dn=new Diet_nutrition();
		int tcal=0, tpro=0, tcarbo=0, tfat=0;
		for(int i=0; i<flist.size(); i++) {
			tcal+=flist.get(i).getCalories();
			tcarbo+=flist.get(i).getCarbohydrate();
			tfat+=flist.get(i).getFat();
			tpro+=flist.get(i).getProtein();
		}
		dn.setDiet_id(did);
		dn.setCalories(tcal);
		dn.setCarbohydrate(tcarbo);
		dn.setFat(tfat);
		dn.setProtein(tpro);
		dnrepo.save(dn);
	}
}
