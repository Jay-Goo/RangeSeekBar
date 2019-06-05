package com.jaygoo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.jaygoo.widget.SeekBar.INDICATOR_MODE_ALWAYS_SHOW;


public class RangeSeekBar extends View {

    //normal seekBar mode
    public final static int SEEKBAR_MODE_SINGLE = 1;
    //RangeSeekBar
    public final static int SEEKBAR_MODE_RANGE = 2;

    //number according to the actual proportion of the number of arranged;
    public final static int TRICK_MARK_MODE_NUMBER = 0;
    //other equally arranged
    public final static int TRICK_MARK_MODE_OTHER = 1;
    //tick mark text gravity
    public final static int TRICK_MARK_GRAVITY_LEFT = 0;
    public final static int TRICK_MARK_GRAVITY_CENTER = 1;
    public final static int TRICK_MARK_GRAVITY_RIGHT = 2;

    //tick mark text layout gravity
    public final static int TRICK_MARK_LAYOUT_GRAVITY_TOP = 0;
    public final static int TRICK_MARK_LAYOUT_GRAVITY_BOTTOM = 1;

    protected int lineTop, lineBottom, lineLeft, lineRight;
    private int seekBarMode;
    //刻度模式：number根据数字实际比例排列；other 均分排列
    private int tickMarkMode;
    //刻度与进度条间的间距
    //The spacing between the tick mark and the progress bar
    private int tickMarkTextMargin;
    //刻度文字与提示文字的大小
    //tick mark text and prompt text size
    private int tickMarkTextSize;
    private int tickMarkGravity;
    private int tickMarkLayoutGravity;
    private int tickMarkTextColor;
    private int tickMarkInRangeTextColor;
    //刻度上显示的文字
    //The texts displayed on the scale
    private CharSequence[] tickMarkTextArray;
    //进度条圆角
    //radius of progress bar
    private float progressRadius;
    //进度中进度条的颜色
    //the color of seekBar in progress
    private int progressColor;
    //默认进度条颜色
    //the default color of the progress bar
    private int progressDefaultColor;
    //the progress height
    private int progressHeight;
    //the range interval of RangeSeekBar
    private float minInterval;

