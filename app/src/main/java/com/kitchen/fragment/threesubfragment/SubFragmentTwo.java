package com.kitchen.fragment.threesubfragment;

import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SubFragmentTwo extends Fragment implements AdapterView.OnItemClickListener {

    private List<String> list;
    private static  String TAG = "SubFragmentTow";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_sub_two, viewGroup, false);
        list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        initView(view);
        Log.e(TAG, "onCreateView: I am SubFragmentTwo");
        return view;
    }

    private void initView(View view) {
        ListView listView = view.findViewById(R.id.fragment_sub_two_listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        Log.e(TAG, "initView: done");
    }

    private void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final SeekBar seekBar = new SeekBar(getContext());
        seekBar.setMax(100);
        seekBar.setKeyProgressIncrement(1);

        builder.setTitle("请设定阈值");
        builder.setView(seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //获取 i
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create();
        builder.show();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "onItemClick: I was cliked");
        showDialog();
    }
}
