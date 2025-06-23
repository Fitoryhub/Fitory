package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class detailfood {

    private int calories;


    private double protein;


    private double carbohydrate;

    private double fat;

    private String name;
    LocalTime time;

}
