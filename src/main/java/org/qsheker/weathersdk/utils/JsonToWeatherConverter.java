package org.qsheker.weathersdk.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.qsheker.weathersdk.models.WeatherData;

public class JsonToWeatherConverter {

    public WeatherData convert(JsonNode response){
        WeatherData data = new WeatherData();

        data.setCityId(response.get("id").asLong());
        data.setCityName(response.get("name").asText());
        data.setTemperature(response.get("main").get("temp").asDouble());
        data.setFeelsLike(response.get("main").get("feels_like").asDouble());
        data.setWeatherMain(response.get("weather").get(0).get("main").asText());
        data.setWeatherDescription(response.get("weather").get(0).get("description").asText());
        data.setWindSpeed(response.get("wind").get("speed").asDouble());
        data.setVisibility(response.get("visibility").asLong());
        data.setSunrise(response.get("sys").get("sunrise").asLong());
        data.setSunset(response.get("sys").get("sunset").asLong());
        data.setTimezone(response.get("timezone").asLong());
        data.setDateTime(response.get("dt").asLong());

        return data;
    }
}
