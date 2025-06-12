package com.Fitory.fitory.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="diet_nutrition")
@DynamicInsert
public class Diet_nutrition {
	@Id
	@Column(name = "diet_id", columnDefinition = "BIGINT")
	private int dietid;
	
	@Column(name="calories", columnDefinition = "INT(11)")
	private int calories;
	
	@Column(name="protein", columnDefinition = "DECIMAL(6,2)")
	private double protein;
	
	@Column(name="carbohydrate", columnDefinition = "DECIMAL(6,2)")
	private double carbohydrate;
	
	@Column(name="fat", columnDefinition = "DECIMAL(6,2)")
	private double fat;
}
