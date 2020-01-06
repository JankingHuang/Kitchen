package com.kitchen.fragment.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kitchen.view.CircleProgress;
import com.kitchen.activity.R;

import java.util.Random;

public class SubFragmentFour extends Fragment implements View.OnClickListener{

    private CircleProgress mCircleProgress;
    private Random mRandom;

    public SubFragmentFour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fg_four, viewGroup, false);

        mCircleProgress = (CircleProgress) view.findViewById(R.id.circle_progress_bar);
        mCircleProgress.setOnClickListener(this);
        mRandom = new Random();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle_progress_bar:
                mCircleProgress.setValue(mRandom.nextFloat() * mCircleProgress.getMaxValue());
                break;
        }
    }
}