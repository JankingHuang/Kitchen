package com.kitchen.bean;

import java.util.List;

public class GetUser {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"userID":"0624","userName":"张三","userPwd":"123412","telNum":"1234567812","userPhoto":"http://121.199.22.121/images/user.png"}]
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
         * userID : 0624
         * userName : 张三
         * userPwd : 123412
         * telNum : 1234567812
         * userPhoto : http://121.199.22.121/images/user.png
         */

        private String userID;
        private String userName;
        private String userPwd;
        private String telNum;
        private String userPhoto;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public String getTelNum() {
            return telNum;
        }

        public void setTelNum(String telNum) {
            this.telNum = telNum;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }
    }
}
