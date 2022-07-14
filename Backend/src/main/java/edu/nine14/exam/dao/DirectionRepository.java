package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DirectionRepository extends CrudRepository<Direction, Integer> {

}
