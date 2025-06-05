package com.Fitory.fitory.VO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodlistVO {
	
	private String food_name;
	private double carbohydrate;
	private double fat;
	private double protein;
	private int calories;
	private double sugar;
	private double sodium;
}
