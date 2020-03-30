package com.kitchen.fragment.threesubfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.bean.GetEquipment;
import com.kitchen.utils.GlobalData;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubFragmentTwo extends Fragment implements AdapterView.OnItemClickListener {

    private List<String> list;
    private static  String TAG = "SubFragmentTow";
    private GlobalData globalData ;
    private OkHttpClient client = new OkHttpClient();
    private String userID;
    private View view;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        view = inflater.inflate(R.layout.fragment_sub_two, viewGroup, false);
        globalData = (GlobalData) getContext().getApplicationContext();
        list = new ArrayList<>();
        Log.e(TAG, "onCreateView: I am SubFragmentTwo");
        initView();
        initListView();
//        getData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Log.e(TAG, "onResume: I appear ");
        userID = globalData.getUserID();
        Log.e(TAG, "onResume: "+userID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(userID == null)
                        return;
                    runOk();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        listView = view.findViewById(R.id.fragment_sub_two_listview);
        arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1, list);

    }

    private void initListView() {
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        Log.e(TAG, "initView: done");
    }

    private void showDialog(){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.customize_dialog_layout,null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        final Dialog dialog = builder.create();
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //把阈值发送给服务器
            }
        });
        ((IndicatorSeekBar)view.findViewById(R.id.seekbar)).setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                Log.e(TAG, "onStartTrackingTouch: "+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                Log.e(TAG, "onStopTrackingTouch: "+seekBar.getProgress());
                //获取阈值
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "onItemClick: I was cliked");
        showDialog();
    }
    public void runOk() throws Exception {
        list.clear();
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID", userID)
                .build();

        Request request = new Request.Builder()
                .url("http://121.199.22.121:8080/kit/getEqu?")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "runOk: "+result);
            GetEquipment getEquipment = globalData.gson.fromJson(result, GetEquipment.class);
            for(int i = 0;i<getEquipment.getData().size();i++) {
                String  equipmentInfo = "";
                equipmentInfo += getEquipment.getData().get(i).getEquType();
                equipmentInfo += " ";
                equipmentInfo += getEquipment.getData().get(i).getEquName();
                equipmentInfo += "\n";
                equipmentInfo += getEquipment.getData().get(i).getEquYear();
                equipmentInfo += " ";
                equipmentInfo += getEquipment.getData().get(i).getEquTime();
                list.add(equipmentInfo);
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

}
