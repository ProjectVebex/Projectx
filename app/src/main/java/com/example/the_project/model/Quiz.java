package com.example.the_project.model;

import java.util.ArrayList;

public class Quiz {
    String name,marks,dateAndTime,quizTime;


    public Quiz(String name, String marks, String dateAndTime, String quizTime) {
        this.name = name;
        this.marks = marks;
        this.dateAndTime = dateAndTime;
        this.quizTime = quizTime;
    }

    public Quiz(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(String quizTime) {
        this.quizTime = quizTime;
    }


    @Override
    public String toString() {
        return "\nQuiz{" +
                "subject='" + name + '\'' +
                ", marks='" + marks + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", quizTime='" + quizTime + '\'' +
                '}';
    }
}
