package com.kitchen.fragment.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.view.TempControl;

public class SubFragmentOne extends Fragment {

    public SubFragmentOne(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fg_one,viewGroup,false);

        final TempControl tempControl = view.findViewById(R.id.temp_control);
        // 设置几格代表温度1度
        tempControl.setAngleRate(1);
        //设置温度
        tempControl.setTemp(13);

        //设置指针是否可旋转
        tempControl.setCanRotate(true);

//        //温度改变监听
//        tempControl.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
//            @Override
//            public void change(int temp) {
//                    Toast.makeText(getActivity(), temp + "°", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }
}
