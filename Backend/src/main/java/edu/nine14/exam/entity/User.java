package edu.nine14.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Map;

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
    public void setBirthday(String birthday) {
        this.birthday = Date.valueOf(birthday);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    public void setGrade(String grade) {
        this.grade = Integer.parseInt(grade);
    }

    public Map<String, String> getUserInformation() {
        Map<String, String> userInformation = new java.util.HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("password")) {
                continue;
            }
            try {
                Method method = this.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                userInformation.put(field.getName(), method.invoke(this).toString());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return userInformation;
    }

    public void updateUserInformation(Map<String, String> information) {
        for (Map.Entry<String, String> entry : information.entrySet()) {
            if (entry.getKey().equals("password") || entry.getKey().equals("username") || entry.getKey().equals("userType")) {
                continue;
            }
            try {
                Method setMethod = this.getClass().getMethod("set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1), String.class);
                setMethod.invoke(this, entry.getValue());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Key not found: " + entry.getKey());
            }
        }
    }
}
