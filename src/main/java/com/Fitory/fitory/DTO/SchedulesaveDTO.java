package com.Fitory.fitory.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class SchedulesaveDTO {

    private String id;


    private String date;


    private String time;

    private String type;

    private String item;


}
