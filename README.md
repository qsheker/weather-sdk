Weather SDK
============
A Java SDK for accessing OpenWeather API with caching and polling support.

Features:
---------
- OpenWeather API integration
- Smart caching (10-minute TTL, 10 cities max)
- Dual modes: On-Demand and Polling
- RestTemplate-based HTTP client
- Singleton instance management
- Spring Boot auto-configuration ready
- Comprehensive error handling
- Thread-safe operations

Installation:
-------------
Add to your Maven pom.xml:
```xml
<dependency>
    <groupId>org.qsheker</groupId>
    <artifactId>weather-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

Quick Start:
------------
```java
import org.qsheker.weathersdk.WeatherSDK;
import org.qsheker.weathersdk.Mode;
import org.qsheker.weathersdk.exceptions.WeatherException;
import org.qsheker.weathersdk.models.WeatherData;

public class WeatherApp {
    public static void main(String[] args) {
        try {
            WeatherSDK sdk = WeatherSDK.of("your-api-key", Mode.ON_DEMAND);
            WeatherData weather = sdk.getWeather("London");
            System.out.println("Temperature: " + weather.getTemperature().getTemp() + "°C");
            System.out.println("Weather: " + weather.getWeather().getDescription());
            System.out.println("Wind Speed: " + weather.getWind().getSpeed() + " m/s");
            sdk.shutdown();
        } catch (WeatherException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

API Response Format:
-------------------
```json
{
  "weather": {
    "main": "Clouds",
    "description": "scattered clouds"
  },
  "temperature": {
    "temp": 15.6,
    "feels_like": 14.2
  },
  "visibility": 10000,
  "wind": {
    "speed": 3.1
  },
  "datetime": 1675744800,
  "sys": {
    "sunrise": 1675751262,
    "sunset": 1675787560
  },
  "timezone": 3600,
  "name": "London"
}
```

Field Descriptions:
-------------------
weather: Weather condition information
- main: Main weather condition (Rain, Snow, Clouds, etc.)
- description: Detailed weather description
temperature: Temperature data in Celsius
- temp: Actual temperature
- feels_like: Human perception of weather
visibility: Visibility in meters
wind: Wind information
- speed: Wind speed in meters/second
datetime: Data calculation time (Unix timestamp)
sys: System information
- sunrise: Sunrise time (Unix timestamp)
- sunset: Sunset time (Unix timestamp)
timezone: Shift in seconds from UTC
name: City name

Spring Boot Usage:
-----------------
application.yml:
```yaml
weather:
  sdk:
    api-key: "your-api-key-here"
    mode: ON_DEMAND
```

WeatherController.java:
```java
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherSDK weatherSDK;
    public WeatherController(WeatherSDK weatherSDK) {
        this.weatherSDK = weatherSDK;
    }
    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city) {
        try {
            WeatherData weather = weatherSDK.getWeather(city);
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
```

Error Handling:
---------------
```java
public class WeatherException extends RuntimeException {
    public WeatherException(String message){
        super(message);
    }
}
```

Modes:
------
ON_DEMAND: Fetch data only when requested
POLLING: Automatically update cached cities in background

Best Practices:
---------------
1. Always call shutdown() when done to release resources
2. Use appropriate mode based on your application needs
3. Handle exceptions gracefully
4. Monitor API usage to avoid rate limiting
5. Use caching effectively for frequently accessed cities

Troubleshooting:
----------------
- "SDK instance with this API key already exists": Don't create multiple instances with same key
- "City not found": Verify city name spelling and format
- "Invalid API key": Check your OpenWeather API key configuration
- Network errors: Ensure internet connectivity and firewall settings

License:
--------
MIT License
