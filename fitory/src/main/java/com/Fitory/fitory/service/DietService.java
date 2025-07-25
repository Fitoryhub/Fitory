package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.PageDTO;
import com.Fitory.fitory.entity.Diet;
import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.repository.DietRepository;
import com.Fitory.fitory.repository.Diet_foodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DietService implements IF_DietService{

	private final Diet_foodRepository dietfoodRepository;

	private final DietRepository dietrespo;
	
	public void insert(Diet diet) {
		dietrespo.save(diet);
	}

	public void delete(int did) { dietrespo.deleteById(did);}

	public void updatview(Diet diet) { dietrespo.save(diet); }

	public int getid(String title) {
		return dietrespo.findBytitle(title);
	}
	
	public Diet getone(int did){
		Optional<Diet> temp1=dietrespo.findById(did);
        return temp1.orElseGet(() -> null);
	}

	public Map<String, Object> serlist(int page){
		PageDTO pvo = new PageDTO();
		pvo.setPage(page);
		pvo.setPerPageNum(10);
		pvo.setTotalCount((int) dietrespo.count());
		int start=pvo.getStartNo();
		int end=pvo.getEndNo();
		Map<String,Object> temp=new HashMap<String,Object>();
		List<Diet> dietList=dietrespo.findpage(start,end);
		temp.put("dlist",dietList);
		temp.put("page",pvo);
		return temp;
	}

	@Override
	public List<Diet> selectdiet_id(String userid) {
		return dietrespo.findByUserid(userid);
	}

	@Override
	public List<Diet> findbyuserId(String id) {
		return dietrespo.findByUserid(id);
	}

	@Override
	public List<Long> findbyUserIdandCreateday(String id, String date) {
		return dietrespo.findDietid(id, date);
	}

	@Override
	public List<Long> findDietIdsByUserid(String id) {
		return dietrespo.findDietIdsByUserid(id);
	}

	@Override
	public List<Diet_food> findonedaymeal(Long did) {
		return dietfoodRepository.findByDietId(Math.toIntExact(did));
	}
}