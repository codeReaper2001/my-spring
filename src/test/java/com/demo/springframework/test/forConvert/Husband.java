package com.demo.springframework.test.forConvert;

import java.time.LocalDate;

public class Husband {

    private String wifeName;

    private Integer age;

    private LocalDate marriageData;

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public String getWifeName() {
        return wifeName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getMarriageData() {
        return marriageData;
    }

    public void setMarriageData(LocalDate marriageData) {
        this.marriageData = marriageData;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifeName='" + wifeName + '\'' +
                ", age=" + age +
                ", marriageData=" + marriageData +
                '}';
    }
}
