package com.Fitory.fitory.controller;


import com.Fitory.fitory.dto.*;
import com.Fitory.fitory.entity.*;
import com.Fitory.fitory.mapper.DietMapper;
import com.Fitory.fitory.repository.Diet_foodRepository;
import com.Fitory.fitory.repository.Food_nutritionRepository;
import com.Fitory.fitory.repository.ScheduleRepository;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CalendarController {

    private final String API_URL = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
    private final String SERVICE_KEY = "0sU7LdhuRgHBWZiz3kXvAm1vlbK9QAv9SjIqtR2+++VxXbrXsrdS3IE6jpqX3DSZKXzYHxQAx/HnCCAZ3+vRfw==";
    // 🔑

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private DietMapper dietMapper;

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

    @Autowired
    private Diet_foodRepository dfRepo;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private Food_nutritionRepository fRepo;

    @GetMapping("/add_exercise")
    public String addExercise() {
        return "add_exercise"; // add_exercise.html 렌더링
    }

    @GetMapping("/add_schedule")
    public String addSchedule() {
        return "add_schedule"; // add_exercise.html 렌더링
    }

    // 👇 뷰만 반환
    @GetMapping("/calendar")
    public String calendar(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        if (userInfo == null) {
            return "redirect:login";
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
        if (userHealthService.findInfo(userInfo.getId()) == null) {
            targetW = null;
        } else {
            UserHealthInfoDTO userHealthInfoDTO = userHealthService.findInfo(userInfo.getId());
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
            if (schedule.getTime() != null) {
                scheduleDTO.time = Time.valueOf(schedule.getTime());
            } else {
                scheduleDTO.time = null;
            }
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @PostMapping("/save_schedule")
    @ResponseBody
    public String saveSchedule(SchedulesaveDTO schedule, HttpSession session) {

        Schedule schedule1 = new Schedule();

        LocalDate date = LocalDate.parse(schedule.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time;
        if (schedule.getTime() == null) {
            time = null;
        } else {
            time = LocalTime.parse(schedule.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        }

        schedule1.setDate(date);
        schedule1.setTime(time);
        schedule1.setItem(schedule.getItem());
        schedule1.setUserid(schedule.getId());
        schedule1.setType(schedule.getType());
        if(schedule1.getType().equals("meal")) {
            schedule1.setDietid(schedule.getDietid());
        }

        scheduleService.saveschedule(schedule1);

        return "success";
    }

    @PostMapping("/schedule/detail")
    @ResponseBody
    public Map<String, Object> sdetail(@ModelAttribute IdDate iddate) {

        List<Schedule> slist = scheduleService.finddetail(iddate); // 해당 ID와 날짜로 스케줄 가져옴

        if (slist.isEmpty()) {
            System.out.println("1못가져옴"); // 스케줄이 비어 있을 경우 확인용 출력
        }

        List<IdEname> elist = new ArrayList<>(); // 운동용 ID + 이름 객체

        List<LocalTime> etimeList = new ArrayList<>(); // 운동 시간 저장 리스트


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
                a++;
            }
        }

        Map<String, Object> map = new HashMap<>();


        if (a > 0) { // 식단이 존재할 경우

            List<detailfood> dflist = dietMapper.findDietDetailsByUserIdAndDate(iddate.getId(), iddate.getDate());
            map.put("dflist", dflist);

            for(detailfood df : dflist) {
                df.getName();
            }
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
        int diet_id = 0;
        List<Diet_food> food_id = new ArrayList<>();

        List<Schedule> sss = scheduleRepository.getschedule(id, today);
        List<String> exercisename = new ArrayList<>();
        for(int i=0; i<sss.size(); i++){
            if(sss.get(i).getDietid()!=null){
                diet_id = sss.get(i).getDietid();
            }else {
                exercisename.add(sss.get(i).getItem());
            }
        }

        List<Integer> food_nutrition_id = new ArrayList<>();
        for(int i=0; i<food_id.size(); i++){
            food_nutrition_id.add(food_id.get(i).getFood_nutrition_id());
        }


        int foodcal = 0;
        List<Food_nutrition> ff = fRepo.findBydietId(diet_id);
        for (Food_nutrition foodNutrition : ff) {
            foodcal += foodNutrition.getCalories();
        }

        int e_cal=0;
        List<ExerciseRoutine> elist = new ArrayList<>();
        for(int i=0; i<exercisename.size(); i++){
            elist.add(exerciseRoutineService.todaysexercise(id, exercisename.get(i)));
        }

        for(int i=0; i<elist.size(); i++){
            e_cal += elist.get(i).getCalorie();
        }


        System.out.println(e_cal+"운동 칼로리!@%@%15");
        Map<String, Integer> calList = new HashMap<>();
        calList.put("foodcal", foodcal);
        calList.put("exercisecal", e_cal);

        return calList;
    }

    @GetMapping("/api/holidays")
@ResponseBody
public Map<String, Object> getHolidays(String year, String month) throws IOException {
    String dataKey = "holiday:" + year + "-" + month;
    ObjectMapper mapper = new ObjectMapper();

    // 1. 캐시가 존재하면 바로 반환 (첫 번째 확인)
    String cached = redisTemplate.opsForValue().get(dataKey);
    if (cached != null) {
        System.out.println("레디스에서 반환");
        return mapper.readValue(cached, new TypeReference<Map<String, Object>>() {});
    }

    // 2. 분산 락 시도
    String lockKey = "lock:" + dataKey;
    boolean isLockSet = redisTemplate.opsForValue()
            .setIfAbsent(lockKey, "1", Duration.ofSeconds(10)); // 락 유효 시간 10초

    if (isLockSet) {
        // 🔐 락 획득 성공: 오직 한 스레드/서버만 실행
        try {
            System.out.println("락 획득 성공! 외부 API 호출 시작");
            
            // 한 번 더 캐시 확인 (아주 드물게 다른 서버가 락 해제 직후 캐시했을 수도 있으므로)
            cached = redisTemplate.opsForValue().get(dataKey);
            if (cached != null) {
                return mapper.readValue(cached, new TypeReference<Map<String, Object>>() {});
            }

            // 외부 API 호출
            String url = API_URL + "?serviceKey=" + URLEncoder.encode(SERVICE_KEY, "UTF-8")
                    + "&solYear=" + year
                    + "&solMonth=" + month
                    + "&_type=json";
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String result = reader.lines().collect(Collectors.joining());
                
                // 캐시에 저장 (6시간 유효)
                redisTemplate.opsForValue().set(dataKey, result, Duration.ofHours(6));
                
                return mapper.readValue(result, new TypeReference<Map<String, Object>>() {});
            }
        } finally {
            // 작업 완료 후 락 해제
            System.out.println("작업 완료! 락 해제");
            redisTemplate.delete(lockKey); 
        }
    } else {
        // ❌ 락 획득 실패: 짧은 대기 후 재시도
        System.out.println("락 획득 실패! 짧게 대기 후 캐시 재확인");
        try {
            Thread.sleep(300); // 첫 번째 스레드가 작업할 시간을 줍니다.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 락 획득 스레드가 캐시를 채웠을 가능성이 높으므로 다시 확인
        cached = redisTemplate.opsForValue().get(dataKey);
        if (cached != null) {
            return mapper.readValue(cached, new TypeReference<Map<String, Object>>() {});
        } else {
            // 드물게 아직 캐시가 없는 경우, 재시도하거나 null 반환 등의 처리
            // (여기서는 일단 재요청하는 것으로 처리)
            return getHolidays(year, month);
        }
    }
}
}
