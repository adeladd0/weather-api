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
@Builder
@AllArgsConstructor
public class Response {
    @JsonProperty("cod")
    private String responseCode;
    @JsonProperty("message")
    private String responseMessage;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;
    private String timezone;
    @JsonProperty("timezone_offset")
    private int timezoneOffset;
    private Current current;
    private ArrayList<Hourly> hourly;
    private ArrayList<Daily> daily;

    public Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}