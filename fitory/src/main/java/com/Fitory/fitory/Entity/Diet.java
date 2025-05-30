package com.Fitory.fitory.Entity;

import org.hibernate.annotations.ColumnDefault;
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
@Table(name="diet")
@DynamicInsert
public class Diet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition="BIGINT")
	private Long diet_id;
	
	@Column(name="title", columnDefinition = "VARCHAR(100)")
	private String title;
	
	@Column(name="explanation", columnDefinition="TEXT")
	private String explanation;
	
	@Column(name="goal_type", columnDefinition="ENUM('WEIGHT_LOSS','MUSCLE_GAIN','MAINTAIN')")
	private String goal_type;
	
	@Column(name="plan_type", columnDefinition="ENUM('DAY','WEEK')")
	private String plan_type;
	
	@Column(name="created_at", columnDefinition = "DATETIME")
	@ColumnDefault("SYSDATE()")
	private String created_at;
}
