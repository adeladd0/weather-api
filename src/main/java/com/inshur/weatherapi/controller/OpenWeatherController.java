package com.inshur.weatherapi.controller;



import com.inshur.weatherapi.models.Request;
import com.inshur.weatherapi.models.Response;
import com.inshur.weatherapi.services.OpenWeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("weather")
@AllArgsConstructor
public class OpenWeatherController {

    OpenWeatherService openWeatherService;


    @PostMapping("/data")
    public Response warmestWeatherInNextSevenDays(@Valid @RequestBody Request request)
    { return openWeatherService.warmestWeatherInNextSevenDays(request);
    }

}
