package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Indication {


    private Integer id;
    private String token;

    private double tempIn;
    private double tempOut;
    private double humidityIn;
    private double humidityOut;
    private double humidityBot;
    private double pressure;

    private Date date;
}
