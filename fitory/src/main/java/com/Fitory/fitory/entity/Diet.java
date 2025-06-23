package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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

	@Column(name="user_id")
	private String userid;

	@Column(name="diet_like", columnDefinition = "BIGINT")
	@ColumnDefault("0")
	private int diet_like;

	@Column(name="diet_view", columnDefinition = "BIGINT")
	@ColumnDefault("0")
	private int diet_view;
}
