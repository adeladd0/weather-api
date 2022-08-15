package com.inshur.weatherapi.httphandler;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inshur.weatherapi.constants.ResponseCode;
import com.inshur.weatherapi.constants.ResponseMessage;
import com.inshur.weatherapi.models.Request;
import com.inshur.weatherapi.models.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class OpenWeatherClient {
    private final RestTemplate restTemplate;

    public Response weatherForecast(Request request,String url,String apiKey)
    {


        log.info("===================================Request :: {} {}",url,request);


        Map<String, String> params = new HashMap<>();
        params.put("lat", request.getLatitude());
        params.put("lon", request.getLongitude());
        params.put("appid", apiKey);

        return getResponse(url, params);


    }

    private Response getResponse(String url,  Map<String, String> params) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity <Response> entity = new HttpEntity<>(headers);

            log.info("==========================Params ::{} ::{}" , params,url);
            ResponseEntity<Response> response = restTemplate.exchange
                    (url, HttpMethod.GET, entity, Response.class, params);



            log.info("==========================Response ::{}" , response);
            Response weatherResponse= response.getBody();


            if (weatherResponse != null && weatherResponse.getCurrent()!=null)
            {weatherResponse.setResponseCode(ResponseCode.SUCCESS);
                weatherResponse.setResponseMessage(ResponseMessage.SUCCESS);
            }
            return weatherResponse;

        }
        catch (HttpClientErrorException e) {
            log.error("Http error exception::{}",e.getResponseBodyAsString());
            try {
                Response weatherResponse = new ObjectMapper().readValue(e.getResponseBodyAsString(),Response.class);
                log.info("Response {}",weatherResponse);
                return weatherResponse;
            }
            catch(JsonProcessingException error)
            {
                log.error("Json processing error",e);
                return new Response(ResponseCode.ERROR_OCCURRED, ResponseMessage.ERROR);
            }
        }
        catch(Exception e)
        {
            log.error("Exception occurred sending request to open weather api**************",e);
            return new Response(ResponseCode.ERROR_OCCURRED, ResponseMessage.ERROR);
        }
    }
}
