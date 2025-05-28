package com.agrofarm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrofarm.backend.service.OpenAiService;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private OpenAiService aiService;  // Здесь правильный сервис

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        try {
            return aiService.ask(message);  // вызываем метод ask из сервиса
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
