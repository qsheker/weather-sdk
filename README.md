# Weather SDK

A Java SDK for accessing OpenWeather API with caching and polling support.

## Features

- ✅ OpenWeather API integration
- ✅ Smart caching (10-minute TTL, 10 cities max)
- ✅ Dual modes: On-Demand and Polling
- ✅ RestTemplate-based HTTP client
- ✅ Singleton instance management
- ✅ Spring Boot auto-configuration ready

## Installation

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>org.qsheker</groupId>
    <artifactId>weather-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
