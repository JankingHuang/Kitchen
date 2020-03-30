package com.kitchen.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.kitchen.activity.R;
import com.kitchen.fragment.threesubfragment.SubFragmentTwo;
import com.kitchen.fragment.threesubfragment.SubFragmentOne;

import java.util.Objects;

public class FragmentTwo extends Fragment implements BubbleNavigationChangeListener {

    private static final String TAG = "FragmentTwo";
    private BubbleNavigationLinearView bubbleNavigationConstraintView;
    private FragmentManager fManager;
    private SubFragmentOne fgOne;
    private SubFragmentTwo fgTwo;

    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragthree, viewGroup, false);
        fManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        bubbleNavigationConstraintView = view.findViewById(R.id.bottom_navigation_view_linea1);
        bubbleNavigationConstraintView.setNavigationChangeListener(this);
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        initFgOne(fTransaction);
        fTransaction.commit();
        return view;
    }

    @Override
    public void onNavigationChanged(View view, int position) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()) {
            case R.id.l_home1:
                initFgOne(fTransaction);
                break;
            case R.id.yan_wu1:
                if(fgTwo == null){
                    fgTwo = new SubFragmentTwo();
                    fTransaction.add(R.id.ly_content1,fgTwo);
                }else{
                    fTransaction.show(fgTwo);
                }
                break;
        }
        fTransaction.commit();
    }

    private void initFgOne(FragmentTransaction fTransaction) {
        if (fgOne == null) {
            fgOne = new SubFragmentOne();
            fTransaction.add(R.id.ly_content1, fgOne);
            Log.i(TAG, "onNavigationChanged: null");
        } else {
            fTransaction.show(fgOne);
            Log.i(TAG, "onNavigationChanged: ");
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fgOne != null) fragmentTransaction.hide(fgOne);
        if(fgTwo != null) fragmentTransaction.hide(fgTwo);
    }
}
