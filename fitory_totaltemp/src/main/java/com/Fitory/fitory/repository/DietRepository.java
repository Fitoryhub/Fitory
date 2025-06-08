package com.Fitory.fitory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Fitory.fitory.entity.Diet;

import java.util.List;


public interface DietRepository extends JpaRepository<Diet, Integer>{
	
	@Query("select d.diet_id From Diet d where d.title = :title")
	public int findBytitle(@Param("title") String title);

	@Query(value="SElECT a.* " +
			"FROM (SELECT d.* , ROW_NUMBER() OVER() AS number "+
			"FROM Diet d ) a " +
			"WHERE number BETWEEN :start AND :end ",
			nativeQuery = true)
	public List<Diet> findpage(@Param("start") int start, @Param("end") int end);
}