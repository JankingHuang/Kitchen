package com.kitchen.bean;

import java.util.List;

public class GetSafeInfo {

    /**
     * code : SUCCESS
     * msg : 获取安全操作信息成功!
     * data : [{"safeName":"通风安全操作","safeContent":"打开通风","exHandle":"关闭通风","safePhoto":"http://121.199.22.121:8080/images/20191201092511_equ1.png"},{"safeName":"温控安全操作","safeContent":"打开温控","exHandle":"关闭温控","safePhoto":"http://121.199.22.121:8080/images/20191201092531_equ.png"}]
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
         * safeName : 通风安全操作
         * safeContent : 打开通风
         * exHandle : 关闭通风
         * safePhoto : http://121.199.22.121:8080/images/20191201092511_equ1.png
         */

        private String safeName;
        private String safeContent;
        private String exHandle;
        private String safePhoto;

        public String getSafeName() {
            return safeName;
        }

        public void setSafeName(String safeName) {
            this.safeName = safeName;
        }

        public String getSafeContent() {
            return safeContent;
        }

        public void setSafeContent(String safeContent) {
            this.safeContent = safeContent;
        }

        public String getExHandle() {
            return exHandle;
        }

        public void setExHandle(String exHandle) {
            this.exHandle = exHandle;
        }

        public String getSafePhoto() {
            return safePhoto;
        }

        public void setSafePhoto(String safePhoto) {
            this.safePhoto = safePhoto;
        }
    }
}
