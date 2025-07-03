package com.Fitory.fitory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.Fitory.fitory.mapper")
public class FitoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitoryApplication.class, args);
	}

}
