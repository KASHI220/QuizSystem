package com.student.quiz.model;

import java.util.List;

public class AnswerRequest {

    private String questionId;
    private String answer;

    public AnswerRequest() {
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}