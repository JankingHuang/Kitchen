package com.kitchen.view;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kitchen.activity.MainActivity;
import com.kitchen.fragment.FragmentFour;
import com.kitchen.fragment.FragmentThree;
import com.kitchen.fragment.FragmentOne;
import com.kitchen.fragment.FragmentTwo;

public class PagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private FragmentOne fragmentOne = null;
    private FragmentTwo fragmentTwo = null;
    private FragmentThree fragmentThree = null;
    private FragmentFour fragmentFour = null;
//    private FragmentFive fragmentFive = null;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentFour = new FragmentFour();
//        fragmentFive = new FragmentFive();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = fragmentOne;
                break;
            case MainActivity.PAGE_TWO:
                fragment = fragmentTwo;
                break;
            case MainActivity.PAGE_THREE:
                fragment = fragmentThree;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = fragmentFour;
                break;
        }
        return fragment;
    }
}
