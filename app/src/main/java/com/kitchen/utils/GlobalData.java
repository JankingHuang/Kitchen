package com.kitchen.utils;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalData extends Application {
    private String userID;
    public  Gson gson = new GsonBuilder().create();
    private  boolean isLogin = false;
    private  int humidity;




    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

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
