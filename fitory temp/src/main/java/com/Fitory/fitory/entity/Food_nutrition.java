package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Table(name="food_nutrition")
@DynamicInsert
public class Food_nutrition {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT")
	private int food_nutrition_id;
	
	@Column(name = "diet_id",columnDefinition = "BIGINT")
	private int dietId;
	
	@Column(name="calories", columnDefinition = "INT(11)")
	private int calories;
	
	@Column(name="protein", columnDefinition = "DECIMAL(6,2)")
	private double protein;
	
	@Column(name="carbohydrate", columnDefinition = "DECIMAL(6,2)")
	private double carbohydrate;
	
	@Column(name="fat", columnDefinition = "DECIMAL(6,2)")
	private double fat;
	
	@Column(name="standard")
	private String standard;
}
