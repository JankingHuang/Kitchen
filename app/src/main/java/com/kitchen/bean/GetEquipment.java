package com.kitchen.bean;

import java.util.List;

public class GetEquipment {

    /**
     * code : SUCCESS
     * msg : 获取智能设备信息成功!
     * data : [{"equName":"传感器温湿度设备","equType":"E215","equTime":"2018-11-10","equYear":10,"userID":"0624"},{"equName":"传感器烟雾设备","equType":"E216","equTime":"2018-11-10","equYear":10,"userID":"0624"},{"equName":"传感器甲烷设备","equType":"E217","equTime":"2018-12-11","equYear":10,"userID":"0624"},{"equName":"传感器温湿度设备","equType":"E215","equTime":"2018-12-12","equYear":10,"userID":"0624"},{"equName":"传感器光敏设备","equType":"E218","equTime":"2019-01-26","equYear":10,"userID":"0624"},{"equName":"传感器烟雾设备","equType":"E216","equTime":"2019-02-10","equYear":10,"userID":"0624"},{"equName":"传感器甲烷设备","equType":"E217","equTime":"2019-02-11","equYear":10,"userID":"0624"},{"equName":"照明设备","equType":"E219","equTime":"2019-08-25","equYear":4,"userID":"0624"},{"equName":"通风设备","equType":"E220","equTime":"2019-08-25","equYear":4,"userID":"0624"},{"equName":"温控设备","equType":"E221","equTime":"2019-09-15","equYear":4,"userID":"0624"},{"equName":"温湿度设备","equType":"E215","equTime":"2019-10-25","equYear":4,"userID":"0624"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
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
         * equName : 传感器温湿度设备
         * equType : E215
         * equTime : 2018-11-10
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
}
