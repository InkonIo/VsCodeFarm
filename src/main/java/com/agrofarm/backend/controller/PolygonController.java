package com.agrofarm.backend.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrofarm.backend.service.AgroMonitoringService;
import com.agrofarm.backend.service.WeatherService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174") // Разрешить доступ с фронта
public class PolygonController {

    private final AgroMonitoringService agroService;
    private final WeatherService weatherService;

    public PolygonController(AgroMonitoringService agroService, WeatherService weatherService) {
        this.agroService = agroService;
        this.weatherService = weatherService;
    }

    @GetMapping("/polygons")
    public String getPolygons() {
        return agroService.listPolygons();
    }

    @GetMapping("/weather/{polygonId}")
    public String getWeather(@PathVariable String polygonId) {
        return weatherService.getWeatherForPolygon(polygonId);
    }

    @GetMapping("/soil/{polygonId}")
    public String getSoil(@PathVariable String polygonId) {
        return agroService.getSoilData(polygonId);
    }
}

