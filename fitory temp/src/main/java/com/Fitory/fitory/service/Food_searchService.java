package com.Fitory.fitory.service;

import com.Fitory.fitory.repository.Food_searchRepository;
import com.Fitory.fitory.repository.Food_searchRepository.Foodlist;
import com.Fitory.fitory.VO.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class Food_searchService {
	
	private final Food_searchRepository fsrepo;
	
	public Map<String, Object> getList(String ftype, int p){
		PageVO pg=new PageVO();
		pg.setPage(p);
		pg.setTotalCount(fsrepo.countByclassification(ftype));
		int start=pg.getStartNo();
		int end=pg.getEndNo();
		Map<String, Object> temp= new HashMap<String,Object>();
		List<Foodlist> list1= fsrepo.findByclassification(ftype, start, end);
		temp.put("flist", list1);
		temp.put("page", pg);
		return temp;
	}
	
	public Foodlist getOne(String fname) {
		Foodlist fvo=fsrepo.findByFood_name(fname);
		return fvo;
	}
}
