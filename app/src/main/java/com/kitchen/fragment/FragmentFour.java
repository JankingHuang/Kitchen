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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.kitchen.activity.R;
import com.kitchen.bean.AddEquipment;
import com.kitchen.bean.DeleteEquipment;
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
import okhttp3.MediaType;
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
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e(TAG, "onItemClick: "+position);
        new AlertDialog.Builder(getContext())
                .setTitle("警告")
                .setMessage("该设备将会被删除！！！！")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:发送deletEquipment
                        // {
                        //            "userID": "062999",
                        //            "equTime": "2018-06-10 24:00:07",
                        //            "equType": "E215"
                        //        }
                        Log.e(TAG, "onClick: "+list.get(position));
                        String queTime = (String) list.get(position).get("equipmentTime");
                        String queType = (String) list.get(position).get("equipmentType");
                        DeleteEquipment deleteEquipment = new DeleteEquipment();
                        deleteEquipment.setEquTime(queTime);
                        deleteEquipment.setEquType(queType);
                        deleteEquipment.setUserID(userID);
                        final String json = globalData.gson.toJson(deleteEquipment);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FragmentFour.this.run("http://121.199.22.121:8080/kit/deleteEqu",json);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();;
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
        final View view = factory.inflate(R.layout.dialog_layout, null);
        final AlertDialog.Builder ad1 = new AlertDialog.Builder(getContext());
        Spinner spinnerEquipmentType = view.findViewById(R.id.spinner_equiment_type);
        Spinner spinnerEquipmentTime = view.findViewById(R.id.spinner_equiment_limit_time);
        final EditText equipmentTime = view.findViewById(R.id.edit_equipment_time);
         final String[] resultType = new String[1];
         final String[] resultTime = new String[1];
        spinnerEquipmentTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resultTime[0] = getActivity().getResources().getStringArray(R.array.array_Equipment_Limint_Time)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEquipmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resultType[0] = getActivity().getResources().getStringArray(R.array.array_Equipment)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ad1.setTitle("连接设备");
        ad1.setIcon(R.drawable.connection);
        ad1.setView(view);
        ad1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                AddEquipment addEquipment = new AddEquipment();
                Log.e(TAG, "onClick: "+resultTime[0] +"/n"+resultType[0]);
                String aux[] = resultTime[0].split(" ");
                int time = Integer.parseInt(aux[0]);
                String[] string = resultType[0].split(" ");
                String type = string[0];
                String name = string[1];
                String resultTime1 = equipmentTime.getText().toString().trim();
                addEquipment.setEquName(string[1]);
                addEquipment.setEquType(string[0]);
                addEquipment.setEquTime(resultTime1);
                addEquipment.setEquYear(time);
                addEquipment.setUserID(globalData.getUserID());
                final String json = globalData.gson.toJson(addEquipment);
                Log.e(TAG, "onClick: "+json);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FragmentFour.this.run("http://121.199.22.121:8080/kit/addEqu",json);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        ad1.show();
    }
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public void run(String url,String json) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, json))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }
    }
}

