package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class detailfood {

    private double calories;


    private double protein;


    private double carbohydrate;

    private double fat;

    private String name;

    private String time;

    public void setTime(LocalTime time) {

        // 자동으로 문자열 포맷도 세팅
        this.time = time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
