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
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private Food_nutritionService food_nutritionService;

    @Autowired
    private UserHealthServiceImpl userHealthService;

    @GetMapping("/add_schedule")
    public String addSchedule() {
        return "add_schedule"; // add_schedule.html 렌더링
    }

    // 👇 뷰만 반환
    @GetMapping("/calendar")
    public String calendar(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        if (userInfo == null) {
            return "redirect:/";
        }
        session.setAttribute("userInfo", userInfo);
        User user = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        //키와 체중 이용해 BMI 계산
        int w = Integer.parseInt(user.getWeight());
        int h = Integer.parseInt(user.getHeight());
        int tempage = Integer.parseInt(user.getBirth().substring(0, 4));
        int age = Year.now().getValue() - tempage;

        String targetW;
        if(userHealthService.findInfo(userInfo.getId())==null){
            targetW = null;
        }else{
            UserHealthInfoDTO userHealthInfoDTO =  userHealthService.findInfo(userInfo.getId());
            targetW = userHealthInfoDTO.getTargetWeight();
        }

        double bmi = (w / ((h / 100.0) * (h / 100.0)));

        //BMR(기초 대사량) 계산
        String gender = user.getGender();
        double[] temp = new double[4];
        //성별에 따라 계산식이 다름
        if (gender.equals("남성")) {
            temp[0] = 66.47;
            temp[1] = 13.75;
            temp[2] = 5;
            temp[3] = 6.76;
        } else {
            temp[0] = 655.1;
            temp[1] = 9.56;
            temp[2] = 1.85;
            temp[3] = 4.68;
        }
        int bmr = Math.toIntExact(Math.round(temp[0] + (temp[1] * w) + (temp[2] * h) - (temp[3] * age)));

        model.addAttribute("BMR", bmr);
        model.addAttribute("BMI", bmi);
        model.addAttribute("user", user);
        model.addAttribute("targetWeight", targetW);
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
        List<String> foodnames = new ArrayList<>(); // 음식 이름 저장 리스트
        List<Schedule> slist = scheduleService.finddetail(iddate); // 해당 ID와 날짜로 스케줄 가져옴

        if (slist.isEmpty()) {
            System.out.println("1못가져옴"); // 스케줄이 비어 있을 경우 확인용 출력
        }

        List<IdEname> elist = new ArrayList<>(); // 운동용 ID + 이름 객체
        List<IdEname> dlist = new ArrayList<>(); // 식단용 ID + 이름 객체
        List<LocalTime> etimeList = new ArrayList<>(); // 운동 시간 저장 리스트
        List<LocalTime> dtimeList = new ArrayList<>(); // 식사 시간 저장 리스트

        int a = 0; // 식단 개수 카운팅

        // 스케줄 목록 순회
        for (Schedule schedule : slist) {
            IdEname idename = new IdEname();

            if (schedule.getType().equals("exercise")) { // 운동일 경우
                idename.setEname(schedule.getItem());
                idename.setId(iddate.getId());
                elist.add(idename);
                etimeList.add(schedule.getTime());
            } else { // 식단일 경우
                foodnames.add(schedule.getItem());
                idename.setEname(schedule.getItem());
                idename.setId(iddate.getId());
                dlist.add(idename);
                dtimeList.add(schedule.getTime());
                a++;
            }
        }

        Map<String, Object> map = new HashMap<>();
        List<detailfood> dflist = new ArrayList<>(); // 반환용 식단 상세 정보 리스트

        if (a > 0) { // 식단이 존재할 경우
            List<Diet> dietList1 = dietService.findbyuserId(iddate.getId()); // 해당 유저의 전체 식단 조회
            List did = new ArrayList(); // diet_id 저장용

            for (Diet dl : dietList1) {
                did.add(dl.getDiet_id()); // 식단 ID 저장
            }

            List<Diet_nutrition> dn = new ArrayList<>(); // 식단별 영양정보
            for (int i = 0; i < did.size(); i++) {
                dn.add(nutritionService.finddid(Integer.parseInt(did.get(i).toString())));
            }

            List<Diet_food> dnn1 = new ArrayList<>();
                List<Double> fcal = new ArrayList();
            for (String name : foodnames) {

                Diet_food dietFood =diet_foodService.findByfoodname(name);

               Food_nutrition foodNutrition= food_nutritionService.getid(dietFood.getFood_nutrition_id());

               ;

                dnn1.add(dietFood);
                fcal.add(foodNutrition.getCalories());

            }

            List<Diet_nutrition> dnn = new ArrayList<>();

            // ❗ 문제 발생 위치: diet_id가 일치하면 해당 diet의 nutrition 정보를 음식에 할당함
            // => 이건 해당 음식 하나의 영양이 아니라, diet 전체 영양정보임
            for (Diet_nutrition d : dn) {
                for (Diet_food d2 : dnn1) {
                    if (d.getDietid() == d2.getDietId()) {
                        dnn.add(d); // ❗ 이로 인해 여러 음식에 같은 영양정보가 붙게 됨
                    }
                }
            }

            // 🔧 수정 필요: dnn.size() == foodnames.size()가 보장되지 않으면 예외 발생 가능
            for (int i = 0; i < dnn.size(); i++) {
                detailfood df = new detailfood();
                df.setName(foodnames.get(i));
                df.setTime(dtimeList.get(i));

                // ❗ 문제 발생 위치: 해당 음식의 영양정보가 아니라, diet 단위의 영양정보가 사용됨
                df.setCalories(fcal.get(i));
                df.setProtein(dnn.get(i).getProtein());
                df.setCarbohydrate(dnn.get(i).getCarbohydrate());
                df.setFat(dnn.get(i).getFat());

                dflist.add(df);
            }

            map.put("dflist", dflist); // 식단 상세 정보 결과 반환
        }

        // 운동 루틴 처리
        List<ExerciseRoutine> exerciseRoutineList = new ArrayList<>();
        for (IdEname idename : elist) {
            exerciseRoutineList.add(exerciseRoutineService.findByidename(idename));
        }

        // 운동 결과 담기
        List<schedultimeenamecal> li = new ArrayList<>();
        for (int i = 0; i < exerciseRoutineList.size(); i++) {
            schedultimeenamecal s = new schedultimeenamecal();
            s.setTime(exerciseRoutineList.get(i).getTime());
            s.setRoutine(exerciseRoutineList.get(i).getRoutine());
            s.setCalorie(exerciseRoutineList.get(i).getCalorie());
            s.setSchedule(etimeList.get(i));
            li.add(s);
        }

        map.put("exerciseRoutineList", li); // 운동 결과 반환

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

    @GetMapping("/schedule/todayCal")
    @ResponseBody
    public Map<String, Integer> calList(Model model, HttpSession session) {
        SessionUserDTO User = (SessionUserDTO) session.getAttribute("userInfo");
        String id = User.getId();

        //오늘 날짜 구해서
        LocalDate today = LocalDate.now();
        List<Schedule> ss = scheduleService.todayCal(id, today);
        List<String> meals = new ArrayList<>();
        List<String> exercise = new ArrayList<>();
        //오늘 식단의 음식 이름 가져오기
        for (int i = 0; i < ss.size(); i++) {
            if (ss.get(i).getType().equals("meal")) {
                //음식 명
                meals.add(ss.get(i).getItem());
            } else {
                //운동 명
                exercise.add(ss.get(i).getItem());
            }

        }

        //음식 이름으로 food_id 구하기
        int[] df_id = new int[meals.size()];
        for (int i = 0; i < meals.size(); i++) {
            Diet_food df = diet_foodService.findByfoodname(meals.get(i));
            df_id[i] = df.getFood_nutrition_id();
        }
        List<ExerciseRoutine> elist = new ArrayList<>();
        int e_cal = 0;
        for (int i = 0; i < exercise.size(); i++) {
            elist.add(exerciseRoutineService.todaysexercise(id, exercise.get(i)));
        }
        for (int i = 0; i < elist.size(); i++) {
            e_cal += elist.get(i).getCalorie();
        }

        // food_id로 해당하는 칼로리 모두 더하기
        int calories = 0;
        for (int j : df_id) {
            calories += food_nutritionService.getCal(j);
        }
        Map<String, Integer> calList = new HashMap<>();
        calList.put("foodcal", calories);
        calList.put("exercisecal", e_cal);

        return calList;
    }

}
