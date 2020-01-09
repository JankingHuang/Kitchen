package com.kitchen.fragment.subfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.bean.GetTempHum;
import com.kitchen.utils.GlobalData;
import com.kitchen.view.TempControl;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubFragmentOne extends Fragment {

    private static final String TAG = "SubFragmentOne";
    private TempControl tempControl;

    public SubFragmentOne(){}
    private GetTempHum getTempHum;
    private GlobalData globalData;
    private OkHttpClient client = new OkHttpClient();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fg_one,viewGroup,false);
        globalData = (GlobalData) Objects.requireNonNull(getContext()).getApplicationContext();
        tempControl = view.findViewById(R.id.temp_control);
        // 设置几格代表温度1度
        tempControl.setAngleRate(1);
        //设置指针是否可旋转
        tempControl.setCanRotate(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String userID = globalData.getUserID();
        tempControl.setTemp(13);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(userID == null)
                        return ;
                    runGetHum("http://121.199.22.121:8080/kit/getHum?userID="+userID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void runGetHum(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "getTempHum: " + result);
            getTempHum = globalData.gson.fromJson(result, GetTempHum.class);
            tempControl.setTemp((int) getDataBean().getTemp());
            globalData.setHumidity((int) getDataBean().getHum());
        }
    }

    private GetTempHum.DataBean getDataBean() {
        return getTempHum.getData().get(0);
    }
}
