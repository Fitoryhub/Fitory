package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.exerciseDTO;
import com.Fitory.fitory.dto.userSetDTO;
import com.Fitory.fitory.entity.Exercise;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.IF_exerciseRepository;
import com.Fitory.fitory.service.IF_UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ExerciseController {

    @Autowired
    IF_exerciseRepository ERepo;
    @Autowired
    IF_UserService userService;


    @GetMapping("/exercise")
    public String exercise(HttpSession session, Model model) {
        String user_id = (String) session.getAttribute("user_id");
        Optional<User> user = userService.findById(user_id);
        System.out.println(user.get().getUser_id() + "user.get id임");
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "exercise_info";
    }

    @PostMapping("/set")
    public String exerciseSet(@ModelAttribute userSetDTO userset, HttpSession session, Model model) {
        String id = session.getAttribute("user_id").toString();
        User user = userService.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
        int weight = user.getWeight_kg();
        int e_time = userset.getTime();
        int cal = userset.getCal();

        int met = (int)(cal*60)/(weight*e_time);
        String Is_anaerobic = userset.getIs_anaerobic();
        String Requires_equipment = userset.getRequires_equipment();
        List<Exercise> exercise = ERepo.findByMatchingExercise( met, Requires_equipment, Is_anaerobic);
        List<exerciseDTO> edto = new ArrayList<>();
        for (Exercise e : exercise) {
            exerciseDTO dto = new exerciseDTO(e);
            edto.add(dto);
        }
        model.addAttribute("eList", edto);




        return null;
    }
}
