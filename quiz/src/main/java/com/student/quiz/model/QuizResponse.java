package com.student.quiz.model;

import java.util.List;

public class QuizResponse {

    private String quizId;
    private String category;
    private List<QuizQuestionResponse> questions;

    public QuizResponse() {
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<QuizQuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestionResponse> questions) {
        this.questions = questions;
    }
}