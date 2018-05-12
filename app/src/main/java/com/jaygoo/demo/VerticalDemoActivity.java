package com.jaygoo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jaygoo.widget.RangeSeekBar;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2018/5/11
 * 描    述:
 * ================================================
 */
public class VerticalDemoActivity extends AppCompatActivity {

    private RangeSeekBar seekbar1,seekbar2,seekbar3;
    private String[] levelArrays = {"初级码农","中级码农","高级码农", "CTO","卒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_demo);

        initView();
        seekbar1.setValue(-0.5f,0.8f);
        seekbar1.setIndicatorTextDecimalFormat("0.00");

        seekbar2.setIndicatorTextDecimalFormat("0");
        seekbar2.setIndicatorTextStringFormat("%s%%");
    }

    private void initView(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.layout_action_bar);
            ((Button)getSupportActionBar().getCustomView()).setText("Click Me To Show RangeSeekbar");
            getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        seekbar1 = (RangeSeekBar)findViewById(R.id.seekbar1);
        seekbar2 = (RangeSeekBar)findViewById(R.id.seekbar2);
        seekbar3 = (RangeSeekBar)findViewById(R.id.seekbar3);

    }
}

