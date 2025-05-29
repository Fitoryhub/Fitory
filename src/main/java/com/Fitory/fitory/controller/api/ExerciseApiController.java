package com.Fitory.fitory.controller.api;

import com.Fitory.fitory.entity.Exercises;
import com.Fitory.fitory.service.ExerciseRoutineService;
import com.Fitory.fitory.service.ExerciseService;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseApiController {


    @Autowired
    ExerciseRoutineService exerciseRoutineService;


    @GetMapping("/set")
    public Map<String, Object> exerciseSet(HttpSession session) {
        List<Exercises> elist = new ArrayList<>();
        Integer cal = (Integer) session.getAttribute("cal");
        Integer time = (Integer) session.getAttribute("time");
        if (session.getAttribute("elist") != null) {
        elist = (List<Exercises>) session.getAttribute("elist");
        System.out.println(elist.size()+"elist size");
        }else{
            System.out.println("elist는 null입니당 헤헤");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("elist",elist);
        response.put("cal", cal);
        response.put("time", time);
        return response;
    }

    @PostMapping("/deleteList") //
    public boolean deleteList(@RequestParam List<Integer> deletelist) {

        System.out.println("삭제할 ID들: " + deletelist);

        for(int i=0;i<deletelist.size();i++){
           boolean result = exerciseRoutineService.deleteByroutineid(deletelist.get(i));
           if(!result){
               return false;
           }
        }
            return true;

    }
}
