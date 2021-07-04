package com.lushu.entity;

public class Participant {
    private String activityID;
    private String studentID;
    private String studentName;
    private String studentSex;
    private int studentAge;
    private String studentPhone;
    private String campus;

    public String getActivityID() {
        return activityID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public String getCampus() {
        return campus;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}
