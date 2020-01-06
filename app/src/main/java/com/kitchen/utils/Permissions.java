package com.kitchen.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    private Context context;
    private boolean isAllPermission = false;

    public boolean isAllPermission() {
        return isAllPermission;
    }

    List<String> permissionList;
    public Permissions(Context context) {
        this.context = context;
         permissionList = new ArrayList<>();
    }

    public void requestPermissions(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.INTERNET);
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }


        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(context, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
            isAllPermission = true;
        }

    }



}
