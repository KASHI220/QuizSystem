package com.student.quiz.client;

import com.student.quiz.model.QuizQuestion;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class QuestionClient {

    private final RestClient restClient;

    public QuestionClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8082")
                .build();
    }

    public List<QuizQuestion> getRandomQuestions(String category) {

        return restClient.get()
                .uri("/questions/category/{category}/random", category)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<QuizQuestion>>() {});
    }
}