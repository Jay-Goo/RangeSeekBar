package com.jaygoo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2018/5/10
 * 描    述:
 * ================================================
 */
public class VerticalRangeSeekBar extends RangeSeekBar {

    public final static int DICTION_LEFT = -1;
    public final static int DICTION_RIGHT = 1;

    private int orientation;

    public VerticalRangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
        orientation = t.getInt(R.styleable.RangeSeekBar_rsb_orientation, DICTION_LEFT);
        t.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (orientation == DICTION_LEFT){
            canvas.rotate(-90);
            canvas.translate(-getHeight(), 0);
        }else {
            canvas.rotate(90);
            canvas.translate(0,-getWidth());
        }
        super.onDraw(canvas);
    }


    @Override
    protected float getEventX(MotionEvent event) {
        if (orientation == DICTION_LEFT){
            return getHeight() - event.getY();
        }else {
            return event.getY();
        }
    }

    @Override
    protected float getEventY(MotionEvent event) {
        if (orientation == DICTION_LEFT){
            return event.getX();
        }else {
            return -event.getX() + getWidth();
        }
    }
}
