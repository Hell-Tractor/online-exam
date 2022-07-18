package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.dao.DirectionSingleRepository;
import edu.nine14.exam.entity.Direction;
import edu.nine14.exam.dao.DirectionRepository;
import edu.nine14.exam.entity.DirectionSingle;
import edu.nine14.exam.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.event.ObjectChangeListener;
import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DirectionService {
    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private DirectionSingleRepository directionSingleRepository;

    public Object getAllDirection(){
        return directionRepository.findAll();
    }

    public void editDirection(Direction direction){
        try {
            directionSingleRepository.editDirection(direction.getDirectionName(),direction.getProfession().getProfessionID()
                    ,direction.getDirectionID());
        }catch (Exception e){
            throw e;
        }
    }

    public Object selectById(Integer directionID){
        Optional<DirectionSingle> direction = directionSingleRepository.findById(directionID);
        if (direction.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return direction.get();
    }

    public Object selectByProfessionId(Integer professionId){
        List<DirectionSingle> direction = directionSingleRepository.selectDirectionByProfessionId(professionId);
        if (direction.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return direction;
    }

    public void deleteQuestion(Integer directionID){
        Optional<DirectionSingle> direction = directionSingleRepository.findById(directionID);
        if (direction.isEmpty())
            throw new IllegalArgumentException("Question not found");
        directionSingleRepository.delete(direction.get());
    }
}
