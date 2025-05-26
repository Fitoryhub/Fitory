package com.Fitory.fitory.controller.api;

import com.Fitory.fitory.entity.Exercises;
import com.Fitory.fitory.service.ExerciseService;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseApiController {

    @Autowired
    ExerciseService EService;
    @Autowired
    UserService userService;


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
}
