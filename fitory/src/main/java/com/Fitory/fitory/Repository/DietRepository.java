package com.Fitory.fitory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Fitory.fitory.Entity.Diet;


public interface DietRepository extends JpaRepository<Diet, Integer>{
	
	@Query("select d.diet_id From Diet d where d.title = :title")
	public int findBytitle(@Param("title") String title);
}
