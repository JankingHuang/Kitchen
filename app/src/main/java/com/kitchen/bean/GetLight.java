package com.kitchen.bean;

import java.util.List;

public class GetLight {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"lightID":1,"light":12.5,"smogTime":null}]
     */

    private String code;
    private Object msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * lightID : 1
         * light : 12.5
         * smogTime : null
         */

        private int lightID;
        private double light;
        private Object smogTime;

        public int getLightID() {
            return lightID;
        }

        public void setLightID(int lightID) {
            this.lightID = lightID;
        }

        public double getLight() {
            return light;
        }

        public void setLight(double light) {
            this.light = light;
        }

        public Object getSmogTime() {
            return smogTime;
        }

        public void setSmogTime(Object smogTime) {
            this.smogTime = smogTime;
        }
    }
}
