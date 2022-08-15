package com.inshur.weatherapi.services;
import com.inshur.weatherapi.constants.ResponseCode;
import com.inshur.weatherapi.httphandler.ApplicationProperties;
import com.inshur.weatherapi.httphandler.OpenWeatherClient;
import com.inshur.weatherapi.models.Daily;
import com.inshur.weatherapi.models.Request;
import com.inshur.weatherapi.models.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@Slf4j
@NoArgsConstructor
public class OpenWeatherService {

    OpenWeatherClient openWeatherClient;
    ApplicationProperties applicationProperties;

    @Autowired
    public OpenWeatherService(OpenWeatherClient openWeatherClient, ApplicationProperties applicationProperties) {
        this.openWeatherClient = openWeatherClient;
        this.applicationProperties = applicationProperties;
    }

    public Response warmestWeatherInNextSevenDays(Request request)
    {
        String url=applicationProperties.getUrl();
        String apiKey=applicationProperties.getApiKey();
        Response response= openWeatherClient.weatherForecast(request,url,apiKey);
        return sortWeatherData(response);

    }

    public Response sortWeatherData(Response response) {
        if(response.getResponseCode().equals(ResponseCode.SUCCESS))
        {
            Comparator<Daily> sortByTemp = Comparator.comparing(a->a.getTemperature().getMax(),Comparator.reverseOrder());
            response.getDaily().sort(sortByTemp.thenComparing(Daily::getHumidity));
            response.getDaily().subList(1, response.getDaily().size()).clear();
        }
        return response;
    }


}

