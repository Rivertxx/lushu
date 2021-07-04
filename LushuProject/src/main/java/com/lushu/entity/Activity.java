package com.lushu.entity;

public class Activity {
    private String activityID;
    private String activityName;
    private String routeID;
    private String date;
    private int participantsNumber;
    private String introduction;
    private String host;

    public String getActivityID() {
        return activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getRouteID() {
        return routeID;
    }

    public String getDate() {
        return date;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getHost() {
        return host;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
