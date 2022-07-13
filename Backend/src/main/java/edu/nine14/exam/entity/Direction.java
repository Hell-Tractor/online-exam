package edu.nine14.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "direction")
public class Direction {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer directionID;

    @Column(name = "name", columnDefinition = "char")
    private Integer directionName;

    @Column(name = "profession_id", columnDefinition = "int")
    private Integer professionID;

    public Integer getDirectionID() {
        return directionID;
    }

    public void setDirectionID(Integer directionID) {
        this.directionID = directionID;
    }

    public Integer getDirectionName() {
        return directionName;
    }

    public void setDirectionName(Integer directionName) {
        this.directionName = directionName;
    }

    public Integer getProfessionID() {
        return professionID;
    }

    public void setProfessionID(Integer professionID) {
        this.professionID = professionID;
    }
}
