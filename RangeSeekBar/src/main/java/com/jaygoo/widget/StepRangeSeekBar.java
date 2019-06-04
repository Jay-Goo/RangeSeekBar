package com.jaygoo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * //                       _ooOoo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                       O\ = /O
 * //                   ____/`---'\____
 * //                 .   ' \\| |// `.
 * //                  / \\||| : |||// \
 * //                / _||||| -:- |||||- \
 * //                  | | \\\ - /// | |
 * //                | \_| ''\---/'' | |
 * //                 \ .-\__ `-` ___/-. /
 * //              ______`. .' /--.--\ `. . __
 * //           ."" '< `.___\_<|>_/___.' >'"".
 * //          | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \ \ `-. \_ __\ /__ _/ .-` / /
 * //    ======`-.____`-.___\_____/___.-`____.-'======
 * //                       `=---='
 * //
 * //    .............................................
 * //             佛祖保佑             永无BUG
 * =====================================================
 * 作    者：JayGoo
 * 创建日期：2019-06-03
 * 描    述:
 * =====================================================
 */
public class StepRangeSeekBar extends RangeSeekBar {

    //the color of step divs
    private int stepsColor;
    //the width of each step
    private float stepsWidth;
    //the height of each step
    private float stepsHeight;
    //the radius of step divs
    private float stepsRadius;
    //steps is 0 will disable StepSeekBar
    private int steps;

    private RectF stepDivRect = new RectF();
    private Paint paint = new Paint();

    public StepRangeSeekBar(Context context) {
        this(context, null);
    }

    public StepRangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        try {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.StepRangeSeekBar);
            steps = t.getInt(R.styleable.StepRangeSeekBar_rsb_steps, 0);
            stepsColor = t.getColor(R.styleable.StepRangeSeekBar_rsb_step_color, 0xFF9d9d9d);
            stepsRadius = t.getDimension(R.styleable.StepRangeSeekBar_rsb_step_radius, 0);
            stepsWidth = t.getDimension(R.styleable.StepRangeSeekBar_rsb_step_width, 0);
            stepsHeight = t.getDimension(R.styleable.StepRangeSeekBar_rsb_step_height, 0);
            t.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDrawProgressBar(Canvas canvas) {
        super.onDrawProgressBar(canvas);
        onDrawSteps(canvas);
    }

    //draw steps
    protected void onDrawSteps(Canvas canvas) {
        if (steps < 1 || stepsHeight <= 0 || stepsWidth <= 0) return;
        int stepMarks = getLineWidth() / (steps);
        float extHeight = (stepsHeight - getProgressHeight()) / 2f;
        for (int k = 0; k <= steps; k++) {
            float x = getLineLeft() + k * stepMarks - stepsWidth / 2f;
            paint.setColor(stepsColor);
            stepDivRect.set(x, getLineTop() - extHeight, x + stepsWidth, getLineBottom() + extHeight);
            canvas.drawRoundRect(stepDivRect, stepsRadius, stepsRadius, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (steps < 1) return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float percent = calculateCurrentSeekBarPercent(event.getX());
            float stepPercent = 1.0f / steps;
            int stepSelected = new BigDecimal(percent / stepPercent).setScale(0, RoundingMode.HALF_UP).intValue();
            currTouchSB.slide(stepSelected * stepPercent);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public SeekBarState[] getRangeSeekBarState() {
        if (steps < 1) return super.getRangeSeekBarState();
        float range = getMaxProgress() - getMinProgress();
        SeekBarState leftSeekBarState = new SeekBarState();
        leftSeekBarState.value = getMinProgress() + range * getLeftSeekBar().currPercent;
        leftSeekBarState.indicatorText = String.valueOf(leftSeekBarState.value);
        if (leftSeekBarState.value == 0) {
            leftSeekBarState.isMin = true;
        } else if (leftSeekBarState.value == steps) {
            leftSeekBarState.isMax = true;
        }

        SeekBarState rightSeekBarState = new SeekBarState();
        if (getSeekBarMode() == SEEKBAR_MODE_RANGE) {
            rightSeekBarState.value = getMinProgress() + range * getRightSeekBar().currPercent;
            rightSeekBarState.indicatorText = String.valueOf(rightSeekBarState.value);
            if (rightSeekBarState.value == 0) {
                rightSeekBarState.isMin = true;
            } else if (rightSeekBarState.value == steps) {
                rightSeekBarState.isMax = true;
            }
        }
        return new SeekBarState[]{leftSeekBarState, rightSeekBarState};
    }


    //******************* Attributes getter and setter *******************//

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    public int getStepsColor() {
        return stepsColor;
    }

    public void setStepsColor(int stepsColor) {
        this.stepsColor = stepsColor;
    }

    public float getStepsWidth() {
        return stepsWidth;
    }

    public void setStepsWidth(float stepsWidth) {
        this.stepsWidth = stepsWidth;
    }

    public float getStepsHeight() {
        return stepsHeight;
    }

    public void setStepsHeight(float stepsHeight) {
        this.stepsHeight = stepsHeight;
    }

    public float getStepsRadius() {
        return stepsRadius;
    }

    public void setStepsRadius(float stepsRadius) {
        this.stepsRadius = stepsRadius;
    }


}
