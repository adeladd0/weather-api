package com.inshur.weatherapi.services;

import com.inshur.weatherapi.constants.ResponseCode;
import com.inshur.weatherapi.httphandler.ApplicationProperties;
import com.inshur.weatherapi.httphandler.OpenWeatherClient;
import com.inshur.weatherapi.models.Daily;
import com.inshur.weatherapi.models.Request;
import com.inshur.weatherapi.models.Response;
import com.inshur.weatherapi.models.Temp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class OpenWeatherServiceTest {



    OpenWeatherService underTest;

    @BeforeEach
    public void setUp(){

        underTest=new OpenWeatherService();

    }

   @ParameterizedTest(name = "{index} => a={0}, b={1}, c={2}")
   @CsvSource({
           "1, 1, 18",
           "44, 44, 10",
           "44, 44, 10",
   })
    void itShouldReturnWarmestDayInNextSevenDays(int a,int b,int c) {

        ArrayList<Daily> daily=new ArrayList<>();
        daily.add(Daily.builder().humidity(10).temperature(new Temp(21)).build());
        daily.add(Daily.builder().humidity(25).temperature(new Temp(22)).build());
        daily.add(Daily.builder().humidity(87).temperature(new Temp(a)).build());
        daily.add(Daily.builder().humidity(10).temperature(new Temp(b)).build());
        daily.add(Daily.builder().humidity(18).temperature(new Temp(44)).build());
        daily.add(Daily.builder().humidity(9).temperature(new Temp(22)).build());
        daily.add(Daily.builder().humidity(9).temperature(new Temp(29)).build());



        ArrayList<Daily> highestHumidity=new ArrayList<>();
        highestHumidity.add(Daily.builder().humidity(c).temperature(new Temp(44)).build());

        Response expected= Response.builder().responseCode(ResponseCode.SUCCESS)
                .daily(highestHumidity).build();

        Response response= Response.builder().responseCode(ResponseCode.SUCCESS)
                .daily(daily).build();

        underTest.sortWeatherData(response);

        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }



}