package edu.nine14.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profession")
public class Profession {
    @Id
    @Column(name = "id", columnDefinition = "int")
    private Integer professionID;

    @Column(name = "name", columnDefinition = "char")
    private Integer professionName;

    public Integer getProfessionID() {
        return professionID;
    }

    public void setProfessionID(Integer professionID) {
        this.professionID = professionID;
    }

    public Integer getProfessionName() {
        return professionName;
    }

    public void setProfessionName(Integer professionName) {
        this.professionName = professionName;
    }
}
