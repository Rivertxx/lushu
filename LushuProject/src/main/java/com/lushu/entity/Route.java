package com.lushu.entity;

public class Route {
    private String routeID;
    private String routeName;
    private String start;
    private String destination;
    private double mileage;
    private double cumulativeClimb;
    private String roadCondition;
    private String author;

    public String getRouteID() {
        return routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public double getMileage() {
        return mileage;
    }

    public double getCumulativeClimb() {
        return cumulativeClimb;
    }

    public String getRoadCondition() {
        return roadCondition;
    }

    public String getAuthor() {
        return author;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setCumulativeClimb(double cumulativeClimb) {
        this.cumulativeClimb = cumulativeClimb;
    }

    public void setRoadCondition(String roadCondition) {
        this.roadCondition = roadCondition;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
