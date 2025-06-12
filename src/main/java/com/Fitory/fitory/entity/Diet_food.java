package com.Fitory.fitory.entity;

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
@Table(name="diet_food")
@DynamicInsert
public class Diet_food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition="BIGINT")
	private int food_id;
	
	@Column(name="diet_id", columnDefinition = "BIGINT")
	private int diet_id;
	
	@Column(name="food_name", columnDefinition = "VARCHAR(100)")
	private String food_name;
	
	@Column(name="quantity", columnDefinition = "VARCHAR(50)")
	private String quantity;
	
	@Column(name="food_nutrition_id", columnDefinition = "BIGINT")
	private int food_nutrition_id;
}
