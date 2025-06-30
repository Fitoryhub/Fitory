package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.DelscheduleDTO;
import com.Fitory.fitory.dto.IdDate;
import com.Fitory.fitory.entity.Schedule;
import com.Fitory.fitory.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleService implements IF_ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public List<Schedule> mySchedule(String userid, LocalDate start, LocalDate end) {
        return scheduleRepository.findByUseridAndDateBetween(userid, start, end);
    }

    @Override
    public void saveschedule(Schedule schedule) {
        scheduleRepository.save(schedule);

    }

    @Override
    public List<Schedule> finddetail(IdDate iddate) {
        String id = iddate.getId();
        LocalDate date = LocalDate.parse(iddate.getDate());
        return scheduleRepository.findByUseridAndDate(id, date);
    }

    @Transactional
    @Override
    public void del(DelscheduleDTO delschedule) {



        String id = delschedule.getId();

        String date = delschedule.getDate();

        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        String timeStr = delschedule.getTime();

        timeStr += ":00";

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        String name = delschedule.getName();

        scheduleRepository.deleteByUseridAndDateAndTimeAndItem( id , date1, time, name);

    }

    @Override
    public List<Schedule> todayCal(String id, LocalDate date) {
        return scheduleRepository.todayCal(id, date);
    }


}
