package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.*;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.repository.DietRepository;
import com.Fitory.fitory.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@RequiredArgsConstructor
@Controller
public class DietController {

    private final DietService dservice;
    private final Diet_foodService dfservice;
    private final Diet_nutritionService dnservice;
    private final Food_nutritionService fnservice;
    private final PlikeService plikeService;
    private final FileService fileService;
    private final DietRepository dietRepository;


    @GetMapping("/diet/board")
    public String board(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "/diet/board";
    }

    @GetMapping("/dietdetail")
    public String dietdetail(HttpSession session, Model model, @RequestParam(name = "diet") int did) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);

        Diet diet = dservice.getone(did);
        diet.setDiet_view(diet.getDiet_view() + 1);
        dservice.updatview(diet);
        model.addAttribute("diet", diet);

        List<Diet_food> dflist = dfservice.getdflist(did);
        model.addAttribute("dflist", dflist);

        Diet_nutrition dnt = dnservice.getone(did);
        model.addAttribute("dnutrition", dnt);

        List<Food_nutrition> fnt = fnservice.getfnlist(did);
        model.addAttribute("fnlist", fnt);

        Plike dp = new Plike();
        if (userInfo == null) {
            dp = null;
        } else {
            dp = plikeService.findplike2(userInfo.getId(), did);
        }
        model.addAttribute("dplike", dp);

        List<Files> files = fileService.findfile2(did);
        if (files.isEmpty()) {
            files = null;
        }
        model.addAttribute("files", files);
        return "/diet/dietdetail";
    }

    @Transactional
    @GetMapping("/dietlike")
    @ResponseBody
    public Map<String, Object> dietlike(@RequestParam(name = "did") int did, @RequestParam(name = "uid") String uid) {
        Plike dp = new Plike();
        Map<String, Object> response = new HashMap<>();
        if (plikeService.findplike2(uid, did) == null) {
            dp.setUid(uid);
            dp.setDnum(did);
            plikeService.saveplike(dp);
            Diet diet = dservice.getone(did);
            diet.setDiet_like(diet.getDiet_like() + 1);
            dservice.updatview(diet);
            response.put("status", "yes");
            response.put("like", diet.getDiet_like());
            return response;
        } else {
            Diet diet = dservice.getone(did);
            diet.setDiet_like(diet.getDiet_like() - 1);
            dservice.updatview(diet);
            dp = plikeService.findplike2(uid, did);
            plikeService.deletdplike(dp);
            response.put("status", "no");
            response.put("like", diet.getDiet_like());
            return response;
        }
    }

    @GetMapping("/diet/page")
    @ResponseBody
    public Map<String, Object> gopage(@RequestParam(name = "page") int page) {
        Map<String, Object> board = dservice.serlist(page);
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

    @Transactional
    @PostMapping("/diet/save")
    @ResponseBody
    public Map<String, String> save(@RequestPart DietsaveDTO dsv, @RequestPart(name = "files", required = false) MultipartFile[] files) throws Exception {
        dservice.insert(dsv.getDiet());
        List<FoodlistDTO> flist = dsv.getFoodlist();
        int did = dservice.getid(dsv.getDiet().getTitle());
        List<Diet_food> dflist = fnservice.insert(flist, did);
        List<Integer> fnid = fnservice.getidlist(did);
        dfservice.insert(did, fnid, dflist, flist);
        dnservice.insert(did, flist);

        String ProjectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image";
        if (files != null) {
            for (MultipartFile onefile : files) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + onefile.getOriginalFilename();
                File savfile = new File(ProjectPath, filename);
                onefile.transferTo(savfile);
                Files file = new Files();
                file.setDnum(did);
                file.setFilename(filename);
                fileService.filesave(file);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("url", "/diet/board");
        return response;
    }


    @Transactional
    @GetMapping("/diet/delete")
    @ResponseBody
    public String delete(@RequestParam(name = "diet_id") int did) {
        fileService.delete(did);
        plikeService.deletdplike(did);
        dfservice.delete(did);
        fnservice.delete(did);
        dnservice.delete(did);
        dservice.delete(did);
        return "/diet/board";
    }

    @GetMapping("/diet/foodlist")
    @ResponseBody
    public PageDTO flist(@RequestParam(name = "totalCount", required = false) int totalCount,
                         @RequestParam(name = "page") int currpage) {
        PageDTO pvo = new PageDTO();
        pvo.setPage(currpage);
        pvo.setTotalCount(totalCount);
        return pvo;
    }

    @GetMapping("/mealDate")
    @ResponseBody
    public List<String> myscheduleDate(HttpSession session) {
        System.out.println("요청 했어용~~@!4");
        SessionUserDTO udto = (SessionUserDTO) session.getAttribute("userInfo");

        String id = udto.getId();
        List<Diet> alldiet = dietRepository.findAllByUserid(id);

        // LinkedHashSet → 순서 유지 + 중복 제거
        Set<String> dateSet = new LinkedHashSet<>();

        if (alldiet != null) {
            for (Diet diet : alldiet) {
                String a = diet.getCreated_at();
                if (a != null && a.length() >= 10) {
                    String dateOnly = a.substring(0, 10);
                    dateSet.add(dateOnly);
                }
            }
        }

        return new ArrayList<>(dateSet); // Set → List로 변환해서 반환
    }

    @GetMapping("/mymeal")
    @ResponseBody
    public Map<Long, List<Diet_food>> myDite(@RequestParam String id) {
        List<Long> didlist = dservice.findDietIdsByUserid(id);

        List<List<Diet_food>> allFoodList = new ArrayList<>();

        Map<Long, List<Diet_food>> foodMap = new HashMap<>();

        for (Long did : didlist) {
            List<Diet_food> onedayfood = dservice.findonedaymeal(did);
            foodMap.put(did, onedayfood);
        }
        return foodMap;
    }

}

