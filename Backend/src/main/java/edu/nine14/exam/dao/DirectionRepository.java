package edu.nine14.exam.dao;

import edu.nine14.exam.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    @Query(value = "select d from Direction d where d.profession.professionID=?1")
    List<Direction> selectDirectionByProfessionId(Integer professionId);
}
