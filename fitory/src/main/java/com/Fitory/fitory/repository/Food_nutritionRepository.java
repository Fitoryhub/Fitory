package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Food_nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Food_nutritionRepository extends JpaRepository<Food_nutrition, Integer>{
	
	@Query("select f.food_nutrition_id From Food_nutrition f where f.dietId= :did")
	public List<Integer> findBydietId(@Param("did") int id);

	public void deleteAllBydietId(int diet_id);


	@Query("select calories from Food_nutrition  WHERE food_nutrition_id=:id")
	int getCaloriesByFood_nutrition_id(@Param("id") int id);

}