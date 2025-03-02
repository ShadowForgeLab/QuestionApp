package com.QuizApp.controller;


import com.QuizApp.model.QuestionWrapper;
import com.QuizApp.model.Response;
import com.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@RequestParam String category, @RequestParam int num, @RequestParam String title) {
        quizService.createQuiz(category,title,num);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") int id) {

       return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submitQuiz/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
