package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.userSetDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExerciseController {



    @GetMapping("/exercise")
    public String exercise() {
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
        return null;
    }
}
