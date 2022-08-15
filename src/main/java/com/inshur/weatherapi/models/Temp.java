package com.inshur.weatherapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temp{
    private int day;
    private int min;
    private int max;
    private int night;
    private int eve;
    private int morn;

    public Temp(int max) {
        this.max = max;
    }
}