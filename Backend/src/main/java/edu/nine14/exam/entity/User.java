package edu.nine14.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username", columnDefinition = "char")
    private String username;
    @Column(name = "password", columnDefinition = "char")
    private String password;
    @Column(name = "user_type", columnDefinition = "char")
    private String userType;
    @Column(name = "name", columnDefinition = "char")
    private String name;
    @Column(name = "mobile_number", columnDefinition = "char")
    private String mobileNumber;
    @Column(name = "sex", columnDefinition = "char")
    private String sex;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "grade", columnDefinition = "smallint")
    private int grade;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
