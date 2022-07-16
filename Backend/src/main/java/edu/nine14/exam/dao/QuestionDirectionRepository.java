package edu.nine14.exam.dao;

import edu.nine14.exam.entity.QuestionDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface QuestionDirectionRepository extends JpaRepository<QuestionDirection, Integer>,
        JpaSpecificationExecutor<QuestionDirection> {
    @Query("select q.questionID,q.body,q.answer,q.selection from QuestionDirection q where q.direction.profession.professionID=?1 and q.type=?2")
    List<Object> questionForProfession(Integer directionID,String type);

    @Query("select q.questionID,q.body,q.answer,q.selection from QuestionDirection q where q.direction.profession.professionID=?1 and q.type=?2 and q.direction.directionName IN (?3,?4)")
    List<Object> questionForDirection(Integer directionID,String type,String direction1,String direction2);
}