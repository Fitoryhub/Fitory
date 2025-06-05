package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.service.DietService;
import com.Fitory.fitory.service.Diet_foodService;
import com.Fitory.fitory.service.Diet_nutritionService;
import com.Fitory.fitory.service.Food_nutritionService;
import com.Fitory.fitory.VO.DietsaveVO;
import com.Fitory.fitory.VO.FoodlistVO;
import com.Fitory.fitory.VO.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
public class DietController {
	
	private final DietService dservice;
	private final Diet_foodService dfservice;
	private final Diet_nutritionService dnservice;
	private final Food_nutritionService fnservice;
	
	
	@GetMapping("/diet/board")
	public String board() {
		return "/diet/board";
	}
	
	@GetMapping("/diet/resist")
	public String resist() {
		return "/diet/resist";
	}
	
	@GetMapping("/diet/analyze")
	public String analyze() {
		return "/diet/analyze";
	}
	
	@PostMapping("/diet/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody DietsaveVO dsv) {
		dservice.insert(dsv.getDiet());
		List<FoodlistVO> flist=dsv.getFoodlist();
		int did=dservice.getid(dsv.getDiet().getTitle());
		List<Diet_food> dflist=fnservice.insert(flist, did);
		int[] fnid=fnservice.getidlist(did);
		dfservice.insert(did, fnid, dflist);
		dnservice.insert(did,flist);
		Map<String, String> response = new HashMap<>();
	    response.put("url", "/diet/board");
	    return response;
	}
	
	@GetMapping("/diet/foodlist")
	@ResponseBody
	public PageVO flist(@RequestParam(name="totalCount", required = false) int totalCount, 
										@RequestParam(name="page") int currpage) {
		PageVO pvo=new PageVO();
		pvo.setPage(currpage);
		pvo.setTotalCount(totalCount);
		return pvo;
	}
		
}
