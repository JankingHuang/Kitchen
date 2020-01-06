package com.kitchen.bean;

public class PostEquipment {

    /**添加设备
     * equName : 温度传感器设备
     * equType : E215
     * equTime : 2019-11-15
     * equYear : 10
     * userID : 0624
     */

    private String equName;
    private String equType;
    private String equTime;
    private int equYear;
    private String userID;

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public String getEquType() {
        return equType;
    }

    public void setEquType(String equType) {
        this.equType = equType;
    }

    public String getEquTime() {
        return equTime;
    }

    public void setEquTime(String equTime) {
        this.equTime = equTime;
    }

    public int getEquYear() {
        return equYear;
    }

    public void setEquYear(int equYear) {
        this.equYear = equYear;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
