package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.DelscheduleDTO;
import com.Fitory.fitory.dto.IdDate;
import com.Fitory.fitory.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface IF_ScheduleService {
    List<Schedule> mySchedule(String userid , LocalDate start, LocalDate end);

    void saveschedule(Schedule schedule);

    List<Schedule> finddetail(IdDate iddate);

    void del(DelscheduleDTO delschedule);

    List<Schedule> todayCal(String id, LocalDate date);
}
