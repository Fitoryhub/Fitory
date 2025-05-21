package com.Fitory.fitory.repository;

import com.Fitory.fitory.dto.exerciseDTO;
import com.Fitory.fitory.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IF_exerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findByMetrank(int metrank);
}
