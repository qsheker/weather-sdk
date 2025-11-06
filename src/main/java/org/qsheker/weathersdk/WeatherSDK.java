package org.qsheker.weathersdk;

import org.qsheker.weathersdk.api.OpenWeatherClient;
import org.qsheker.weathersdk.cache.WeatherCache;
import org.qsheker.weathersdk.exceptions.WeatherException;
import org.qsheker.weathersdk.models.WeatherData;
import org.qsheker.weathersdk.service.PollingService;
import org.qsheker.weathersdk.service.WeatherService;
import org.qsheker.weathersdk.service.impl.WeatherServiceImpl;
import org.qsheker.weathersdk.web.mappers.WeatherResponseMapper;

public class WeatherSDK {
    private final String apiKey;
    private final Mode mode;
    private final WeatherService weatherService;
    private PollingService pollingService;

    WeatherSDK(String apiKey, Mode mode) throws WeatherException {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new WeatherException("API key cannot be null or empty");
        }

        this.apiKey = apiKey;
        this.mode = mode;

        OpenWeatherClient client = new OpenWeatherClient(apiKey);
        WeatherCache cache = new WeatherCache();
        this.weatherService = new WeatherServiceImpl(client,cache);

        if (mode == Mode.POLLING) {
            this.pollingService = new PollingService(cache, client, 10);
            pollingService.startPolling();
        }
    }

    public static WeatherSDK of(String apiKey, Mode mode) throws WeatherException {
        return WeatherInstanceRegistry.getInstance(apiKey, mode);
    }

    public WeatherData getWeather(String cityName) throws WeatherException {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new WeatherException("City name cannot be null or empty");
        }
        return weatherService.fetch(cityName);
    }

    public void shutdown() {
        if (pollingService != null) {
            pollingService.stopPolling();
        }
        WeatherInstanceRegistry.deleteInstance(apiKey);
    }

    public String getApiKey() {
        return apiKey;
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isActive() {
        return WeatherInstanceRegistry.isInstanceRegistered(apiKey);
    }
}
