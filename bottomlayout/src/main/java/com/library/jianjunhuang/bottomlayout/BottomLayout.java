package com.library.jianjunhuang.bottomlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-1-23.
 */

public class BottomLayout extends LinearLayout {

    private List<TabView> tabViews = new ArrayList<>();
    private ViewPager mViewPager;

    public BottomLayout(Context context) {
        this(context, null);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

    }

    public void addTab(TabView tabView, int position) {
        tabView.setPosition(position);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TabView tv = (TabView) v;
                mViewPager.setCurrentItem(tv.getPosition());
            }
        });
        tabViews.add(tabView);
    }

    public void addTab(TabView tabView) {
        addTab(tabView, tabViews.size());
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    TabView left = tabViews.get(position);
                    TabView right = tabViews.get(position + 1);
                    left.setAlpha(1 - positionOffset);
                    right.setAlpha(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
