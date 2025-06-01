package com.Fitory.fitory.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Fitory.fitory.Entity.Food_search;




public interface Food_searchRepository extends JpaRepository<Food_search, Integer>{
	
	@Query("SELECT COUNT(*) FROM Food_search f WHERE f.classification = :ftype")
	public int countByclassification(@Param("ftype") String ftype);
	
	/*@Query("SELECT a.food_name AS food_name, a.createdat AS createdat "
			+ "FROM (SELECT f.food_name AS food_name, f.createdat AS createdat, ROW_NUMBER() OVER() AS number "
			+ "		FROM Food_search f "
			+ "		WHERE f.classification = :ftype) AS a "
			+ "WHERE number BETWEEN :start AND :end ")
    public List<Foodname> findByclassification(@Param("ftype") String ftype, @Param("start") int start, @Param("end") int end);
	
	public interface Foodname {
		String getFood_name();
		Date getCreatedat();
	}*/
	@Query(value="SELECT a.food_name AS food_name, a.calories AS calories, a.protein AS protein, a.carbohydrate AS carbohydrate, a.fat AS fat, a.sodium as sodium, a.sugar AS sugar "
			+"FROM (SELECT f.food_name, f.calories, f.protein, f.carbohydrate, f.fat, f.sodium, f.sugar, ROW_NUMBER() OVER() AS number "
			+"		FROM Food_search f "
			+"		WHERE f.classification = :ftype) AS a "
			+"WHERE number BETWEEN :start AND :end ",
			nativeQuery=true)
	public List<Foodlist> findByclassification(@Param("ftype") String ftype, @Param("start") int start, @Param("end") int end);
	
	public interface Foodlist{
		String getFood_name();
		int getCalories();
		double getCarbohydrate();
		double getProtein();
		double getFat();
		double getSodium();
		double getSugar();
	}
	
	@Query(value="SELECT f.food_name, f.calories, f.protein, f.carbohydrate, f.fat, f.sodium, f.sugar "
			+ "FROM food_search f "
			+ "WHERE f.food_name=:fname "
			+ "LIMIT 1",
			nativeQuery=true)
	public Foodlist findByFood_name(@Param("fname") String fname);
	
}
