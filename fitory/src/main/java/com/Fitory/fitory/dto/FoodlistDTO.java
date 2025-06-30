package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodlistDTO {

	private String food_name;
	private double carbohydrate;
	private double fat;
	private double protein;
	private int calories;
	private int amount;
	private String mealtype;
	private int totalcal;
	private String standard;

}