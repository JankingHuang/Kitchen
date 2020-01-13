package com.kitchen.fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.utils.Control;
import com.kitchen.view.ThreeAdapter;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;
import com.scalified.fab.ActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;


public class FragmentThree extends Fragment implements AdapterView.OnItemClickListener,  Control {


    private static final String TAG = "SubFragmentThree";

    private JellyToggleButton jellyToggleButton;
    private ListView listView;


    private List<Map<String, Object>> list;
    private ThreeAdapter threeAdapter;
    private CountdownView countdownView;
    final long[] milliSecond = new long[1];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_three, viewGroup, false);

        initView(view);
        initListView(listView);
        return view;
    }

    private void initView(View view) {
        listView = view.findViewById(R.id.fragment_three_listview);

        jellyToggleButton = view.findViewById(R.id.jtb_21);
    }

    private void initListView(ListView listView) {
        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("logo", R.drawable.thermometer256);
        map.put("title", "温度");
        map.put("note", "设备号：257");
        map.put("state", true);
        list.add(map);

        map = new HashMap<>();//新建一个避免覆盖
        map.put("logo", R.drawable.humidity256);
        map.put("title", "湿度");
        map.put("note", "设备号：255");
        map.put("state", false);
        list.add(map);

        map = new HashMap<>();
        map.put("logo", R.drawable.smoke256);
        map.put("title", "烟雾浓度");
        map.put("note", "设备号：256");
        map.put("state", false);
        list.add(map);

        threeAdapter = new ThreeAdapter(getContext());
        threeAdapter.setList(list);
        threeAdapter.setControl(this);
        listView.setAdapter(threeAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: ");
    }



    @Override
    public void btnTimePickerDialog(int position, final CountdownView countdownView) {
        Calendar c1 = Calendar.getInstance();//获取系统时间
        new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                        String text = "选择了：" + hourofDay + "时" + minute + "分";
                        milliSecond[0] = (hourofDay * 60 + minute) * 60 * 1000;
                        if(milliSecond[0] == 0){
                            milliSecond[0] = 1;
                        }
                        countdownView.start(milliSecond[0]);
                        Log.i(TAG, "onTimeSet: " + text + "milliSecond---->" + milliSecond[0]);
                    }
                },
                c1.get(Calendar.HOUR_OF_DAY),
                c1.get(Calendar.MINUTE),
                true).show();
    }


    @Override
    public void btnJellyToggleButton(int position, State state) {
        if (state.equals(State.LEFT)) {
            Log.i(TAG, "State.LEFT: " + position);
            list.get(position).put("state", false);
        }
        if (state.equals(State.RIGHT)) {
            Log.i(TAG, "State.RIGHT: " + position);
            list.get(position).put("state", true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }
}