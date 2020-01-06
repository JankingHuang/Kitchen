package com.kitchen.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;

@SuppressLint("ValidFragment")
public class FragmentOne extends Fragment  {


    public FragmentOne(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fragone,viewGroup,false);
        return view;
    }
}
