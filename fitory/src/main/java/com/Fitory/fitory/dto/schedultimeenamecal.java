package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class schedultimeenamecal {

    private  int calorie;
    private String routine;
    private String schedule;
    private int time;


    public void setSchedule(LocalTime time) {

        this.schedule = time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
