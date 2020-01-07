package com.kitchen.bean;

import java.util.List;

public class GetTempHum {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"humID":1,"temp":32.6,"humTime":"2019-11-18 20:33:31","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 20:33:30","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 20:33:29","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 20:33:28","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 20:33:27","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:37:02","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:37:01","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:37:00","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:59","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:58","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:57","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:56","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:55","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:54","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:53","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:52","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:51","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:50","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:49","hum":25.6},{"humID":1,"temp":32.6,"humTime":"2019-11-18 08:36:48","hum":25.6}]
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
         * humID : 1
         * temp : 32.6
         * humTime : 2019-11-18 20:33:31
         * hum : 25.6
         */

        private int humID;
        private double temp;
        private String humTime;
        private double hum;

        public int getHumID() {
            return humID;
        }

        public void setHumID(int humID) {
            this.humID = humID;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public String getHumTime() {
            return humTime;
        }

        public void setHumTime(String humTime) {
            this.humTime = humTime;
        }

        public double getHum() {
            return hum;
        }

        public void setHum(double hum) {
            this.hum = hum;
        }
    }
}
