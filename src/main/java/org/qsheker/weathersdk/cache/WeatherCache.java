package org.qsheker.weathersdk.cache;

import org.qsheker.weathersdk.models.WeatherData;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class WeatherCache {
    private static final int MAX_CITIES = 10;
    private static final int CACHE_TTL_MINUTES = 10;

    private final Map<String, WeatherData> cache;

    public WeatherCache() {
        this.cache = new LinkedHashMap<>(MAX_CITIES, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, WeatherData> eldest) {
                return size() > MAX_CITIES;
            }
        };
    }

    public WeatherData get(String cityName) {
        WeatherData data = cache.get(cityName.toLowerCase());
        if (data != null && isDataFresh(data)) {
            return data;
        }
        return null;
    }

    public void put(String cityName, WeatherData data) {
        cache.put(cityName.toLowerCase(), data);
    }

    public void remove(String cityName) {
        cache.remove(cityName.toLowerCase());
    }

    public void clear() {
        cache.clear();
    }

    public boolean contains(String cityName) {
        return cache.containsKey(cityName.toLowerCase());
    }

    private boolean isDataFresh(WeatherData data) {
        return ChronoUnit.MINUTES.between(data.getLastUpdated(), Instant.now()) < CACHE_TTL_MINUTES;
    }

    public Map<String, WeatherData> getAll() {
        return new LinkedHashMap<>(cache);
    }
}
