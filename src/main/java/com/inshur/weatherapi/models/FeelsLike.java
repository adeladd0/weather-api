package com.inshur.weatherapi.models;

import lombok.Data;

@Data
public class FeelsLike{
    private double day;
    private double night;
    private double eve;
    private double morn;
}