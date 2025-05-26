package com.Fitory.fitory.repository;


import com.Fitory.fitory.entity.Exercises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercises, Integer> {

    List<Exercises> findByMetrank(int metrank);

    List<Exercises> findAllByMetrank(int metrank);

    @Query("SELECT e FROM Exercises e WHERE e.metrank = :met AND " +
            "e.requiresEquipment = :requiresEquipment AND " +
            "e.isAnaerobic = :isAnaerobic")
    List<Exercises> findByMatchingExercise(
            @Param("met") int met,
            @Param("requiresEquipment") String requiresEquipment,
            @Param("isAnaerobic") String isAnaerobic
    );

    @Query("SELECT e FROM Exercises e WHERE e.metrank = :met AND " +
            "e.isAnaerobic = :isAnaerobic")
    List<Exercises> findByMetrankAndIsAnaerobic(
            @Param("met")int met,
            @Param("isAnaerobic") String isAnaerobic
    );

    @Query("SELECT e FROM Exercises e WHERE e.metrank = :met AND " +
            "e.requiresEquipment = :requiresEquipment")
    List<Exercises> findByMetrankAndrequiresEquipment(
            @Param("met")int met,
            @Param("requiresEquipment") String requiresEquipment
    );


}
