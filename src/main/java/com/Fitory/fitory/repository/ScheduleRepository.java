package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Schedule;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {



    @Query("select s from Schedule s where s.userid=:userid and s.date between :start and :end")
    List<Schedule> findByUseridAndDateBetween(@Param("userid") String userid,
                                              @Param("start") LocalDate start , @Param("end")LocalDate end);

}
