package com.kitchen.fragment.subfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.bean.GetMethane;
import com.kitchen.bean.GetSmog;
import com.kitchen.utils.GlobalData;
import com.kitchen.view.CircleProgress;
import com.kitchen.activity.R;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubFragmentFour extends Fragment{

    private static final String TAG = "SubFragmentFour";
    private CircleProgress circleProgress;
    private GetMethane getMethane;
    private OkHttpClient client = new OkHttpClient();
    private GlobalData globalData;
    public SubFragmentFour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fg_four, viewGroup, false);
        globalData = (GlobalData) getContext().getApplicationContext();
        circleProgress = (CircleProgress) view.findViewById(R.id.circle_progress_bar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userID = globalData.getUserID();
                    if(userID == null)
                        return ;
                    runGetSmog("http://121.199.22.121:8080/kit/getMet?userID="+userID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void runGetSmog(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "Respone:" + result);
            getMethane = globalData.gson.fromJson(result, GetMethane.class);
            if(getMethane.getData() == null)
                return;
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    circleProgress.setValue((float) getMethane.getData().get(0).getMet());
                }
            });
        }
    }
}