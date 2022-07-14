package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.dao.QuestionRepository;
import edu.nine14.exam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Object getAllQuestion(){
        return questionRepository.findAll();
    }

    public Object selectById(Integer questionID){
        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return question.get();
    }

    public Object selectByType(String type){
        Object question = questionRepository.selectQuestionByType(type);
        if (question.equals(null))//感觉有点问题
            throw new IllegalArgumentException("Question not found");
        return question;
    }

    public void saveQuestion(){

    }

    public void updateQuestion(Integer questionID, String selection){
        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        else{
            question.get().setSelection(selection);
            questionRepository.save(question.get());
        }
    }

    public void deleteQuestion(Integer questionID){
        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty())
            throw new IllegalArgumentException("Question not found");
        questionRepository.delete(question.get());
    }

    public void addQuestion(Question question){
        try {
            questionRepository.save(question);
        }catch (Exception e){
            throw e;
        }

    }
}
