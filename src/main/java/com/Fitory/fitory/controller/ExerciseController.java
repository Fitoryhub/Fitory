package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.ExerciseDTO;
import com.Fitory.fitory.dto.UserSetDTO;
import com.Fitory.fitory.entity.Exercises;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.ExerciseService;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ExerciseController {

    @Autowired
    ExerciseService EService;
    @Autowired
    UserService userService;


    @GetMapping("/exercise")
    public String exercise(HttpSession session, Model model, @ModelAttribute("elist") List<ExerciseDTO> elist) {
        Object time = model.getAttribute("time");
        Object cal = model.getAttribute("cal");
        String user_id = (String) session.getAttribute("user_id");
        if (user_id != null) {
            Optional<User> user = userService.findById(user_id);
            user.ifPresent(value -> model.addAttribute("user", value));
        }

        model.addAttribute("time", time);
        model.addAttribute("cal", cal);
        model.addAttribute("elist", elist);

        return "exercise_info";
    }


    @PostMapping("/set")
    public String exerciseSet(@ModelAttribute UserSetDTO userset, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String id = session.getAttribute("user_id").toString();
        User user = userService.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
        int weight = user.getWeightKg();
        int e_time = userset.getTime();
        int cal = userset.getCal();


        int met = (cal * 60) / (weight * e_time);

        String Is_anaerobic = userset.getIs_anaerobic();
        String Requires_equipment = userset.getRequires_equipment();

        List<Exercises> exercises = EService.getMatchingExercises(met, Requires_equipment, Is_anaerobic);


        List<ExerciseDTO> edto = new ArrayList<>();
        for (Exercises e : exercises) {
            ExerciseDTO dto = new ExerciseDTO();
            dto.setName(e.getE_name());
            dto.setIntensity(e.getIntensity());
            dto.setIs_anaerobic(e.getIsAnaerobic());
            dto.setRequires_equipment(e.getRequiresEquipment());
            dto.setMet_rank(e.getMetrank());
            edto.add(dto);
        }
        redirectAttributes.addFlashAttribute("time", e_time);
        redirectAttributes.addFlashAttribute("cal", cal);
        redirectAttributes.addFlashAttribute("elist", edto);
        return "redirect:/exercise";
    }

}
