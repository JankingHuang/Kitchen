package com.kitchen.bean;

import java.util.List;

public class GetMethane {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"metID":1,"met":23.5,"metTime":"2019-12-27 10:23:59"}]
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
         * metID : 1
         * met : 23.5
         * metTime : 2019-12-27 10:23:59
         */

        private int metID;
        private double met;
        private String metTime;

        public int getMetID() {
            return metID;
        }

        public void setMetID(int metID) {
            this.metID = metID;
        }

        public double getMet() {
            return met;
        }

        public void setMet(double met) {
            this.met = met;
        }

        public String getMetTime() {
            return metTime;
        }

        public void setMetTime(String metTime) {
            this.metTime = metTime;
        }
    }
}
