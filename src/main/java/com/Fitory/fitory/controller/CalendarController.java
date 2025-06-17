package com.Fitory.fitory.controller;


import com.Fitory.fitory.dto.*;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class CalendarController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ExerciseRoutineService exerciseRoutineService;

    @Autowired
    private DietService dietService;

    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Autowired
    private Diet_foodService diet_foodService;

    @Autowired
    private Diet_nutritionService nutritionService;

    @GetMapping("/add_schedule")
    public String addSchedule() {
        return "add_schedule"; // add_schedule.html 렌더링
    }

    // 👇 뷰만 반환
    @GetMapping("/calendar")
    public String calendar(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        if(userInfo==null){
            return "redirect:/";
        }
        session.setAttribute("userInfo", userInfo);
        User user = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        //키와 체중 이용해 BMI 계산
        int w = Integer.parseInt(user.getWeight());
        int h = Integer.parseInt(user.getHeight());
        int tempage = Integer.parseInt(user.getBirth().substring(0,4));
        int age = java.time.Year.now().getValue() - tempage;
        System.out.println(age+"ㄴ나이임!@#!@$%!%^!$#%");
        System.out.println(java.time.Year.now().getValue()+"올해");
        System.out.println(tempage+"내가 태어난 년도");
        double bmi = (w/((h/100.0)*(h/100.0)));

        //BMR(기초 대사량) 계산
        String gender = user.getGender();
        double[] temp = new double[4];
        //성별에 따라 계산식이 다름
        if(gender.equals("남성")){
           temp[0] = 66.47;
           temp[1] = 13.75;
           temp[2] = 5;
           temp[3] = 6.76;
        }else {
            temp[0] = 655.1;
            temp[1] = 9.56;
            temp[2] = 1.85;
            temp[3] = 4.68;
        }
        int bmr = Math.toIntExact(Math.round(temp[0] + (temp[1] * w) + (temp[2] * h) - (temp[3] * age)));
        System.out.println(bmr+"BMR이거임ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
        model.addAttribute("BMR", bmr);
        model.addAttribute("BMI", bmi);
        model.addAttribute("user", user);
        return "calendar"; // calendar.html
    }

    // 👇 AJAX용 JSON API
    @GetMapping("/api/schedules")
    @ResponseBody
    public List<ScheduleDTO> getScheduleData(@RequestParam(value = "num", required = false, defaultValue = "0") int num, HttpSession session) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        String id = userInfo.getId();

        YearMonth date = YearMonth.from(LocalDate.now());
        date = date.plusMonths(num);
        LocalDate start = date.atDay(1);
        LocalDate end = date.atEndOfMonth();


        List<Schedule> list = scheduleService.mySchedule(id, start, end);

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : list) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.day = schedule.getDate().getDayOfMonth();
            scheduleDTO.item = schedule.getItem();
            scheduleDTO.time = Time.valueOf(schedule.getTime());
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @PostMapping("/save_schedule")
    @ResponseBody
    public String saveSchedule(SchedulesaveDTO schedule, HttpSession session) {

        Schedule schedule1 = new Schedule();

        LocalDate date = LocalDate.parse(schedule.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.parse(schedule.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

        schedule1.setDate(date);
        schedule1.setTime(time);
        schedule1.setItem(schedule.getItem());
        schedule1.setUserid(schedule.getId());
        schedule1.setType(schedule.getType());

        scheduleService.saveschedule(schedule1);

        return "success";
    }

    @PostMapping("/schedule/detail")
    @ResponseBody
    public Map<String, Object> sdetail(@ModelAttribute IdDate iddate) {
        List<String> foodnames = new ArrayList<>();
        List<Schedule> slist = scheduleService.finddetail(iddate);
        if (slist.isEmpty()) {
            System.out.println("1못가져옴");
        }

        List<IdEname> elist = new ArrayList<>();
        List<IdEname> dlist = new ArrayList<>();
        List<LocalTime> etimeList = new ArrayList<>();
        List<LocalTime> dtimeList = new ArrayList<>();

        int a = 0; // 식단 개수 체크용

        for (Schedule schedule : slist) {
            IdEname idename = new IdEname();
            if (schedule.getType().equals("exercise")) {
                idename.setEname(schedule.getItem());
                idename.setId(iddate.getId());
                elist.add(idename);
                etimeList.add(schedule.getTime());
            } else {
                foodnames.add(schedule.getItem());
                idename.setEname(schedule.getItem());
                idename.setId(iddate.getId());
                dlist.add(idename);
                dtimeList.add(schedule.getTime());
                a++;
            }
        }

        Map<String, Object> map = new HashMap<>();
        List<detailfood> dflist = new ArrayList<>();

        if (a > 0) {
            List<Diet> dietList1 = dietService.findbyuserId(iddate.getId());
            List did = new ArrayList();

            for (Diet dl : dietList1) {

                did.add(dl.getDiet_id());

            }

            List<Diet_nutrition> dn = new ArrayList<>();
            for (int i = 0; i < did.size(); i++) {
                dn.add(nutritionService.finddid(Integer.parseInt(did.get(i).toString())));
            }

            List<Diet_food> dnn1 = new ArrayList<>();
            for (String name : foodnames) {
                dnn1.add(diet_foodService.findByfoodname(name));
            }

            List<Diet_nutrition> dnn = new ArrayList<>();
            for (Diet_nutrition d : dn) {
                for (Diet_food d2 : dnn1) {
                    if (d.getDietid() == d2.getDietid()) {
                        dnn.add(d);
                    }
                }
            }

            // 여기서 i 기준이 꼬일 수 있으니, dflist 생성은 dnn과 foodnames, dtimeList 개수가 같다고 가정하는 게 위험함
            // 하지만 원래 코드 구조 유지 위해 그대로 둠
            for (int i = 0; i < dnn.size(); i++) {
                detailfood df = new detailfood();
                df.setName(foodnames.get(i));
                df.setTime(dtimeList.get(i));
                df.setCalories(dnn.get(i).getCalories());
                df.setProtein(dnn.get(i).getProtein());
                df.setCarbohydrate(dnn.get(i).getCarbohydrate());
                df.setFat(dnn.get(i).getFat());
                dflist.add(df);
            }

            map.put("dflist", dflist);
        }

        List<ExerciseRoutine> exerciseRoutineList = new ArrayList<>();
        for  (IdEname idename : elist) {
            exerciseRoutineList.add(exerciseRoutineService.findByidename(idename));
        }

        List<schedultimeenamecal> li = new ArrayList<>();
        for (int i = 0; i < exerciseRoutineList.size(); i++) {
            schedultimeenamecal s = new schedultimeenamecal();
            s.setTime(exerciseRoutineList.get(i).getTime());
            s.setRoutine(exerciseRoutineList.get(i).getRoutine());
            s.setCalorie(exerciseRoutineList.get(i).getCalorie());
            s.setSchedule(etimeList.get(i));
            li.add(s);
        }

        map.put("exerciseRoutineList", li);

        return map;
    }
    @PostMapping("/del/schedule")
    @ResponseBody
    public Map<String, Object> delschedule(@ModelAttribute DelscheduleDTO delschedule) {
        scheduleService.del(delschedule);
        IdDate iddate = new IdDate();
        iddate.setId(delschedule.getId());
        iddate.setDate(delschedule.getDate());
        return sdetail(iddate);

    }

}
