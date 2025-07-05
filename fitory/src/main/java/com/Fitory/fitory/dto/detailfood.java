package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class detailfood {
    public detailfood(String name, String mealtype, double calories, double protein, double carbohydrate, double fat) {
        this.name = name;
        this.mealtype = mealtype;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }

    private double calories;

    private double protein;

    private String mealtype;

    private double carbohydrate;

    private double fat;

    private String name;


}
