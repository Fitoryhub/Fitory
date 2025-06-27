package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.entity.Diet_nutrition;
import com.Fitory.fitory.repository.Diet_nutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Diet_nutritionService implements IF_Diet_nutritionService{
	
	private final Diet_nutritionRepository dnrepo;
	
	public void insert(int did, List<FoodlistDTO> flist) {
		Diet_nutrition dn=new Diet_nutrition();
		int tcal=0, tpro=0, tcarbo=0, tfat=0;
		for(int i=0; i<flist.size(); i++) {
			int tg=flist.get(i).getAmount();
			tcal+=flist.get(i).getTotalcal();
			tcarbo+=flist.get(i).getCarbohydrate();
			tfat+=flist.get(i).getFat();
			tpro+=flist.get(i).getProtein();
		}
		dn.setDietid(did);
		dn.setCalories(tcal);
		dn.setCarbohydrate(tcarbo);
		dn.setFat(tfat);
		dn.setProtein(tpro);
		dnrepo.save(dn);
	}

	public Diet_nutrition getone(int did){
		Optional<Diet_nutrition> temp=dnrepo.findById(did);
        return temp.orElse(null);
	}

	public void delete(int did) {
		dnrepo.deleteById(did);
	}

	@Override
	public Diet_nutrition finddid(int did) {
		return dnrepo.findByDietid(did);
	}
}
