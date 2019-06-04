package com.jaygoo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
public class VerticalStepRangeSeekBar extends StepRangeSeekBar {
    public final static int DICTION_LEFT = -1;
    public final static int DICTION_RIGHT = 1;
    private int orientation = DICTION_LEFT;

    public VerticalStepRangeSeekBar(Context context) {
        this(context, null);
    }

    public VerticalStepRangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
            orientation = t.getInt(R.styleable.RangeSeekBar_rsb_orientation, DICTION_LEFT);
            t.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
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
