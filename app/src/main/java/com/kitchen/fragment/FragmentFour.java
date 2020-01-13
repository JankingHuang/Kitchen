package com.kitchen.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.kitchen.activity.R;
import com.kitchen.bean.GetEquipment;
import com.kitchen.bean.Register;
import com.kitchen.utils.Control;
import com.kitchen.utils.GlobalData;
import com.kitchen.view.FourAdapter;
import com.kitchen.view.ThreeAdapter;
import com.nightonke.jellytogglebutton.State;
import com.scalified.fab.ActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.iwgang.countdownview.CountdownView;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentFour extends Fragment implements AdapterView.OnItemClickListener, Control, View.OnClickListener {

    private static final String TAG = "FragmentFour";
    private ListView listView;
    private List<Map<String, Object>> list;
    private FourAdapter fourAdapter;
    private ActionButton actionButton;
    private View dialogView;
    LayoutInflater inflaterDialog;
    private GlobalData globalData ;
    private String userID;
    private OkHttpClient client = new OkHttpClient();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fragment_four,viewGroup,false);
        inflaterDialog = inflater;
        list = new ArrayList<>();
        globalData = (GlobalData) getContext().getApplicationContext();
        actionButton = view.findViewById(R.id.action_button);
        actionButton.setImageResource(R.drawable.fab_plus_icon);
        actionButton.setButtonColor(Color.parseColor("#1FDF6C"));
        actionButton.setOnClickListener(this);
        dialogView = inflaterDialog.inflate(R.layout.dialog_layout, null);
        initView(view);
        initListView(listView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void runOk() throws Exception {
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
                Map<String, Object> map = new HashMap<>();
                map.put("logo", R.drawable.thermometer256);
                map.put("equipmentType", getEquipment.getData().get(i).getEquType());
                map.put("equipmentName", getEquipment.getData().get(i).getEquName());
                map.put("equipmentLimitTime", getEquipment.getData().get(i).getEquYear());
                map.put("equipmentTime", getEquipment.getData().get(i).getEquTime());
                list.add(map);
            }
        }
        initListView(listView);
    }


    private void initView(View view) {
        listView = view.findViewById(R.id.fragment_four_listview);
    }

    private void initListView(ListView listView) {
        fourAdapter = new FourAdapter(getContext());
        fourAdapter.setList(list);
        listView.setAdapter(fourAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void btnTimePickerDialog(int position, CountdownView countdownView) {

    }

    @Override
    public void btnJellyToggleButton(int position, State state) {

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: Fab");
        showAddDialog();
    }


    protected void showAddDialog() {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(getContext());
        ad1.setTitle("连接设备");
        ad1.setIcon(R.drawable.connection);
        ad1.setView(textEntryView);
        ad1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        ad1.show();
    }
}

