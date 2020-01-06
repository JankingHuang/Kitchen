package com.kitchen.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.view.FourAdapter;
import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

@SuppressLint("ValidFragment")
public class FragmentOne extends Fragment implements CardStackView.ItemExpendListener {

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };

    private CardStackView mStackView;
    private FourAdapter mFourAdapter;
    private LinearLayout mActionButtonContainer;
    public FragmentOne(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view = inflater.inflate(R.layout.fragone,viewGroup,false);

        mStackView = view.findViewById(R.id.stackview_main);
        mActionButtonContainer = view.findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mFourAdapter = new FourAdapter(getContext());
//        mFourAdapter = new FourAdapter(this);
        mStackView.setAdapter(mFourAdapter);
//        mFourAdapter.updateData(Arrays.asList(TEST_DATAS));

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFourAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );

        return view;
    }


    @Override
    public void onItemExpend(boolean expend) {
        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
        Log.i("TAG", "onItemExpend: ");
    }
}
