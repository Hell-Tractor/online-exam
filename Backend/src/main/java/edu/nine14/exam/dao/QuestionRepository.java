package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query(value = "select q.questionID,q.direction,q.body,q.answer,q.type from Question q where q.type=?1")
    Object selectQuestionByType(String type);
}
