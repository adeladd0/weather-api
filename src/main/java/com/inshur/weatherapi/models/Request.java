package com.inshur.weatherapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank
    private String latitude;
    @NotBlank
    private String longitude;
}
