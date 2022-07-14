package edu.nine14.exam.service;

import edu.nine14.exam.dao.QuestionDirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDirectionService {
    @Autowired
    private QuestionDirectionRepository questionDirectionRepository;

    public Object getAllQuestion(){
        return questionDirectionRepository.findAll();
    }

    public Object questionForProfession(Integer professionID,String type){
        return questionDirectionRepository.questionForProfession(professionID,type);
    }

    public Object questionForDirection(Integer directionID,String type,String direction1,String direction2){
        return questionDirectionRepository.questionForDirection(directionID,type,direction1,direction2);
    }
}
