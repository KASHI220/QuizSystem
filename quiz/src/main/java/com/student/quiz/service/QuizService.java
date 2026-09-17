package com.student.quiz.service;

import com.student.quiz.client.HistoryClient;
import com.student.quiz.client.QuestionClient;
import com.student.quiz.dto.QuizResult;
import com.student.quiz.model.AnswerRequest;
import com.student.quiz.model.Quiz;
import com.student.quiz.model.QuizQuestion;
import com.student.quiz.model.QuizResponse;
import com.student.quiz.model.QuizQuestionResponse;
import com.student.quiz.model.StartQuizRequest;
import com.student.quiz.model.SubmitQuizRequest;
import com.student.quiz.model.HistoryRequest;

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

    public Quiz startQuiz(StartQuizRequest request) {
        List<QuizQuestion> questions =
                questionClient.getRandomQuestions(request.getCategory());

        Quiz quiz = new Quiz();
        quiz.setQuizId(UUID.randomUUID().toString());
        quiz.setStudentId(request.getStudentId());
        quiz.setCategory(request.getCategory());
        quiz.setQuestions(questions);

        quizStore.put(quiz.getQuizId(), quiz);

        return quiz;
    }

    public QuizResult submitQuiz(
            String quizId,
            SubmitQuizRequest request) {

        Quiz quiz = quizStore.get(quizId);

        if (quiz == null) {
            throw new RuntimeException("Quiz not found");
        }

        int correct = 0;

        for (AnswerRequest submittedAnswer : request.getAnswers()) {

            for (QuizQuestion question : quiz.getQuestions()) {

                if (question.getQuestionId()
                        .equals(submittedAnswer.getQuestionId())) {

                    if (question.getCorrectAnswer()
                            .equalsIgnoreCase(submittedAnswer.getAnswer())) {

                        correct++;
                    }

                    break;
                }
            }
        }

        int total = quiz.getQuestions().size();
        int wrong = total - correct;

        boolean passed = correct >= total / 2.0;

        // Create quiz result
        QuizResult result = new QuizResult();

        result.setQuizId(quizId);
        result.setTotalQuestions(total);
        result.setCorrectAnswers(correct);
        result.setWrongAnswers(wrong);
        result.setScore(correct);
        result.setPassed(passed);

        // Create history request
        HistoryRequest history = new HistoryRequest();

        history.setStudentId(quiz.getStudentId());
        history.setQuizId(quiz.getQuizId());
        history.setCategory(quiz.getCategory());
        history.setTotalQuestions(total);
        history.setCorrectAnswers(correct);
        history.setWrongAnswers(wrong);
        history.setScore(correct);
        history.setPassed(passed);
        history.setAttemptNumber(1);

        // Send history to History Service
        historyClient.saveHistory(history);

        return result;
    }

    public QuizResponse getQuizResponse(Quiz quiz) {

        List<QuizQuestionResponse> questions = quiz.getQuestions()
                .stream()
                .map(question -> {
                    QuizQuestionResponse response = new QuizQuestionResponse();

                    response.setQuestionId(question.getQuestionId());
                    response.setQuestion(question.getQuestion());
                    response.setOptions(question.getOptions());

                    return response;
                })
                .toList();

        QuizResponse response = new QuizResponse();
        response.setQuizId(quiz.getQuizId());
        response.setCategory(quiz.getCategory());
        response.setQuestions(questions);

        return response;
    }
}