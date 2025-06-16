package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Diet_food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Diet_foodRepository extends JpaRepository<Diet_food, Integer>{

    @Query(value = "SELECT f.* "+
            "       FROM diet_food f "+
            "       WHERE diet_id=:did"
            ,nativeQuery = true)
    public List<Diet_food> findAllByDiet_id(@Param("did") int did);

    List<Diet_food> findByDietid(int dietId);

    Diet_food findByFoodname(String name);
}

