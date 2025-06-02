package com.Fitory.fitory;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.Fitory.fitory.Repository.DietRepository;
import com.Fitory.fitory.Repository.Food_nutritionRepository;


import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = FitoryApplication.class)
class FitoryApplicationTests {
	
	@Autowired
	private final DietRepository dietrespo=null;
	@Autowired
	private final Food_nutritionRepository fnrepo=null;
	
	@Test
	void contextLoads() {
		
	}
}
