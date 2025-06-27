package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Table(name="diet_food")
@DynamicInsert
public class Diet_food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition="BIGINT")
	private int food_id;
	
	@Column(name="diet_id", columnDefinition = "BIGINT")
	private int dietId;
	
	@Column(name="food_name", columnDefinition = "VARCHAR(100)")
	private String foodname;
	
	@Column(name="amount", columnDefinition = "VARCHAR(50)")
	private int amount;

	@Column(name="mealtype", columnDefinition="ENUM('breakfast','launch','dinner','else')")
	private String mealtype;
	
	@Column(name="food_nutrition_id", columnDefinition = "BIGINT")
	private int food_nutrition_id;
}
