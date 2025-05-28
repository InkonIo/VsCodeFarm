package com.agrofarm.backend.service;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class OpenAiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    String API_KEY = System.getenv("OPENAI_API_KEY");

    private final OkHttpClient client = new OkHttpClient();

    public String ask(String prompt) throws IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");

        JSONArray messages = new JSONArray();
        // Добавим системное сообщение с твоим промптом агронома:
        messages.put(new JSONObject().put("role", "system").put("content",
            "Ты цифровой помощник агроном. Будешь отвечать и помогать мне по теме почвы и погоды."));
        // Сообщение пользователя:
        messages.put(new JSONObject().put("role", "user").put("content", prompt));
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            JSONObject json = new JSONObject(body);
            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        }
    }
}
