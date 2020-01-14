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
import com.kitchen.fragment.twosubfragment.SubFragmentFive;
import com.kitchen.fragment.twosubfragment.SubFragmentFour;
import com.kitchen.fragment.twosubfragment.SubFragmentOne;
import com.kitchen.fragment.twosubfragment.SubFragmentThree;

import java.util.Objects;

public class FragmentTwo extends Fragment implements BubbleNavigationChangeListener {

    private static final String TAG = "FragmentTwo";
    private BubbleNavigationLinearView bubbleNavigationConstraintView;
    private SubFragmentOne fgOne;
    private SubFragmentThree fgThree;
    private SubFragmentFour fgFour;
    private SubFragmentFive fgFive;
    private FragmentManager fManager;

    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragtwo, viewGroup, false);
        fManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        bubbleNavigationConstraintView = view.findViewById(R.id.bottom_navigation_view_linea);
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
            case R.id.l_home:
                initFgOne(fTransaction);
                break;
            case R.id.yan_wu:
                if (fgThree == null) {
                    fgThree = new SubFragmentThree();
                    fTransaction.add(R.id.ly_content, fgThree);
                } else {
                    fTransaction.show(fgThree);
                }
                break;
            case R.id.l_list:
                if (fgFour == null) {
                    fgFour = new SubFragmentFour();
                    fTransaction.add(R.id.ly_content, fgFour);
                } else {
                    fTransaction.show(fgFour);
                }
                break;
            case R.id.list:
                if (fgFive == null) {
                    fgFive = new SubFragmentFive();
                    fTransaction.add(R.id.ly_content, fgFive);
                } else {
                    fTransaction.show(fgFive);
                }
                break;
        }
        fTransaction.commit();
    }

    private void initFgOne(FragmentTransaction fTransaction) {
        if (fgOne == null) {
            fgOne = new SubFragmentOne();
            fTransaction.add(R.id.ly_content, fgOne);
            Log.i(TAG, "onNavigationChanged: 11222223");
        } else {
            fTransaction.show(fgOne);
            Log.i(TAG, "onNavigationChanged: ");
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fgOne != null) fragmentTransaction.hide(fgOne);
        if (fgThree != null) fragmentTransaction.hide(fgThree);
        if (fgFour != null) fragmentTransaction.hide(fgFour);
        if (fgFive != null) fragmentTransaction.hide(fgFive);
    }
}