    //enable RangeSeekBar two thumb Overlap
    private boolean enableThumbOverlap;
    //****************** the above is attr value  ******************//
    //用户设置的真实的最大值和最小值
    //True values set by the user
    private float minProgress, maxProgress;
    // the progress width
    private int lineWidth;
    private int linePaddingRight;
    private float touchDownX;
    //剩余最小间隔的进度
    protected float reservePercent;
    private boolean isEnable = true;
    private boolean isScaleThumb = false;
    private Paint paint = new Paint();
    private RectF backgroundLineRect = new RectF();
    private RectF foregroundLineRect = new RectF();
    private Rect tickMarkTextRect = new Rect();
    private SeekBar leftSB;
    private SeekBar rightSB;
    protected SeekBar currTouchSB;
    private OnRangeChangedListener callback;


    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
        leftSB = new SeekBar(this, attrs, true);
        rightSB = new SeekBar(this, attrs, false);
        rightSB.setVisible(seekBarMode != SEEKBAR_MODE_SINGLE);
        setRange(minProgress, maxProgress, minInterval);
        initProgressLine();
    }

    private void initProgressLine() {
        //Android 7.0以后，优化了View的绘制，onMeasure和onSizeChanged调用顺序有所变化
        //Android7.0以下：onMeasure--->onSizeChanged--->onMeasure
        //Android7.0以上：onMeasure--->onSizeChanged
        int leftLineTop = (int) (leftSB.getIndicatorHeight() + leftSB.getIndicatorArrowSize() + leftSB.getIndicatorMargin()
                + leftSB.getThumbHeight() * leftSB.getThumbScaleRatio() / 2 - progressHeight / 2);
        if (seekBarMode == SEEKBAR_MODE_SINGLE) {
            lineTop = leftLineTop;
        } else {
            int rightLineTop = (int) (rightSB.getIndicatorHeight() + rightSB.getIndicatorArrowSize() + rightSB.getIndicatorMargin()
                    + rightSB.getThumbHeight() * rightSB.getThumbScaleRatio() / 2 - progressHeight / 2);
            lineTop = Math.max(leftLineTop, rightLineTop);
        }
        lineBottom = lineTop + progressHeight;
        //default value
        if (progressRadius < 0) {
            progressRadius = (int) ((getLineBottom() - getLineTop()) * 0.45f);
        }
    }

    private void initAttrs(AttributeSet attrs) {
        try {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
            seekBarMode = t.getInt(R.styleable.RangeSeekBar_rsb_mode, SEEKBAR_MODE_RANGE);
            minProgress = t.getFloat(R.styleable.RangeSeekBar_rsb_min, 0);
            maxProgress = t.getFloat(R.styleable.RangeSeekBar_rsb_max, 100);
            minInterval = t.getFloat(R.styleable.RangeSeekBar_rsb_min_interval, 0);
            progressColor = t.getColor(R.styleable.RangeSeekBar_rsb_progress_color, 0xFF4BD962);
            progressRadius = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_progress_radius, -1);
            progressDefaultColor = t.getColor(R.styleable.RangeSeekBar_rsb_progress_default_color, 0xFFD7D7D7);
            progressHeight = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_progress_height, Utils.dp2px(getContext(), 2));
            tickMarkMode = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_mode, TRICK_MARK_MODE_NUMBER);
            tickMarkGravity = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_gravity, TRICK_MARK_GRAVITY_CENTER);
            tickMarkLayoutGravity = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_layout_gravity, TRICK_MARK_LAYOUT_GRAVITY_TOP);
            tickMarkTextArray = t.getTextArray(R.styleable.RangeSeekBar_rsb_tick_mark_text_array);
            tickMarkTextMargin = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_tick_mark_text_margin, Utils.dp2px(getContext(), 7));
            tickMarkTextSize = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_tick_mark_text_size, Utils.dp2px(getContext(), 12));
            tickMarkTextColor = t.getColor(R.styleable.RangeSeekBar_rsb_tick_mark_text_color, progressDefaultColor);
            tickMarkInRangeTextColor = t.getColor(R.styleable.RangeSeekBar_rsb_tick_mark_text_color, progressColor);
            t.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightNeeded = 2 * getLineTop() + progressHeight;
        /*
         * onMeasure传入的widthMeasureSpec和heightMeasureSpec不是一般的尺寸数值，而是将模式和尺寸组合在一起的数值
         * MeasureSpec.EXACTLY 是精确尺寸
         * MeasureSpec.AT_MOST 是最大尺寸
         * MeasureSpec.UNSPECIFIED 是未指定尺寸
         */
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = MeasureSpec.makeMeasureSpec(heightNeeded, MeasureSpec.AT_MOST);
        } else {
            heightSize = MeasureSpec.makeMeasureSpec(
                    heightNeeded, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算进度条的位置，并根据它初始化两个按钮的位置
        // Calculates the position of the progress bar and initializes the positions of
        // the two buttons based on it
        lineLeft = leftSB.getThumbWidth() / 2 + getPaddingLeft();
        lineRight = w - lineLeft - getPaddingRight();
        lineWidth = lineRight - lineLeft;
        linePaddingRight = w - lineRight;
        backgroundLineRect.set(getLineLeft(), getLineTop(), getLineRight(), getLineBottom());
        int lineCenterY = (getLineBottom() + getLineTop()) / 2;
        leftSB.onSizeChanged(getLineLeft(), lineCenterY, lineWidth);
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSB.onSizeChanged(getLineLeft(), lineCenterY, lineWidth);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawTickMark(canvas);
        onDrawProgressBar(canvas);
        onDrawSeekBar(canvas);
    }

    //绘制刻度，并且根据当前位置是否在刻度范围内设置不同的颜色显示
    // Draw the scales, and according to the current position is set within
    // the scale range of different color display
    protected void onDrawTickMark(Canvas canvas) {
        if (tickMarkTextArray != null) {
            int trickPartWidth = lineWidth / (tickMarkTextArray.length - 1);
            for (int i = 0; i < tickMarkTextArray.length; i++) {
                final String text2Draw = tickMarkTextArray[i].toString();
                if (TextUtils.isEmpty(text2Draw)) continue;
                paint.getTextBounds(text2Draw, 0, text2Draw.length(), tickMarkTextRect);
                paint.setColor(tickMarkTextColor);
                //平分显示
                float x;
                if (tickMarkMode == TRICK_MARK_MODE_OTHER) {
                    if (tickMarkGravity == TRICK_MARK_GRAVITY_RIGHT) {
                        x = getLineLeft() + i * trickPartWidth - tickMarkTextRect.width();
                    } else if (tickMarkGravity == TRICK_MARK_GRAVITY_CENTER) {
                        x = getLineLeft() + i * trickPartWidth - tickMarkTextRect.width() / 2f;
                    } else {
                        x = getLineLeft() + i * trickPartWidth;
                    }
                } else {
                    float num = Utils.parseFloat(text2Draw);
                    SeekBarState[] states = getRangeSeekBarState();
                    if (Utils.compareFloat(num, states[0].value) != -1 && Utils.compareFloat(num, states[1].value) != 1 && (seekBarMode == SEEKBAR_MODE_RANGE)) {
                        paint.setColor(tickMarkInRangeTextColor);
                    }
                    //按实际比例显示
                    x = getLineLeft() + lineWidth * (num - minProgress) / (maxProgress - minProgress)
                            - tickMarkTextRect.width() / 2f;
                }
                float y;
                if (tickMarkLayoutGravity == TRICK_MARK_LAYOUT_GRAVITY_TOP) {
                    y = getLineTop() - tickMarkTextMargin;
                } else {
                    y = getLineBottom() + tickMarkTextMargin + tickMarkTextRect.height();
                }
                canvas.drawText(text2Draw, x, y, paint);
            }
        }
    }

    //绘制进度条
    // draw the progress bar
    protected void onDrawProgressBar(Canvas canvas) {
        paint.setColor(progressDefaultColor);
        canvas.drawRoundRect(backgroundLineRect, progressRadius, progressRadius, paint);
        paint.setColor(progressColor);
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            foregroundLineRect.top = getLineTop();
            foregroundLineRect.left = leftSB.left + leftSB.getThumbWidth() / 2f + lineWidth * leftSB.currPercent;
            foregroundLineRect.right = rightSB.left + rightSB.getThumbWidth() / 2f + lineWidth * rightSB.currPercent;
            foregroundLineRect.bottom = getLineBottom();
            canvas.drawRoundRect(foregroundLineRect, progressRadius, progressRadius, paint);
        } else {
            foregroundLineRect.top = getLineTop();
            foregroundLineRect.left = leftSB.left + leftSB.getThumbWidth() / 2f;
            foregroundLineRect.right = leftSB.left + leftSB.getThumbWidth() / 2f + lineWidth * leftSB.currPercent;
            foregroundLineRect.bottom = getLineBottom();
            canvas.drawRoundRect(foregroundLineRect, progressRadius, progressRadius, paint);
        }
    }

    //绘制SeekBar相关
    protected void onDrawSeekBar(Canvas canvas) {
        //draw left SeekBar
        if (leftSB.getIndicatorShowMode() == INDICATOR_MODE_ALWAYS_SHOW) {
            leftSB.setShowIndicatorEnable(true);
        }
        leftSB.draw(canvas);

        //draw right SeekBar
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            if (rightSB.getIndicatorShowMode() == INDICATOR_MODE_ALWAYS_SHOW) {
                rightSB.setShowIndicatorEnable(true);
            }
            rightSB.draw(canvas);
        }
    }

    //初始化画笔
    private void initPaint() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressDefaultColor);
        paint.setTextSize(tickMarkTextSize);
    }

    public void setProgress(float value) {
        setProgress(value, maxProgress);
    }

    public void setProgress(float leftValue, float rightValue) {
        leftValue = Math.min(leftValue, rightValue);
        rightValue = Math.max(leftValue, rightValue);
        if (rightValue - leftValue < minInterval) {
            leftValue = rightValue - minInterval;
        }

        if (leftValue < minProgress) {
            throw new IllegalArgumentException("setProgress() min < (preset min - offsetValue) . #min:" + leftValue + " #preset min:" + rightValue);
        }
        if (rightValue > maxProgress) {
            throw new IllegalArgumentException("setProgress() max > (preset max - offsetValue) . #max:" + rightValue + " #preset max:" + rightValue);
        }

        float range = maxProgress - minProgress;
        leftSB.currPercent = Math.abs(leftValue - minProgress) / range;
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSB.currPercent = Math.abs(rightValue - minProgress) / range;
        }

        if (callback != null) {
            callback.onRangeChanged(this, leftValue, rightValue, false);
        }
        invalidate();
    }


    /**
     * 设置范围
     *
     * @param min 最小值
     * @param max 最大值
     */
    public void setRange(float min, float max) {
        setRange(min, max, minInterval);
    }

    /**
     * 设置范围
     *
     * @param min         最小值
     * @param max         最大值
     * @param minInterval 最小间隔
     */
    public void setRange(float min, float max, float minInterval) {
        if (max <= min) {
            throw new IllegalArgumentException("setRange() max must be greater than min ! #max:" + max + " #min:" + min);
        }
        if (minInterval < 0) {
            throw new IllegalArgumentException("setRange() interval must be greater than zero ! #minInterval:" + minInterval);
        }
        if (minInterval >= max - min) {
            throw new IllegalArgumentException("setRange() interval must be less than (max - min) ! #minInterval:" + minInterval + " #max - min:" + (max - min));
        }

        maxProgress = max;
        minProgress = min;
        this.minInterval = minInterval;
        reservePercent = minInterval / (max - min);

        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            if (leftSB.currPercent + reservePercent <= 1 && leftSB.currPercent + reservePercent > rightSB.currPercent) {
                rightSB.currPercent = leftSB.currPercent + reservePercent;
            } else if (rightSB.currPercent - reservePercent >= 0 && rightSB.currPercent - reservePercent < leftSB.currPercent) {
                leftSB.currPercent = rightSB.currPercent - reservePercent;
            }
        } else {
            if (1 - reservePercent >= 0 && 1 - reservePercent < leftSB.currPercent) {
                leftSB.currPercent = 1 - reservePercent;
            }
        }
        invalidate();
    }

    /**
     * @return the two seekBar state , see {@link SeekBarState}
     */
    public SeekBarState[] getRangeSeekBarState() {
        float range = maxProgress - minProgress;
        SeekBarState leftSeekBarState = new SeekBarState();
        leftSeekBarState.value = minProgress + range * leftSB.currPercent;

        leftSeekBarState.indicatorText = String.valueOf(leftSeekBarState.value);
        if (Utils.compareFloat(leftSB.currPercent, 0f) == 0) {
            leftSeekBarState.isMin = true;
        } else if (Utils.compareFloat(leftSB.currPercent, 1f) == 0) {
            leftSeekBarState.isMax = true;
        }

        SeekBarState rightSeekBarState = new SeekBarState();
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSeekBarState.value = minProgress + range * rightSB.currPercent;
            rightSeekBarState.indicatorText = String.valueOf(rightSeekBarState.value);
            if (Utils.compareFloat(rightSB.currPercent, 0f) == 0) {
                rightSeekBarState.isMin = true;
            } else if (Utils.compareFloat(rightSB.currPercent, 1f) == 0) {
                rightSeekBarState.isMax = true;
            }
        }

        return new SeekBarState[]{leftSeekBarState, rightSeekBarState};
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.isEnable = enabled;
    }

    public void setIndicatorText(String progress) {
        leftSB.setIndicatorText(progress);
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSB.setIndicatorText(progress);
        }
    }

    /**
     * format number indicator text
     *
     * @param formatPattern format rules
     */
    public void setIndicatorTextDecimalFormat(String formatPattern) {
        leftSB.setIndicatorTextDecimalFormat(formatPattern);
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSB.setIndicatorTextDecimalFormat(formatPattern);
        }
    }

    /**
     * format string indicator text
     *
     * @param formatPattern format rules
     */
    public void setIndicatorTextStringFormat(String formatPattern) {
        leftSB.setIndicatorTextStringFormat(formatPattern);
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            rightSB.setIndicatorTextStringFormat(formatPattern);
        }
    }

    private void changeThumbActivateState(boolean hasActivate) {
        if (hasActivate && currTouchSB != null) {
            boolean state = currTouchSB == leftSB;
            leftSB.setActivate(state);
            if (seekBarMode == SEEKBAR_MODE_RANGE)
                rightSB.setActivate(!state);
        } else {
            leftSB.setActivate(false);
            if (seekBarMode == SEEKBAR_MODE_RANGE)
                rightSB.setActivate(false);
        }
    }

    protected float getEventX(MotionEvent event) {
        return event.getX();
    }

    protected float getEventY(MotionEvent event) {
        return event.getY();
    }

    /**
     * scale the touch seekBar thumb
     */
    private void scaleCurrentSeekBarThumb() {
        if (currTouchSB != null && currTouchSB.getThumbScaleRatio() > 1f && !isScaleThumb) {
            isScaleThumb = true;
            currTouchSB.setThumbWidth((int) (currTouchSB.getThumbWidth() * currTouchSB.getThumbScaleRatio()));
            currTouchSB.setThumbHeight((int) (currTouchSB.getThumbHeight() * currTouchSB.getThumbScaleRatio()));
            currTouchSB.onSizeChanged(getLineLeft(), getLineBottom(), lineWidth);
        }
    }

    /**
     * reset the touch seekBar thumb
     */
    private void resetCurrentSeekBarThumb() {
        if (currTouchSB != null && currTouchSB.getThumbScaleRatio() > 1f && isScaleThumb) {
            isScaleThumb = false;
            currTouchSB.setThumbWidth((int) (currTouchSB.getThumbWidth() / currTouchSB.getThumbScaleRatio()));
            currTouchSB.setThumbHeight((int) (currTouchSB.getThumbHeight() / currTouchSB.getThumbScaleRatio()));
            currTouchSB.onSizeChanged(getLineLeft(), getLineBottom(), lineWidth);
        }
    }

    //calculate currTouchSB percent by MotionEvent
    protected float calculateCurrentSeekBarPercent(float touchDownX) {
        float percent = (touchDownX - getLineLeft()) * 1f / (lineWidth);
        if (touchDownX < getLineLeft()) {
            percent = 0;
        } else if (touchDownX > getLineRight()) {
            percent = 1;
        }
        //RangeMode minimum interval
        if (seekBarMode == SEEKBAR_MODE_RANGE) {
            if (currTouchSB == leftSB) {
                if (percent > rightSB.currPercent - reservePercent) {
                    percent = rightSB.currPercent - reservePercent;
                }
            } else if (currTouchSB == rightSB) {
                if (percent < leftSB.currPercent + reservePercent) {
                    percent = leftSB.currPercent + reservePercent;
                }
            }
        }
        return percent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnable) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = getEventX(event);
                if (seekBarMode == SEEKBAR_MODE_RANGE) {
                    if (rightSB.currPercent >= 1 && leftSB.collide(getEventX(event), getEventY(event))) {
                        currTouchSB = leftSB;
                        scaleCurrentSeekBarThumb();
                    } else if (rightSB.collide(getEventX(event), getEventY(event))) {
                        currTouchSB = rightSB;
                        scaleCurrentSeekBarThumb();
                    } else {
                        float percentClick = (touchDownX - getLineLeft()) * 1f / (lineWidth);
                        float distanceLeft = Math.abs(leftSB.currPercent - percentClick);
                        float distanceRight = Math.abs(rightSB.currPercent - percentClick);
                        if (distanceLeft < distanceRight) {
                            currTouchSB = leftSB;
                        } else {
                            currTouchSB = rightSB;
                        }
                        currTouchSB.slide(percentClick);
                    }
                } else {
                    currTouchSB = leftSB;
                    scaleCurrentSeekBarThumb();
                }

                //Intercept parent TouchEvent
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (callback != null) {
                    callback.onStartTrackingTouch(this, currTouchSB == leftSB);
                }
                changeThumbActivateState(true);
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = getEventX(event);
                if ((seekBarMode == SEEKBAR_MODE_RANGE) && leftSB.currPercent == rightSB.currPercent) {
                    currTouchSB.materialRestore();
                    if (callback != null) {
                        callback.onStopTrackingTouch(this, currTouchSB == leftSB);
                    }
                    if (x - touchDownX > 0) {
                        //method to move right
                        if (currTouchSB != rightSB) {
                            resetCurrentSeekBarThumb();
                            currTouchSB = rightSB;
                        }
                    } else {
                        //method to move left
                        if (currTouchSB != leftSB) {
                            resetCurrentSeekBarThumb();
                            currTouchSB = leftSB;
                        }
                    }
                    if (callback != null) {
                        callback.onStartTrackingTouch(this, currTouchSB == leftSB);
                    }
                }
                scaleCurrentSeekBarThumb();
                currTouchSB.material = currTouchSB.material >= 1 ? 1 : currTouchSB.material + 0.1f;
                touchDownX = x;
                currTouchSB.slide(calculateCurrentSeekBarPercent(touchDownX));
                currTouchSB.setShowIndicatorEnable(true);

                if (callback != null) {
                    SeekBarState[] states = getRangeSeekBarState();
                    callback.onRangeChanged(this, states[0].value, states[1].value, true);
                }
                invalidate();
                //Intercept parent TouchEvent
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                changeThumbActivateState(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (seekBarMode == SEEKBAR_MODE_RANGE) {
                    rightSB.setShowIndicatorEnable(false);
                }
                if (currTouchSB == leftSB) {
                    resetCurrentSeekBarThumb();
                } else if (currTouchSB == rightSB) {
                    resetCurrentSeekBarThumb();
                }
                leftSB.setShowIndicatorEnable(false);
                if (callback != null) {
                    SeekBarState[] states = getRangeSeekBarState();
                    callback.onRangeChanged(this, states[0].value, states[1].value, false);
                }
                //Intercept parent TouchEvent
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                changeThumbActivateState(false);
                break;
            case MotionEvent.ACTION_UP:
                if (seekBarMode == SEEKBAR_MODE_RANGE) {
                    rightSB.setShowIndicatorEnable(false);
                }
                leftSB.setShowIndicatorEnable(false);
                currTouchSB.materialRestore();
                resetCurrentSeekBarThumb();
                if (callback != null) {
                    SeekBarState[] states = getRangeSeekBarState();
                    callback.onRangeChanged(this, states[0].value, states[1].value, false);
                }
                //Intercept parent TouchEvent
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (callback != null) {
                    callback.onStopTrackingTouch(this, currTouchSB == leftSB);
                }
                changeThumbActivateState(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.minValue = minProgress;
        ss.maxValue = maxProgress;
        ss.rangeInterval = minInterval;
        SeekBarState[] results = getRangeSeekBarState();
        ss.currSelectedMin = results[0].value;
        ss.currSelectedMax = results[1].value;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        try {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            float min = ss.minValue;
            float max = ss.maxValue;
            float rangeInterval = ss.rangeInterval;
            setRange(min, max, rangeInterval);
            float currSelectedMin = ss.currSelectedMin;
            float currSelectedMax = ss.currSelectedMax;
            setProgress(currSelectedMin, currSelectedMax);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOnRangeChangedListener(OnRangeChangedListener listener) {
        callback = listener;
    }

    /**
     * if is single mode, please use it to get the SeekBar
     *
     * @return left seek bar
     */
    public SeekBar getLeftSeekBar() {
        return leftSB;
    }

    public SeekBar getRightSeekBar() {
        return rightSB;
    }

    public float getSingleSeekBarProgress() {
        return getLeftSeekBarProgress();
    }

    public float getLeftSeekBarProgress() {
        return getRangeSeekBarState()[0].value;
    }

    public float getRightSeekBarProgress() {
        return getRangeSeekBarState()[1].value;
    }

    public int getLineTop() {
        return lineTop;
    }

    public void setLineTop(int lineTop) {
        this.lineTop = lineTop;
    }

    public int getLineBottom() {
        return lineBottom;
    }

    public void setLineBottom(int lineBottom) {
        this.lineBottom = lineBottom;
    }

    public int getLineLeft() {
        return lineLeft;
    }

    public void setLineLeft(int lineLeft) {
        this.lineLeft = lineLeft;
    }

    public int getLineRight() {
        return lineRight;
    }

    public void setLineRight(int lineRight) {
        this.lineRight = lineRight;
    }

    public int getLinePaddingRight() {
        return linePaddingRight;
    }

    public int getProgressHeight() {
        return progressHeight;
    }

    public void setProgressHeight(int progressHeight) {
        this.progressHeight = progressHeight;
    }

    public float getMinProgress() {
        return minProgress;
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setProgressColor(int progressDefaultColor, int progressColor) {
        this.progressDefaultColor = progressDefaultColor;
        this.progressColor = progressColor;
    }

    public int getTickMarkTextColor() {
        return tickMarkTextColor;
    }

    public void setTickMarkTextColor(int tickMarkTextColor) {
        this.tickMarkTextColor = tickMarkTextColor;
    }

    public int getTickMarkInRangeTextColor() {
        return tickMarkInRangeTextColor;
    }

    public void setTickMarkInRangeTextColor(int tickMarkInRangeTextColor) {
        this.tickMarkInRangeTextColor = tickMarkInRangeTextColor;
    }

    public int getSeekBarMode() {
        return seekBarMode;
    }

    public void setSeekBarMode(int seekBarMode) {
        this.seekBarMode = seekBarMode;
        rightSB.setVisible(seekBarMode != SEEKBAR_MODE_SINGLE);
    }

    public int getTickMarkMode() {
        return tickMarkMode;
    }

    public void setTickMarkMode(int tickMarkMode) {
        this.tickMarkMode = tickMarkMode;
    }

    public int getTickMarkTextMargin() {
        return tickMarkTextMargin;
    }

    public void setTickMarkTextMargin(int tickMarkTextMargin) {
        this.tickMarkTextMargin = tickMarkTextMargin;
    }

    public int getTickMarkTextSize() {
        return tickMarkTextSize;
    }

    public void setTickMarkTextSize(int tickMarkTextSize) {
        this.tickMarkTextSize = tickMarkTextSize;
    }

    public int getTickMarkGravity() {
        return tickMarkGravity;
    }

    public void setTickMarkGravity(int tickMarkGravity) {
        this.tickMarkGravity = tickMarkGravity;
    }

    public CharSequence[] getTickMarkTextArray() {
        return tickMarkTextArray;
    }

    public void setTickMarkTextArray(CharSequence[] tickMarkTextArray) {
        this.tickMarkTextArray = tickMarkTextArray;
    }

    public float getMinInterval() {
        return minInterval;
    }

    public float getProgressRadius() {
        return progressRadius;
    }

    public void setProgressRadius(float progressRadius) {
        this.progressRadius = progressRadius;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public int getProgressDefaultColor() {
        return progressDefaultColor;
    }

    public void setProgressDefaultColor(int progressDefaultColor) {
        this.progressDefaultColor = progressDefaultColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }


    public void setTypeface(Typeface typeFace) {
        paint.setTypeface(typeFace);
    }

    public boolean isEnableThumbOverlap() {
        return enableThumbOverlap;
    }

    public void setEnableThumbOverlap(boolean enableThumbOverlap) {
        this.enableThumbOverlap = enableThumbOverlap;
    }
}
