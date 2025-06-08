package com.Fitory.fitory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Fitory.fitory.dto.DietsaveDTO;
import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.dto.PageDTO;
import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.entity.Diet_food;
import com.Fitory.fitory.service.DietService;
import com.Fitory.fitory.service.Diet_foodService;
import com.Fitory.fitory.service.Diet_nutritionService;
import com.Fitory.fitory.service.Food_nutritionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@Controller
public class DietController {
	
	private final DietService dservice;
	private final Diet_foodService dfservice;
	private final Diet_nutritionService dnservice;
	private final Food_nutritionService fnservice;
	
	
	@GetMapping("/diet/board")
	public String board(HttpSession session, Model model) {
		SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		return "/diet/board";
	}

	@GetMapping("/diet/page")
	@ResponseBody
	public Map<String,Object> gopage(@RequestParam(name = "page") int page){
		Map<String,Object> board=dservice.serlist(page);
		return board;
	}


	@GetMapping("/diet/resist")
	public String resist(HttpSession session, Model model) {
		if (session.getAttribute("userInfo") == null) {
			return "redirect:/login";
		}
		SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
		return "/diet/resist";
	}
	
	@GetMapping("/diet/analyze")
	public String analyze(HttpSession session, Model model) {
		SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		return "/diet/analyze";
	}
	
	@PostMapping("/diet/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody DietsaveDTO dsv) {
		dservice.insert(dsv.getDiet());
		List<FoodlistDTO> flist=dsv.getFoodlist();
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
	public PageDTO flist(@RequestParam(name="totalCount", required = false) int totalCount, 
										@RequestParam(name="page") int currpage) {
		PageDTO pvo=new PageDTO();
		pvo.setPage(currpage);
		pvo.setTotalCount(totalCount);
		return pvo;
	}
		
}

