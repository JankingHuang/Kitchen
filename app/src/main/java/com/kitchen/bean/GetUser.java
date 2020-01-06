package com.kitchen.bean;

import java.util.List;

public class GetUser {

    /**
     * code : SUCCESS
     * msg : null
     * data : [{"userID":"062666","userName":"Jankin","userPwd":"123456","telNum":"15526426270","userPhoto":"http://121.199.22.121:8080/images/20200105200523_062666.jpg","userEmail":"archkernel@outlook.com"}]
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
         * userID : 062666
         * userName : Jankin
         * userPwd : 123456
         * telNum : 15526426270
         * userPhoto : http://121.199.22.121:8080/images/20200105200523_062666.jpg
         * userEmail : archkernel@outlook.com
         */

        private String userID;
        private String userName;
        private String userPwd;
        private String telNum;
        private String userPhoto;
        private String userEmail;

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

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
    }
}
