package com.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CrashInforActivity extends AppCompatActivity {


    private TextView textViewCrashInfor;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_information);
        initView();
        Listener();
        final Intent intent = getIntent();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewCrashInfor.setText(intent.getStringExtra("crashInformation"));
            }
        });
        finish();
    }

    private void Listener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出程序
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                finish();
            }
        });
    }

    private void initView() {
        textViewCrashInfor = (TextView) findViewById(R.id.textView_crash_infor);
        btnOk = (Button) findViewById(R.id.btn_ok);
    }

}
