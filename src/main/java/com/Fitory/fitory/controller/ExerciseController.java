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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println(user.get().getUser_id()+"user.get id임");
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "exercise_info";
    }

    @PostMapping("/set")
    public String exerciseSet(@ModelAttribute userSetDTO userset) {



        int e_time = userset.getTime();
        int cal = userset.getCal();
            /*
            double met = (cal*60)/(weight*e_time);
            이 met를 db에 넘겨 검색
            해당하는 운동 찾기 ㄱㄱ
            */
        userset.getIs_anaerobic();
        userset.getRequires_equipment();

        int met = 3;
        List<Exercise> elist = ERepo.findByMetrank(met);
        List<exerciseDTO> edto = new ArrayList<>();
        for (Exercise ex : elist) {
            exerciseDTO dto = new exerciseDTO();
            dto.setName(ex.getE_name());
            dto.setMet_rank(ex.getMetrank());
            dto.setIntensity(ex.getIntensity());
            dto.setRequires_equipment(ex.getRequires_equipment());
            dto.setIs_anaerobic(ex.getIs_anaerobic());
            edto.add(dto);
        }
        return null;
    }
}
