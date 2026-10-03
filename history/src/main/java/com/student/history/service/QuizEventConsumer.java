package com.student.history.service;

import com.student.history.entity.QuizCompletedEvent;
import com.student.history.entity.History;
import com.student.history.repo.HistoryRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QuizEventConsumer {

    private final HistoryRepository historyRepository;

    public QuizEventConsumer(
            HistoryRepository historyRepository) {

        this.historyRepository = historyRepository;
    }

    @KafkaListener(
            topics = "quiz-completed",
            groupId = "history-service"
    )
    public void consumeQuizCompleted(
            QuizCompletedEvent event) {

        System.out.println(
                "Received quiz completed event: "
                        + event.getQuizId()
        );

        History history = new History();

        history.setStudentId(
                event.getStudentId()
        );

        history.setQuizId(
                event.getQuizId()
        );

        history.setCategory(
                event.getCategory()
        );

        history.setTotalQuestions(
                event.getTotalQuestions()
        );

        history.setCorrectAnswers(
                event.getScore()
        );

        history.setWrongAnswers(
                event.getTotalQuestions()
                        - event.getScore()
        );

        history.setScore(
                event.getScore()
        );

        history.setPassed(
                event.isPassed()
        );

        history.setAttemptNumber(
                event.getAttemptNumber()
        );

        historyRepository.save(history);

        System.out.println(
                "Quiz history saved successfully: "
                        + event.getQuizId()
        );
    }
}