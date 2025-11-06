package org.qsheker.weathersdk.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.qsheker.weathersdk.api.OpenWeatherClient;
import org.qsheker.weathersdk.cache.WeatherCache;
import org.qsheker.weathersdk.exceptions.WeatherException;
import org.qsheker.weathersdk.models.WeatherData;
import org.qsheker.weathersdk.service.WeatherService;
import org.qsheker.weathersdk.utils.JsonToWeatherConverter;


public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherClient openWeatherClient;
    private final WeatherCache cache;

    public WeatherServiceImpl(OpenWeatherClient client, WeatherCache cache) {
        this.openWeatherClient = client;
        this.cache = cache;
    }

    @Override
    public WeatherData fetch(String cityName) {
        WeatherData cached = cache.get(cityName);

        if (cached != null) {
            return cached;
        }
        try {
            JsonNode newData = openWeatherClient.fetchFromApi(cityName);
            JsonToWeatherConverter converter = new JsonToWeatherConverter();
            WeatherData data = converter.convert(newData);

            cache.put(cityName, data);
            return data;

        } catch (WeatherException e) {
            if (cached != null) return cached;
            throw e;
        }
    }
}
