package com.Fitory.fitory.repository;

import com.Fitory.fitory.dto.exerciseDTO;
import com.Fitory.fitory.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IF_exerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findByMetrank(int metrank);

    @Query("SELECT exercises from exercises where exercises.metrank = :met AND " +
            "exercises.requires_equipment = :requires_equipment AND " +
            "exercises.is_anaerobic = :is_anaerobic")
    List<Exercise> findByMatchingExercise(
            @Param("met") int met,
            @Param("requires_equipment") String requires_equipment,
            @Param("is_anaerobic") String is_anaerobic
    );
}
