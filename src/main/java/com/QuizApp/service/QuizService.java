package com.QuizApp.service;

import com.QuizApp.model.Question;
import com.QuizApp.model.QuestionWrapper;
import com.QuizApp.model.Quiz;
import com.QuizApp.model.Response;
import com.QuizApp.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuestionRepo questionRepo;

    public void createQuiz(String category, String title, int num) {

        List<Question> questions = questionRepo.findRandomByCategory(category,num);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        new ResponseEntity<>("Created a quiz successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for(Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestionTitle());
            questionForUser.add(qw);
        }
        return  new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {

        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int score = 0;
        int i=0;
         for (Response response : responses) {
             if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                 score++;

             i++;
         }
         return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
