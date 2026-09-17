package com.student.quiz.model;

public class StartQuizRequest {

    private Long studentId;
    private String category;

    public StartQuizRequest() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}