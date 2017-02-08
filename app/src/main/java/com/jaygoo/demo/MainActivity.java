package com.jaygoo.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jaygoo.widget.RangeSeekbar;

public class MainActivity extends AppCompatActivity {

    private RangeSeekbar seekbar1;
    private RangeSeekbar seekbar2;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar1 = (RangeSeekbar)findViewById(R.id.seekbar1);
        seekbar2 = (RangeSeekbar)findViewById(R.id.seekbar2);
        tv2 = (TextView)findViewById(R.id.progress2_tv);

        seekbar1.setValue(10);
        seekbar2.setValue(10,70);

        seekbar1.setOnRangeChangedListener(new RangeSeekbar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekbar view, float min, float max, boolean isFromUser) {
                seekbar1.setProgress((int)min+"%");
            }
        });

        seekbar2.setOnRangeChangedListener(new RangeSeekbar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekbar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    tv2.setText(min + "-" + max);
                }
            }
        });

    }
}
