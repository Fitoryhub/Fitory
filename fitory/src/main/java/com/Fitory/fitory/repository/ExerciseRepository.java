package com.Fitory.fitory.repository;


import com.Fitory.fitory.entity.Exercises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercises, Integer> {

    List<Exercises> findByMetrank(int metrank);

    List<Exercises> findAllByMetrank(int metrank);

    @Query("SELECT e FROM exercises e WHERE e.metrank = :met AND " +
            "e.bodyweight = :requiresEquipment AND " +
            "e.oxygen = :isAnaerobic")
    List<Exercises> findByMatchingExercise(
            @Param("met") int met,
            @Param("requiresEquipment") String requiresEquipment,
            @Param("isAnaerobic") String isAnaerobic
    );

    @Query("SELECT e FROM exercises e WHERE e.metrank = :met AND " +
            "e.oxygen = :isAnaerobic")
    List<Exercises> findByMetrankAndIsAnaerobic(
            @Param("met")int met,
            @Param("isAnaerobic") String isAnaerobic
    );

    @Query("SELECT e.ename FROM exercises e WHERE e.metrank = :met AND " +
            "e.bodyweight = :requiresEquipment")
    List<Exercises> findByMetrankAndrequiresEquipment(
            @Param("met")int met,
            @Param("requiresEquipment") String requiresEquipment
    );


    @Query("SELECT e.ename FROM exercises e WHERE e.oxygen = :s")
    List<String> findByOxygen(@Param("s") String s);



    @Query("SELECT e.ename FROM exercises e WHERE e.bodyweight = :s")
    List<String> findByBodyweight(@Param("s") String s);

}
