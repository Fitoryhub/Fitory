package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Schedule;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {


    @Query("select s from Schedule s where s.userid=:userid and s.date between :start and :end order by s.time asc")
    List<Schedule> findByUseridAndDateBetween(@Param("userid") String userid,
                                              @Param("start") LocalDate start, @Param("end") LocalDate end);

    List<Schedule> findByUseridAndDate(String id, LocalDate date);



    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.userid = :userid AND s.date = :date AND s.time = :time AND s.item = :item")
    void deleteByUseridAndDateAndTimeAndItem(
            @Param("id") String userid,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("item") String item);
}
