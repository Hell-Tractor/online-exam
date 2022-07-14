package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Question;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface QuestionDirectionRepository extends CrudRepository<Question, Integer> {

}
