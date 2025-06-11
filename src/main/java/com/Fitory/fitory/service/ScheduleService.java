package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Schedule;
import com.Fitory.fitory.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService implements IF_ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public List<Schedule> mySchedule(String userid , LocalDate start, LocalDate end) {
    return scheduleRepository.findByUseridAndDateBetween(userid ,start,end);
    }

    @Override
    public void saveschedule(Schedule schedule) {
        scheduleRepository.save(schedule);

    }

}
