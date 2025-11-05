package org.qsheker.weathersdk.web.mappers;


import org.qsheker.weathersdk.models.WeatherData;
import org.qsheker.weathersdk.web.dto.WeatherResponseDto;

public class WeatherResponseMapper implements Mapper<WeatherData, WeatherResponseDto>{
    @Override
    public WeatherResponseDto toDto(WeatherData from) {

        WeatherResponseDto.Weather weather = WeatherResponseDto.Weather.builder()
                .main(from.getWeatherMain())
                .description(from.getWeatherDescription())
                .build();
        WeatherResponseDto.Temperature temperature = WeatherResponseDto.Temperature.builder()
                .temp(from.getTemperature())
                .feels_like(from.getFeelsLike())
                .build();

        WeatherResponseDto.Wind wind = WeatherResponseDto.Wind.builder()
                .speed(from.getWindSpeed())
                .build();
        WeatherResponseDto.Sys sys = WeatherResponseDto.Sys.builder()
                .sunset(from.getSunset())
                .sunrise(from.getSunrise())
                .build();

        return WeatherResponseDto.builder()
                .weather(weather)
                .visibility(from.getVisibility())
                .temperature(temperature)
                .wind(wind)
                .sys(sys)
                .name(from.getCityName())
                .dateTime(from.getDateTime())
                .timezone(from.getTimezone())
                .build();
    }

    @Override
    public WeatherData toObject(WeatherResponseDto to) {
        if (to == null) {
            return null;
        }
        WeatherData weatherData = new WeatherData();
        weatherData.setCityName(to.getName());
        weatherData.setVisibility(to.getVisibility());
        weatherData.setTimezone(to.getTimezone());
        weatherData.setDateTime(to.getDateTime());

        if (to.getWeather() != null) {
            weatherData.setWeatherMain(to.getWeather().getMain());
            weatherData.setWeatherDescription(to.getWeather().getDescription());
        }
        if (to.getTemperature() != null) {
            weatherData.setTemperature(to.getTemperature().getTemp());
            weatherData.setFeelsLike(to.getTemperature().getFeels_like());
        }
        if (to.getWind() != null) {
            weatherData.setWindSpeed(to.getWind().getSpeed());
        }
        if (to.getSys() != null) {
            weatherData.setSunrise(to.getSys().getSunrise());
            weatherData.setSunset(to.getSys().getSunset());
        }
        return weatherData;
    }
}
