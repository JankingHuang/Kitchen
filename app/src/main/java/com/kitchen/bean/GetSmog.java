package com.kitchen.bean;

import java.util.List;

public class GetSmog {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"smogID":1,"smog":32.5,"smogTime":"2019-12-27 10:23:31"}]
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
         * smogID : 1
         * smog : 32.5
         * smogTime : 2019-12-27 10:23:31
         */

        private int smogID;
        private double smog;
        private String smogTime;

        public int getSmogID() {
            return smogID;
        }

        public void setSmogID(int smogID) {
            this.smogID = smogID;
        }

        public double getSmog() {
            return smog;
        }

        public void setSmog(double smog) {
            this.smog = smog;
        }

        public String getSmogTime() {
            return smogTime;
        }

        public void setSmogTime(String smogTime) {
            this.smogTime = smogTime;
        }
    }
}
