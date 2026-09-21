package com.student.questions.service;

import com.student.questions.entity.Question;
import com.student.questions.repo.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    final private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions(){
       return questionRepository.findAll();
    }
    public List<Question> getQuestionsByCategory(String category){
        return questionRepository.findByCategory(category);
    }
    public List<Question> getRandomQuestionsByCategory(String category) {
        return questionRepository.findRandomQuestionsByCategory(category, 10);
    }

}
