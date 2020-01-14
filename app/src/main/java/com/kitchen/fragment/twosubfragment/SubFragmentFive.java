package com.kitchen.fragment.twosubfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.bean.GetLight;
import com.kitchen.utils.GlobalData;
import com.kitchen.view.TempControl;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubFragmentFive extends Fragment {

    private static final String TAG = "SubFragmentFive";
    private TempControl lightControl;

    public SubFragmentFive(){}
    private GlobalData globalData;
    private OkHttpClient client = new OkHttpClient();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fg_five,viewGroup,false);
        globalData = (GlobalData) Objects.requireNonNull(getContext()).getApplicationContext();
        lightControl = view.findViewById(R.id.light_control);
        // 设置几格代表温度1度
        lightControl.setAngleRate(1);
        //设置指针是否可旋转
        lightControl.setCanRotate(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String userID = globalData.getUserID();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(userID == null)
                        return ;
                    runGetHum("http://121.199.22.121:8080/kit/getLight?userID="+userID);
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
            Log.e(TAG, "Response: " + result);
            GetLight getLight = globalData.gson.fromJson(result, GetLight.class);
            if(getLight.getData() == null | getLight.getData().size() == 0)
                return;
            lightControl.setTemp((int) getLight.getData().get(0).getLight());
        }
    }
}
