package org.qsheker.weathersdk.service;

import org.qsheker.weathersdk.api.OpenWeatherClient;
import org.qsheker.weathersdk.cache.WeatherCache;
import org.qsheker.weathersdk.exceptions.WeatherException;
import org.qsheker.weathersdk.models.WeatherData;
import org.qsheker.weathersdk.utils.JsonToWeatherConverter;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingService {
    private final WeatherCache cache;
    private final OpenWeatherClient client;
    private final JsonToWeatherConverter converter = new JsonToWeatherConverter();
    private final ScheduledExecutorService scheduler;
    private final int pollingIntervalMinutes;

    public PollingService(WeatherCache cache, OpenWeatherClient client,int pollingIntervalMinutes) {
        this.cache = cache;
        this.client = client;
        this.pollingIntervalMinutes = pollingIntervalMinutes;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void startPolling() {
        scheduler.scheduleAtFixedRate(this::updateAllCachedCities, 0, pollingIntervalMinutes, TimeUnit.MINUTES);
    }

    public void stopPolling() {
        scheduler.shutdown();
    }

    private void updateAllCachedCities() {
        cache.getAll().keySet().forEach(city -> {
            try {
                var json = client.fetchFromApi(city);
                WeatherData data = converter.convert(json);
            } catch (WeatherException e) {
                System.err.println("Failed to update weather for city: " + city + ", error: " + e.getMessage());
            }
        });
    }
}

