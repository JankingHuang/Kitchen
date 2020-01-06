package com.kitchen.utils;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalData extends Application {
    private String userID;
    public  Gson gson = new GsonBuilder().create();
    private  boolean isLogin = false;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
