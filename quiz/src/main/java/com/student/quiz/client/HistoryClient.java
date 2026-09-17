package com.student.quiz.client;

import com.student.quiz.model.HistoryRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HistoryClient {

    private final RestClient restClient;

    public HistoryClient(RestClient.Builder builder) {

        this.restClient = builder
                .baseUrl("http://localhost:8084")
                .build();
    }

    public void saveHistory(HistoryRequest request) {

        restClient.post()
                .uri("/history")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}