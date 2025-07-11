package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Food_nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Food_nutritionRepository extends JpaRepository<Food_nutrition, Integer>{

	@Query("select f.foodnutritionid From Food_nutrition f where f.dietId= :did")
	public List<Integer> findBydietId_fid(@Param("did") int id);

	public void deleteAllBydietId(int diet_id);


	@Query("select calories from Food_nutrition  WHERE foodnutritionid=:id")
	int getCaloriesByFood_nutrition_id(@Param("id") int id);

	public List<Food_nutrition> findAllBydietId(int dietId);

	Food_nutrition findByFoodnutritionid(int foodNutritionId);

	List<Food_nutrition> findBydietId(int diet_id);
}