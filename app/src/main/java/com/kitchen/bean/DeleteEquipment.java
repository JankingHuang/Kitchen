package com.kitchen.bean;

public class DeleteEquipment {

    /**
     * userID : 06241
     * equTime : 2018-11-10 12:00:01
     * equType : E215
     */

    private String userID;
    private String equTime;
    private String equType;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEquTime() {
        return equTime;
    }

    public void setEquTime(String equTime) {
        this.equTime = equTime;
    }

    public String getEquType() {
        return equType;
    }

    public void setEquType(String equType) {
        this.equType = equType;
    }
}
