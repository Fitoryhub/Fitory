package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Diet_nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Diet_nutritionRepository extends JpaRepository<Diet_nutrition, Integer>{
    Diet_nutrition findByDietid(int did);
}
