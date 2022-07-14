package edu.nine14.exam.service;

import edu.nine14.exam.entity.Question;
import edu.nine14.exam.dao.QuestionDirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Optional;

@Service
public class QuestionDirectionService {
    @Autowired
    private QuestionDirectionRepository questionRepository;

    public Object getAllQuestion(){
        return questionRepository.findAll();
    }

    /*public Object findByProfession(Integer professionID) throws FailedLoginException {
        try{
            Optional<Question> question = questionRepository.findById(professionID);

            if(question.isEmpty())
                throw new FailedLoginException("");
        }
    }*/
}
