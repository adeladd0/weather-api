package com.inshur.weatherapi.httphandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inshur.weatherapi.constants.ResponseCode;
import com.inshur.weatherapi.models.Request;
import com.inshur.weatherapi.models.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@RestClientTest(OpenWeatherClient.class)
class OpenWeatherClientTest {

    @Value("${url}")
    String url;
    @Autowired
    private OpenWeatherClient underTest;

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;

    String expected= """
            {
              "timezone": "Africa/Tripoli",
              "current": {
                "dt": 1660571127,
                "sunrise": 1660536820,
                "sunset": 1660583258,
                "temp": 311.51,
                "pressure": 1007,
                "humidity": 13,
                "uvi": 3,
                "clouds": 1,
                "visibility": 10000,
                "weather": [
                  {
                    "id": 800,
                    "main": "Clear",
                    "description": "clear sky",
                    "icon": "01d"
                  }
                ],
                "feels_like": 308.95,
                "dew_point": 278.26,
                "wind_speed": 5.55,
                "wind_deg": 330,
                "wind_gust": 4.54
              },
              "hourly": null,
              "daily": [
                {
                  "dt": 1660730400,
                  "sunrise": 1660709662,
                  "sunset": 1660755969,
                  "moonrise": 1660769940,
                  "moonset": 1660727940,
                  "pressure": 1009,
                  "humidity": 12,
                  "weather": [
                    {
                      "id": 800,
                      "main": "Clear",
                      "description": "clear sky",
                      "icon": "01d"
                    }
                  ],
                  "clouds": 3,
                  "pop": 0,
                  "uvi": 10.88,
                  "moon_phase": 0.69,
                  "temp": {
                    "day": 310,
                    "min": 299,
                    "max": 313,
                    "night": 304,
                    "eve": 311,
                    "morn": 299
                  },
                  "feels_like": {
                    "day": 308.06,
                    "night": 302.75,
                    "eve": 308.74,
                    "morn": 299.77
                  },
                  "dew_point": 276.97,
                  "wind_speed": 7.69,
                  "wind_deg": 328,
                  "wind_gust": 14.21
                }
              ],
              "cod": "01",
              "message": "Successfully retrieved weather data",
              "lat": 22,
              "lon": 21,
              "timezone_offset": 7200
            }
            """;


    @BeforeEach
    public void setUp() {
        mockRestServiceServer= MockRestServiceServer.createServer(restTemplate);

    }
    @AfterEach
    void resetMockServer(){
        mockRestServiceServer.reset();
    }
    @Test
    void itShouldReturnWeatherForecast()
      {
          this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(url))
                  .andRespond(MockRestResponseCreators.withSuccess(expected, APPLICATION_JSON));

          Request request=new Request("22.0","21.0");
          Response response=underTest.weatherForecast(request,url,"123");

          assertThat(response.getResponseCode()).isEqualTo(ResponseCode.SUCCESS);
          assertThat(response.getDaily()).isNotEmpty();


      }

    @Test
    void itShouldFailWhenServerIsDown()
      {
          this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(url))
                  .andRespond(MockRestResponseCreators.withServerError());

          Request request=new Request("22.0","21.0");
          Response response=underTest.weatherForecast(request,url,"123");

          assertThat(response.getResponseCode()).isEqualTo(ResponseCode.ERROR_OCCURRED);
          assertThat(response.getDaily()).isNull();

      }

    @Test
    void itShouldFailWhenCredentialsAreWrong() throws JsonProcessingException {
        Response body=new Response(ResponseCode.UNAUTHORISED,"message");
        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(url))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.UNAUTHORIZED).
                        contentType(APPLICATION_JSON).body(new ObjectMapper().writeValueAsString(body)));

        Request request=new Request("22.0","21.0");
        Response response=underTest.weatherForecast(request,url,"123");

        assertThat(response.getResponseCode()).isEqualTo(ResponseCode.UNAUTHORISED);
        assertThat(response.getDaily()).isNull();

    }
}