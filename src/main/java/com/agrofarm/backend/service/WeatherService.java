package com.agrofarm.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final String apiKey = "796f43583579cfa479b03e29ae5410ea";
    private final OkHttpClient client = new OkHttpClient();
    private final AgroMonitoringService agroService;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherService(AgroMonitoringService agroService) {
        this.agroService = agroService;
    }

    public String getWeatherForPolygon(String polygonId) {
        try {
            String polygonsJson = agroService.listPolygons();
            JsonNode polygons = mapper.readTree(polygonsJson);
            for (JsonNode polygon : polygons) {
                if (polygon.get("id").asText().equals(polygonId)) {
                    double lat = polygon.get("center").get(1).asDouble();
                    double lon = polygon.get("center").get(0).asDouble();
                    return getCurrentWeather(lat, lon);
                }
            }
            return "{\"error\":\"Polygon not found\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private String getCurrentWeather(double lat, double lon) throws Exception {
        String url = "https://api.openweathermap.org/data/2.5/weather?"
                + "lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
