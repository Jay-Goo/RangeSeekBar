package com.jaygoo.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jaygoo.demo.fragments.BaseFragment;
import com.jaygoo.demo.fragments.RangeSeekBarFragment;
import com.jaygoo.demo.fragments.SingleSeekBarFragment;
import com.jaygoo.demo.fragments.VerticalSeekBarFragment;
import com.jaygoo.demo.fragments.StepsSeekBarFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String[] types = new String[]{"SINGLE", "RANGE", "STEP","VERTICAL"};

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
        fragments.add(new RangeSeekBarFragment());
        fragments.add(new StepsSeekBarFragment());
        fragments.add(new VerticalSeekBarFragment());

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
