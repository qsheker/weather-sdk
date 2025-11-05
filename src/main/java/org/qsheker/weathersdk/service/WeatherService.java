package org.qsheker.weathersdk.service;

import org.qsheker.weathersdk.models.WeatherData;

public interface WeatherService {
    WeatherData fetch(String cityName);
}
