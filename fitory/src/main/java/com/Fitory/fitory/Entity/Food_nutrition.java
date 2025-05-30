package com.Fitory.fitory.Entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
	
	@Column(columnDefinition = "BIGINT")
	private int diet_id;
	
	@Column(name="calories", columnDefinition = "INT(11)")
	private int calories;
	
	@Column(name="protein", columnDefinition = "DECIMAL(6,2)")
	private double protein;
	
	@Column(name="carbohydrate", columnDefinition = "DECIMAL(6,2)")
	private double carbohydrate;
	
	@Column(name="fat", columnDefinition = "DECIMAL(6,2)")
	private double fat;
	
	@Column(name="sodium", columnDefinition = "DECIMAL(6,2)")
	private double sodium;
	
	@Column(name="sugar", columnDefinition = "DECIMAL(6,2)")
	private double sugar;
}
