package com.inshur.weatherapi.httphandler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix="openweather")
@Configuration
public class ApplicationProperties {
    private String url;
    private String apiKey;
}
