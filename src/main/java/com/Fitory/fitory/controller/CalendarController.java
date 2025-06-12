package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.ScheduleDTO;
import com.Fitory.fitory.dto.SchedulesaveDTO;
import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.entity.Schedule;
import com.Fitory.fitory.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.sql.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/add_schedule")
    public String addSchedule() {
        return "add_schedule"; // add_schedule.html 렌더링
    }

    // 👇 뷰만 반환
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar"; // calendar.html
    }

    // 👇 AJAX용 JSON API
    @GetMapping("/api/schedules")
    @ResponseBody
    public List<ScheduleDTO> getScheduleData(@RequestParam(value = "num" ,required = false , defaultValue = "0") int num , HttpSession session) {
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
    public String saveSchedule(SchedulesaveDTO schedule , HttpSession session) {

        Schedule schedule1 = new Schedule();

       LocalDate date= LocalDate.parse(schedule.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       LocalTime time= LocalTime.parse(schedule.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

        schedule1.setDate(date);
        schedule1.setTime(time);
        schedule1.setItem(schedule.getItem());
        schedule1.setUserid(schedule.getId());
        schedule1.setType(schedule.getType());

        scheduleService.saveschedule(schedule1);

        return "success";
    }

}
