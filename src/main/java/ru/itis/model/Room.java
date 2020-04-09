package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Room {
    private Integer id;
    private String token;
    private String name;
    private double tempIn;
    private double humidityIn;
    private double humidityBot;
}
