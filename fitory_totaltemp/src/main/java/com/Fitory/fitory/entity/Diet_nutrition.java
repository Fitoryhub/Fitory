package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="diet_nutrition")
@DynamicInsert
public class Diet_nutrition {
	@Id
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
}
