package com.kitchen.bean;

public class PostMessage {

    /**
     * userID : 0624
     * equType : E215
     * equState : 1
     * equID : 1
     * hour : 0
     * minute : 12
     */

    private String userID;
    private String equType;
    private int equState;
    private int equID;
    private int hour;
    private int minute;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEquType() {
        return equType;
    }

    public void setEquType(String equType) {
        this.equType = equType;
    }

    public int getEquState() {
        return equState;
    }

    public void setEquState(int equState) {
        this.equState = equState;
    }

    public int getEquID() {
        return equID;
    }

    public void setEquID(int equID) {
        this.equID = equID;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
