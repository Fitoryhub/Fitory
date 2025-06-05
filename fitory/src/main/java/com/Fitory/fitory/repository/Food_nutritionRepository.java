package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Food_nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Food_nutritionRepository extends JpaRepository<Food_nutrition, Integer>{
	
	@Query("select f.food_nutrition_id From Food_nutrition f where f.diet_id= :did")
	public int[] findBydiet_id(@Param("did") int id);
}
