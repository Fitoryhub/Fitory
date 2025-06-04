package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.ExerciseDTO;
import com.Fitory.fitory.dto.ExerciseRoutineDTO;
import com.Fitory.fitory.dto.UserSetDTO;
import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.entity.Exercises;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.ExerciseRoutineService;
import com.Fitory.fitory.service.ExerciseService;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ExerciseController {

    @Autowired
    ExerciseService EService;
    @Autowired
    UserService userService;
    @Autowired
    ExerciseRoutineService exerciseRoutineService;



    @GetMapping("/exercise")
    public String exercise(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:loginForm";
        }
        List<ExerciseDTO> elist = (List<ExerciseDTO>) model.getAttribute("elist");
        Object time = model.getAttribute("time");
        Object cal = model.getAttribute("cal");
        String user_id = (String) session.getAttribute("user_id");
        if (user_id != null) {
            Optional<User> user = userService.findById(user_id);
            user.ifPresent(value -> model.addAttribute("user", value));
        }

        model.addAttribute("time", time);
        model.addAttribute("cal", cal);
        if (elist != null && !elist.isEmpty()) {
            System.out.println(elist.get(0).getName() + "elist 운동 이름" + "elist size = " + elist.size());
        } else {
            System.out.println("elist is null or empty");
        }
        model.addAttribute("elist", elist);

        return "exercise_info";
    }

    @PostMapping("/set")
    public String handleForm(@ModelAttribute UserSetDTO userset, HttpSession session) {
        String id = session.getAttribute("user_id").toString();
        User user = userService.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
        int weight = user.getWeight();
        int cal = userset.getCal();
        int e_time = userset.getTime();

        int met = (cal * 60) / (weight * e_time);

        String Is_anaerobic = userset.getIs_anaerobic();
        String Requires_equipment = userset.getRequires_equipment();
        List<Exercises> exercises = new ArrayList<>();

        if(Is_anaerobic.isBlank() && Requires_equipment.isBlank()) {
            exercises = EService.getMatchingExercises_v4(met);
        } else if (Is_anaerobic.isEmpty()) {
            exercises = EService.getMatchingExercises_v3(met, Requires_equipment);
        }else if(Requires_equipment.isEmpty()) {
            exercises = EService.getMatchingExercises_v2(met, Is_anaerobic);
        }else{
            exercises = EService.getMatchingExercises(met, Requires_equipment, Is_anaerobic);
        }


        List<ExerciseDTO> edto = new ArrayList<>();
        for (Exercises e : exercises) {
            ExerciseDTO dto = new ExerciseDTO();
            dto.setName(e.getEname());
            dto.setIntensity(e.getIntensity());
            dto.setIs_anaerobic(e.getOxygen());
            dto.setRequires_equipment(e.getBodyweight());
            dto.setMet_rank(e.getMetrank());
            edto.add(dto);
        }
        session.setAttribute("elist", edto);
        session.setAttribute("cal", cal);
        session.setAttribute("time", e_time);

        return "redirect:/exercise";
    }

    @PostMapping("/routines")
    @ResponseBody
    public boolean routines(@RequestBody Map<String, Object> routineData, HttpSession session) {

        int time = (int) routineData.get("time");
        int cal = (int) routineData.get("cal");
        String id = session.getAttribute("user_id").toString();
        List<String> exercise = (List<String>) routineData.get("selectExercises");

        for(int i=0; i<exercise.size(); i++){
            boolean resutl;
            ExerciseRoutine e = new ExerciseRoutine();
            e.setUserid(id);
            e.setRoutine(exercise.get(i));
            e.setTime(time);
            e.setCalorie(cal);

            resutl=exerciseRoutineService.save(e);
            if(!resutl){
                return false;
            }
        }
            return true;
    }
}
