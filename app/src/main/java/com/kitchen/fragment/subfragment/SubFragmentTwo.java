package com.kitchen.fragment.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.utils.GlobalData;
import com.kitchen.view.CircleProgress;
import com.kitchen.activity.R;

import java.util.Random;

public class SubFragmentTwo extends Fragment{

    private CircleProgress mCircleProgress;
    private GlobalData globalData;
    public SubFragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fg_two, viewGroup, false);
        mCircleProgress = (CircleProgress) view.findViewById(R.id.circle_progress_bar);
        globalData = (GlobalData) getContext().getApplicationContext();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCircleProgress.setValue(globalData.getHumidity());
    }
}
