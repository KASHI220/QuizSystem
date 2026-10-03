package com.student.quiz.service;

import com.student.quiz.model.QuizCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuizEventProducer {

    private final KafkaTemplate<String, QuizCompletedEvent> kafkaTemplate;

    public QuizEventProducer(
            KafkaTemplate<String, QuizCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishQuizCompleted(QuizCompletedEvent event) {

        kafkaTemplate.send(
                "quiz-completed",
                event.getQuizId(),
                event
        );
    }
}