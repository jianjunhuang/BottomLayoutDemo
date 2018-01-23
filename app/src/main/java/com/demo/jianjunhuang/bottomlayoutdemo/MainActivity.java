package com.demo.jianjunhuang.bottomlayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.library.jianjunhuang.bottomlayout.BottomLayout;
import com.library.jianjunhuang.bottomlayout.TabView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomLayout bottomLayout;

    private TabView schedule;
    private TabView earlyUp;
    private TabView tools;
    private TabView mine;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        bottomLayout = findViewById(R.id.bottomlayout);

        schedule = findViewById(R.id.schedule);
        earlyUp = findViewById(R.id.early_up);
        tools = findViewById(R.id.tools);
        mine = findViewById(R.id.mine);


        bottomLayout.addTab(schedule);
        schedule.setAlpha(1);

        bottomLayout.addTab(earlyUp);
        bottomLayout.addTab(tools);
        bottomLayout.addTab(mine);

        fragments.add(new SchuduleFragment());
        fragments.add(new EarlyUpFragment());
        fragments.add(new ToolsFragment());
        fragments.add(new MineFragment());

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        bottomLayout.setUpWithViewPager(viewPager);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
