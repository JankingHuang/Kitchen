package com.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.kitchen.view.PagerAdapter;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BubbleNavigationChangeListener {

    private ViewPager vPager;

    private PagerAdapter myAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;
    private BubbleNavigationLinearView bubbleNavigationConstraintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        myAdapter = new PagerAdapter(getSupportFragmentManager());
        bindViews();
    }

    private void bindViews() {
        vPager = findViewById(R.id.vpager);
        vPager.setAdapter(myAdapter);
        vPager.setCurrentItem(0);
        vPager.setOffscreenPageLimit(7);
        vPager.addOnPageChangeListener(this);
        bubbleNavigationConstraintView = findViewById(R.id.bottom_navigation_view_linear);
        bubbleNavigationConstraintView.setNavigationChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vPager.getCurrentItem()) {
                case PAGE_ONE:
                    bubbleNavigationConstraintView.setCurrentActiveItem(0);
                    break;
                case PAGE_TWO:
                    bubbleNavigationConstraintView.setCurrentActiveItem(1);
                    break;
                case PAGE_THREE:
                    bubbleNavigationConstraintView.setCurrentActiveItem(2);
                    break;
                case PAGE_FOUR:
                    bubbleNavigationConstraintView.setCurrentActiveItem(3);
                    break;
                case PAGE_FIVE:
                    bubbleNavigationConstraintView.setCurrentActiveItem(4);
                    break;
            }
        }
    }

    @Override
    public void onNavigationChanged(View view, int position) {
        switch (position) {
            case 0:
                vPager.setCurrentItem(PAGE_ONE);
                break;
            case 1:
                vPager.setCurrentItem(PAGE_TWO);
                break;
            case 2:
                vPager.setCurrentItem(PAGE_THREE);
                break;
            case 3:
                vPager.setCurrentItem(PAGE_FOUR);
                break;
            case 4:
                vPager.setCurrentItem(PAGE_FIVE);
                break;
        }
    }
}