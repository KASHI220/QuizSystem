package com.student.quiz.service;

import com.student.quiz.client.HistoryClient;
import com.student.quiz.client.QuestionClient;
import com.student.quiz.dto.QuizResult;
import com.student.quiz.model.AnswerRequest;
import com.student.quiz.model.HistoryRequest;
import com.student.quiz.model.Quiz;
import com.student.quiz.model.QuizQuestion;
import com.student.quiz.model.QuizQuestionResponse;
import com.student.quiz.model.QuizResponse;
import com.student.quiz.model.StartQuizRequest;
import com.student.quiz.model.SubmitQuizRequest;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QuizService {

    private final QuestionClient questionClient;
    private final HistoryClient historyClient;

    private final Map<String, Quiz> quizStore = new HashMap<>();

    public QuizService(
            QuestionClient questionClient,
            HistoryClient historyClient) {

        this.questionClient = questionClient;
        this.historyClient = historyClient;
    }

    // =========================
    // START QUIZ
    // =========================

    public Quiz startQuiz(
            Long studentId,
            StartQuizRequest request) {

        List<QuizQuestion> questions =
                questionClient.getRandomQuestions(
                        request.getCategory()
                );

        Quiz quiz = new Quiz();

        quiz.setQuizId(UUID.randomUUID().toString());
        quiz.setStudentId(studentId);
        quiz.setCategory(request.getCategory());
        quiz.setQuestions(questions);

        // First attempt
        quiz.setAttemptNumber(1);

        quiz.setCompleted(false);
        quiz.setPassed(false);

        // Store quiz in memory
        quizStore.put(
                quiz.getQuizId(),
                quiz
        );

        return quiz;
    }


    // =========================
    // SUBMIT QUIZ
    // =========================

    public QuizResult submitQuiz(
            String quizId,
            SubmitQuizRequest request) {

        Quiz quiz = quizStore.get(quizId);

        // Quiz doesn't exist
        if (quiz == null) {
            throw new RuntimeException("Quiz not found");
        }

        // Prevent submitting the same quiz twice
        if (quiz.isCompleted()) {
            throw new RuntimeException(
                    "Quiz already submitted"
            );
        }

        int correct = 0;

        // Check submitted answers
        for (AnswerRequest submittedAnswer
                : request.getAnswers()) {

            for (QuizQuestion question
                    : quiz.getQuestions()) {

                if (question.getQuestionId()
                        .equals(
                                submittedAnswer.getQuestionId()
                        )) {

                    if (question.getCorrectAnswer()
                            .equalsIgnoreCase(
                                    submittedAnswer.getAnswer()
                            )) {

                        correct++;
                    }

                    break;
                }
            }
        }

        int total =
                quiz.getQuestions().size();

        int wrong =
                total - correct;

        // 50% or more = pass
        boolean passed =
                correct >= total / 2.0;

        // Update quiz state
        quiz.setCompleted(true);
        quiz.setPassed(passed);

        // Retry is allowed only when:
        // 1. Student failed
        // 2. This was attempt 1
        boolean retryAllowed =
                !passed
                        && quiz.getAttemptNumber() == 1;


        // =========================
        // CREATE RESULT
        // =========================

        QuizResult result = new QuizResult();

        result.setQuizId(quizId);
        result.setTotalQuestions(total);
        result.setCorrectAnswers(correct);
        result.setWrongAnswers(wrong);
        result.setScore(correct);
        result.setPassed(passed);

        result.setAttemptNumber(
                quiz.getAttemptNumber()
        );

        result.setRetryAllowed(
                retryAllowed
        );


        // =========================
        // SAVE HISTORY
        // =========================

        HistoryRequest history =
                new HistoryRequest();

        history.setStudentId(
                quiz.getStudentId()
        );

        history.setQuizId(
                quiz.getQuizId()
        );

        history.setCategory(
                quiz.getCategory()
        );

        history.setTotalQuestions(
                total
        );

        history.setCorrectAnswers(
                correct
        );

        history.setWrongAnswers(
                wrong
        );

        history.setScore(
                correct
        );

        history.setPassed(
                passed
        );

        history.setAttemptNumber(
                quiz.getAttemptNumber()
        );

        historyClient.saveHistory(history);

        return result;
    }


    // =========================
    // RETRY QUIZ
    // =========================

    public Quiz retryQuiz(
            String quizId,
            Long studentId) {

        Quiz oldQuiz =
                quizStore.get(quizId);

        // Quiz doesn't exist
        if (oldQuiz == null) {
            throw new RuntimeException(
                    "Quiz not found"
            );
        }

        // Make sure student owns this quiz
        if (!oldQuiz.getStudentId()
                .equals(studentId)) {

            throw new RuntimeException(
                    "You cannot retry another student's quiz"
            );
        }

        // Student must submit first
        if (!oldQuiz.isCompleted()) {

            throw new RuntimeException(
                    "Submit the current quiz before retrying"
            );
        }

        // Don't allow retry after passing
        if (oldQuiz.isPassed()) {

            throw new RuntimeException(
                    "You already passed the quiz"
            );
        }

        // Only one retry
        if (oldQuiz.getAttemptNumber() >= 2) {

            throw new RuntimeException(
                    "No more attempts allowed"
            );
        }

        // Get new random questions
        List<QuizQuestion> questions =
                questionClient.getRandomQuestions(
                        oldQuiz.getCategory()
                );


        // Create attempt 2
        Quiz retryQuiz = new Quiz();

        retryQuiz.setQuizId(
                UUID.randomUUID().toString()
        );

        retryQuiz.setStudentId(
                studentId
        );

        retryQuiz.setCategory(
                oldQuiz.getCategory()
        );

        retryQuiz.setQuestions(
                questions
        );

        retryQuiz.setAttemptNumber(2);

        retryQuiz.setCompleted(false);
        retryQuiz.setPassed(false);


        // Store new quiz
        quizStore.put(
                retryQuiz.getQuizId(),
                retryQuiz
        );

        return retryQuiz;
    }


    // =========================
    // REMOVE CORRECT ANSWERS
    // =========================

    public QuizResponse getQuizResponse(
            Quiz quiz) {

        List<QuizQuestionResponse> questions =
                quiz.getQuestions()
                        .stream()
                        .map(question -> {

                            QuizQuestionResponse response =
                                    new QuizQuestionResponse();

                            response.setQuestionId(
                                    question.getQuestionId()
                            );

                            response.setQuestion(
                                    question.getQuestion()
                            );

                            response.setOptions(
                                    question.getOptions()
                            );

                            return response;
                        })
                        .toList();


        QuizResponse response =
                new QuizResponse();

        response.setQuizId(
                quiz.getQuizId()
        );

        response.setCategory(
                quiz.getCategory()
        );

        response.setQuestions(
                questions
        );

        return response;
    }
}