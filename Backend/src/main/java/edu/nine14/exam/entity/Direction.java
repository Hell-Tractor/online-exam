package edu.nine14.exam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "direction")
public class Direction {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer directionID;

    @Column(name = "name", columnDefinition = "char")
    private String directionName;

    @JoinColumn
    @ManyToOne(cascade=CascadeType.ALL)
    private Profession profession;

    public Integer getDirectionID() {
        return directionID;
    }

    public void setDirectionID(Integer directionID) {
        this.directionID = directionID;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
