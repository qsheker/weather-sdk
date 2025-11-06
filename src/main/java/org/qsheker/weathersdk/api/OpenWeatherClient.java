package org.qsheker.weathersdk.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.qsheker.weathersdk.exceptions.WeatherException;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherClient {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
    private final String apiKey;
    private final RestTemplate restTemplate;

    public OpenWeatherClient(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new WeatherException("API key must not be null or empty");
        }
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }
     public JsonNode fetchFromApi(String cityName){
         try{
             String url = BASE_URL.replace("{city}", cityName).replace("{apiKey}",apiKey);
             var response = restTemplate.getForObject(url, JsonNode.class);

             if(response==null || response.isEmpty()){
                 throw new WeatherException("Empty response from OpenWeather API for city: " + cityName);
             }
             return response;
         }catch (Exception e){
             throw new WeatherException("Failed to fetch weather data for city: " + cityName + " — " + e.getMessage());
         }
     }
}
