package com.student.quiz.model;

public class QuizCompletedEvent {

    private Long studentId;
    private String quizId;
    private String category;
    private int score;
    private int totalQuestions;
    private boolean passed;
    private int attemptNumber;

    public QuizCompletedEvent() {
    }

    public QuizCompletedEvent(
            Long studentId,
            String quizId,
            String category,
            int score,
            int totalQuestions,
            boolean passed,
            int attemptNumber) {

        this.studentId = studentId;
        this.quizId = quizId;
        this.category = category;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.passed = passed;
        this.attemptNumber = attemptNumber;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
}