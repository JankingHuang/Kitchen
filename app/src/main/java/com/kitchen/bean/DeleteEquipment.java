package com.kitchen.bean;

import java.util.List;

public class DeleteEquipment {
    //存放删除的列表
    private List<listBean> listBeanList;
    class listBean{
        private String UserID;
        private String EquimentType;
        private String EquimentDate;
    }
}
