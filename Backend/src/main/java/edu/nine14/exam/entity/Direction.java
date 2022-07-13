package edu.nine14.exam.entity;

import javax.persistence.*;

@Entity
@Table(name = "direction")
public class Direction {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer directionID;

    @Column(name = "name", columnDefinition = "char")
    private Integer directionName;

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession professionID;

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

    public Profession getProfessionID() {
        return professionID;
    }

    public void setProfessionID(Profession professionID) {
        this.professionID = professionID;
    }
}
