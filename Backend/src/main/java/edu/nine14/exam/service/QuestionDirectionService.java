package edu.nine14.exam.service;

import edu.nine14.exam.dao.QuestionDirectionRepository;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.entity.QuestionDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionDirectionService {
    @Autowired
    private QuestionDirectionRepository questionDirectionRepository;

    public Object getAllQuestion(){
        return questionDirectionRepository.findAll();
    }

    public Object selectById(Integer questionDirectionID){
        Optional<QuestionDirection> questionDirection = questionDirectionRepository.findById(questionDirectionID);
        if (questionDirection.isEmpty())
            throw new IllegalArgumentException("Question not found");
        return questionDirection.get();
    }

    public Object questionForProfession(Integer professionID,String type){
        return questionDirectionRepository.questionForProfession(professionID,type);
    }

    public Object questionForDirection(Integer directionID,String type,String direction1,String direction2){
        return questionDirectionRepository.questionForDirection(directionID,type,direction1,direction2);
    }

    /**
     * 按照所需要的条件进行查找
     * @param page 页数，默认第0页
     * @param size 每页显示的条数
     * @param direction 专业方向
     * @param id 问题id
     * @param profession 专业
     * @param type 题目类型
     * @return 返回符合条件的所有题目
     */
    public Page<QuestionDirection> findByCondition(Integer page, Integer size, Integer direction,
                                                   Integer profession,Integer id, String type){
        Pageable pageable = PageRequest.of(page, size);
        return questionDirectionRepository.findAll((root, criteriaQuery, criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(direction!=null){
                predicates.add(criteriaBuilder.equal(root.get("direction").get("directionID"),direction));
            }
            if(id!=null){
                predicates.add(criteriaBuilder.equal(root.get("questionID"),id));
            }
            if(profession!=null){
                predicates.add(criteriaBuilder.equal(root.get("direction").get("profession").get("professionID"),profession));
            }
            if(type!=null){
                predicates.add(criteriaBuilder.equal(root.get("type"),type));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

}