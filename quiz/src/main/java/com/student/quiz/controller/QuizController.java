package com.student.quiz.controller;

import com.student.quiz.dto.QuizResult;
import com.student.quiz.model.Quiz;
import com.student.quiz.model.QuizResponse;
import com.student.quiz.model.StartQuizRequest;
import com.student.quiz.model.SubmitQuizRequest;
import com.student.quiz.service.QuizService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    @PostMapping("/start")
    public QuizResponse startQuiz(
            @RequestBody StartQuizRequest request,
            Authentication authentication) {

        Long studentId =
                Long.parseLong(
                        authentication.getName()
                );

        Quiz quiz =
                quizService.startQuiz(
                        studentId,
                        request
                );

        return quizService.getQuizResponse(
                quiz
        );
    }


    @PostMapping("/{quizId}/submit")
    public QuizResult submitQuiz(
            @PathVariable String quizId,
            @RequestBody SubmitQuizRequest request) {

        return quizService.submitQuiz(
                quizId,
                request
        );
    }


    @PostMapping("/retry/{quizId}")
    public QuizResponse retryQuiz(
            @PathVariable String quizId,
            Authentication authentication) {

        Long studentId =
                Long.parseLong(
                        authentication.getName()
                );

        Quiz quiz =
                quizService.retryQuiz(
                        quizId,
                        studentId
                );

        return quizService.getQuizResponse(
                quiz
        );
    }
}