package org.qsheker.weathersdk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherData {
    private String cityName;
    private Double temperature;
    private Double feelsLike;
    private String weatherMain;
    private String weatherDescription;
    private Double windSpeed;
    private Long visibility;
    private Long sunrise;
    private Long sunset;
    private Long timezone;
    private Long dateTime;
    private Long cityId;
    private Instant lastUpdated;

}
