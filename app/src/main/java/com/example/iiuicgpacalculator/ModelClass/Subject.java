package com.example.iiuicgpacalculator.ModelClass;

public class Subject {
    String grade="A";
    String gradeIndex="0";
    String hrsIndex="0";
    String hrs="1";
    String name="";

    double resMultiply=1;

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public String getHrs() {
        return hrs;
    }

    public String getName() {
        return name;
    }

    public double getResMultiply() {
        return resMultiply;
    }

    public void setResMultiply(double resMultiply) {
        this.resMultiply = resMultiply;
    }

    public String getGradeIndex() {
        return gradeIndex;
    }

    public void setGradeIndex(String gradeIndex) {
        this.gradeIndex = gradeIndex;
    }

    public String getHrsIndex() {
        return hrsIndex;
    }


    public void setHrsIndex(String hrsIndex) {
        this.hrsIndex = hrsIndex;
    }
}
