package org.qsheker.weathersdk;

import org.qsheker.weathersdk.exceptions.WeatherException;

import java.util.HashMap;
import java.util.Map;

class WeatherInstanceRegistry {
    private static final Map<String, WeatherSDK> instances = new HashMap<>();

    public static WeatherSDK getInstance(String apiKey, Mode mode) throws WeatherException {
        synchronized (instances) {
            if (instances.containsKey(apiKey)) {
                WeatherSDK existingInstance = instances.get(apiKey);
                if (existingInstance.getMode() != mode) {
                    throw new WeatherException("SDK instance with this API key already exists with different mode");
                }
                throw new WeatherException("SDK instance with this API key already exists");
            }

            WeatherSDK sdk = new WeatherSDK(apiKey, mode);
            instances.put(apiKey, sdk);
            return sdk;
        }
    }

    public static void deleteInstance(String apiKey) {
        synchronized (instances) {
            instances.remove(apiKey);
        }
    }

    public static boolean isInstanceRegistered(String apiKey) {
        synchronized (instances) {
            return instances.containsKey(apiKey);
        }
    }

    public static int getInstanceCount() {
        synchronized (instances) {
            return instances.size();
        }
    }
}

