package com.jaygoo.demo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.Utils;

public class MainActivity extends AppCompatActivity {

    private RangeSeekBar seekbar1, seekbar2, seekbar3, seekbar4, seekbar5, seekbar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        seekbar2.setTypeface(Typeface.DEFAULT_BOLD);
        seekbar2.getLeftSeekBar().setTypeface(Typeface.DEFAULT_BOLD);
        seekbar2.setIndicatorTextDecimalFormat("0.00");
        seekbar4.setIndicatorTextDecimalFormat("0");
        seekbar5.setIndicatorTextStringFormat("你是%s吗");

        seekbar1.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                seekbar1.setIndicatorText((int)leftValue+"");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //do what you want!!
            }
        });
        seekbar2.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }
        });

        seekbar4.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (leftValue <= 50){
                    view.setProgressColor(getResources().getColor(R.color.colorAccent));
                    view.getLeftSeekBar().setThumbDrawableId(R.drawable.thumb_activated);
                    view.getLeftSeekBar().setIndicatorBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else {
                    view.setProgressColor(getResources().getColor(R.color.colorPrimary));
                    view.getLeftSeekBar().setIndicatorBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    view.getLeftSeekBar().setThumbDrawableId(R.drawable.thumb_blue);
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        seekbar5.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        seekbar6.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                Log.e("VALUES OF RANGE STEP", leftValue +  " , " + rightValue);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                Log.e("STOP TRACKING", "stop tracking range steps" );
            }
        });

        seekbar1.setValue(90);
        seekbar2.setValue(-0.5f,0.8f);
        seekbar3.setValue(-26, 90);
        seekbar5.setValue(25, 75);
    }

    private void initView(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.layout_action_bar);
            getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, VerticalDemoActivity.class));
                }
            });
        }

        seekbar1 = findViewById(R.id.seekbar1);
        seekbar2 = findViewById(R.id.seekbar2);
        seekbar3 = findViewById(R.id.seekbar3);
        seekbar4 = findViewById(R.id.seekbar4);
        seekbar5 = findViewById(R.id.seekbar5);
        seekbar6 = findViewById(R.id.seekbar6);
    }

}
