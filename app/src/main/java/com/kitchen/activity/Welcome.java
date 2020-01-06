package com.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kitchen.utils.Permissions;

public class Welcome extends AppCompatActivity {

    private Permissions permissions;
    protected static final Gson gson = new GsonBuilder().create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catoon);
        permissions = new Permissions(this);
        permissions.requestPermissions();
        if(!permissions.isAllPermission()){
            Toast.makeText(this, "没有权限，程序无法使用！！！", Toast.LENGTH_LONG).show();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        final LinearLayout tv_lin = (LinearLayout) findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin = (LinearLayout) findViewById(R.id.text_hide_lin);//所谓的布
        ImageView logo = (ImageView) findViewById(R.id.image);//图片
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        logo.startAnimation(animation);//开始执行动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一个动画执行完后执行第二个动画就是那个字体显示那部分
                animation = AnimationUtils.loadAnimation(Welcome.this, R.anim.text_splash_position);
                tv_lin.startAnimation(animation);
                animation = AnimationUtils.loadAnimation(Welcome.this, R.anim.text_canvas);
                tv_hide_lin.startAnimation(animation);

                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
