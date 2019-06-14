package com.jaygoo.demo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String[] types = new String[]{"SINGLE", "RANGE", "STEP","SINGLE-VERTICAL", "RANGE-VERTICAL", "STEP-VERTICAL", "custom"};

    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        fragments.clear();
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new SingleSeekBarFragment());

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.layout_tab);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        for (String s: types){
            tabLayout.newTab().setText(s);
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return types.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return types[position];
        }
    }

}
