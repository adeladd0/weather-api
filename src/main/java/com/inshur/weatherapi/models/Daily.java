package com.inshur.weatherapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class Daily{
    private int dt;
    private int sunrise;
    private int sunset;
    private int moonrise;
    private int moonset;
    @JsonProperty("moon_phase")
    private double moonPhase;
    @JsonProperty("temp")
    private Temp temperature;
    @JsonProperty("feels_like")
    private FeelsLike feelsLike;
    private int pressure;
    private int humidity;
    @JsonProperty("dew_point")
    private double dewPoint;
    @JsonProperty("wind_speed")
    private double windSpeed;
    @JsonProperty("wind_deg")
    private int windDegrees;
    @JsonProperty("wind_gust")
    private double windGust;
    private ArrayList<Weather> weather;
    private int clouds;
    private int pop;
    private double uvi;
}