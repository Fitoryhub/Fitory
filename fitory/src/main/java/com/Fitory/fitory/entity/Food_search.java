package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name="food_search")
@DynamicInsert
public class Food_search {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition="BIGINT")
	private int food_id;
	
	@Column(name="classification", columnDefinition = "VARCHAR(100)")
	private String classification;
	
	@Column(name="food_name", columnDefinition = "VARCHAR(100)")
	private String food_name;

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
	
	@Column(name="createdat", columnDefinition = "DATE")
	private Date createdat;
}
