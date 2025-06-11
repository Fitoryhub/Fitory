package com.Fitory.fitory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Fitory.fitory.entity.Diet;
import com.Fitory.fitory.entity.Diet_nutrition;
import com.Fitory.fitory.entity.Plike;
import com.Fitory.fitory.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Fitory.fitory.dto.DietsaveDTO;
import com.Fitory.fitory.dto.FoodlistDTO;
import com.Fitory.fitory.dto.PageDTO;
import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.entity.Diet_food;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@Controller
public class DietController {
	
	private final DietService dservice;
	private final Diet_foodService dfservice;
	private final Diet_nutritionService dnservice;
	private final Food_nutritionService fnservice;
	private final PlikeService plikeService;
	
	
	@GetMapping("/diet/board")
	public String board(HttpSession session, Model model) {
		SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		return "/diet/board";
	}
	@GetMapping("/dietdetail")
	public String dietdetail(HttpSession session, Model model, @RequestParam(name="diet") int did){
		SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		Diet diet=dservice.getone(did);
		diet.setDiet_view(diet.getDiet_view()+1);
		dservice.updatview(diet);
		model.addAttribute("diet",diet);
		List<Diet_food> dflist=dfservice.getdflist(did);
		model.addAttribute("dflist",dflist);
		Diet_nutrition dnt=dnservice.getone(did);
		model.addAttribute("dnutrition",dnt);
		Plike dp=new Plike();
		if(userInfo==null){
			dp=null;
		}else {
			dp=plikeService.findplike2(userInfo.getId(), did);
		}
		model.addAttribute("dplike",dp);
		return "/diet/dietdetail";
	}
	@GetMapping("/dietlike")
	@ResponseBody
	public Map<String,Object> dietlike(@RequestParam(name = "did") int did,@RequestParam(name = "uid") String uid){
		Plike dp= new Plike();
		Map<String,Object> response= new HashMap<>();
		if(plikeService.findplike2(uid,did)==null){
			dp.setUid(uid);
			dp.setDnum(did);
			plikeService.saveplike(dp);
			Diet diet=dservice.getone(did);
			diet.setDiet_like(diet.getDiet_like()+1);
			dservice.updatview(diet);
			response.put("status","yes");
			response.put("like",diet.getDiet_like());
			return response;
		}else{
			Diet diet=dservice.getone(did);
			diet.setDiet_like(diet.getDiet_like()-1);
			dservice.updatview(diet);
			dp=plikeService.findplike2(uid,did);
			plikeService.deletdplike(dp);
			response.put("status","no");
			response.put("like",diet.getDiet_like());
			return response;
		}
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
@GetMapping("/mymeal")
	@ResponseBody
	public Map<String, Object> mymeal(@RequestParam("userid") String userid) {

		List <Diet> diets = dservice.selectdiet_id(userid);
		ArrayList<String> foodname = new ArrayList<>();
		for(Diet diet:diets){
			Long did=diet.getDiet_id();
			 List<Diet_food> foods=dfservice.getdflist(did.intValue());
				for(Diet_food food:foods){
					foodname.add(food.getFood_name());
				}
		}


	Map<String, Object> list = new HashMap<>();
	list.put("list", foodname);
	return list;
	}
		
}

