package com.QuizApp.service;

import com.QuizApp.model.Question;
import com.QuizApp.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public List<Question> getByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        questionRepo.save(question);
        return question;
    }

    public Optional<Question> getById(int id) {
        return questionRepo.findById(id);
    }

    public void deleteQuestion(int id) {
        questionRepo.deleteById(id);
    }
}
