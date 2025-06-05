package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DietRepository extends JpaRepository<Diet, Integer>{
	
	@Query("select d.diet_id From Diet d where d.title = :title")
	public int findBytitle(@Param("title") String title);
}
