package com.student.questions.controller;

import com.student.questions.entity.Question;
import com.student.questions.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    final private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> questionList() {
        return questionService.getQuestions();
    }

    @GetMapping("/category/{category}")
    public List<Question> getCategoryQuestions(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("/category/{category}/random")
    public List<Question> getRandomQuestions(@PathVariable String category) {
        return questionService.getRandomQuestionsByCategory(category);
    }
}