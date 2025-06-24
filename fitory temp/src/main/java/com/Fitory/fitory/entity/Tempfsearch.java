package com.Fitory.fitory.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Table(name="tempfsearch")
@DynamicInsert
public class Tempfsearch {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "foodname", columnDefinition = "VARCHAR(40)")
    private String foodname;

    @Column(name="calories", columnDefinition = "DECIMAL(20,6)")
    private int calories;

    @Column(name="protein", columnDefinition = "DECIMAL(20,6)")
    private double protein;

    @Column(name="carbohydrate", columnDefinition = "DECIMAL(20,6)")
    private double carbohydrate;

    @Column(name="fat", columnDefinition = "DECIMAL(20,6)")
    private double fat;
}
