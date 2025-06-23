package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.ExerciseRoutine;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRoutineRepository extends JpaRepository<ExerciseRoutine, Integer> {
    @Override
    <S extends ExerciseRoutine> S save(S entity);

    List<ExerciseRoutine> findByUserid(String userId);

    int deleteByroutineid(int routineid);

    ExerciseRoutine findFirstByUseridAndRoutine(String userid, String routine);

    @Query("select e from routines e where e.userid=:id and e.routine=:ename")
    ExerciseRoutine todayExercise(@Param("id") String id,
                                  @Param("ename") String ename);


}
/*
 @Query("select s from Schedule s where s.userid=:userid and s.date=:date")
    List<Schedule> todayCal(@Param("userid") String userid,
                          @Param("date") LocalDate date);
                          * */