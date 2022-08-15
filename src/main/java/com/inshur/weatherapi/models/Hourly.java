package com.inshur.weatherapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Hourly{
    private int dt;
    private double temp;
    private int pressure;
    private int humidity;
    private double uvi;
    private int clouds;
    private int visibility;
    @JsonProperty("feels_like")
    private FeelsLike feelsLike;
    @JsonProperty("dew_point")
    private double dewPoint;
    @JsonProperty("wind_speed")
    private double windSpeed;
    @JsonProperty("wind_deg")
    private int windDegrees;
    @JsonProperty("wind_gust")
    private ArrayList<Weather> weather;
    private int pop;
}
