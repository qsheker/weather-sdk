package org.qsheker.weathersdk.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponseDto {
    @JsonProperty("weather")
    private Weather weather;

    @JsonProperty("temperature")
    private Temperature temperature;

    private Long visibility;

    private Wind wind;

    @JsonProperty("datetime")
    private Long dateTime;

    private Sys sys;

    private Long timezone;

    private String name;

    @Data
    @Builder
    public static class Weather{
        String main;
        String description;
    }

    @Data
    @Builder
    public static class Temperature{
        Double temp;
        Double feels_like;
    }

    @Data
    @Builder
    public static class Wind{
        Double speed;
    }
    @Data
    @Builder
    public static class Sys{
        Long sunrise;
        Long sunset;
    }
}
