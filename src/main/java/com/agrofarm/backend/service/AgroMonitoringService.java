package com.agrofarm.backend.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class AgroMonitoringService {

    private final String apiKey = "e38bbe663df68e5f56afbc969a1b9176";
    private final OkHttpClient client = new OkHttpClient();

    public String listPolygons() {
        try {
            String url = "https://api.agromonitoring.com/agro/1.0/polygons?appid=" + apiKey;
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } catch (IOException e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    public String getSoilData(String polygonId) {
        try {
            String url = "https://api.agromonitoring.com/agro/1.0/soil?polyid=" + polygonId + "&appid=" + apiKey;
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } catch (IOException e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
