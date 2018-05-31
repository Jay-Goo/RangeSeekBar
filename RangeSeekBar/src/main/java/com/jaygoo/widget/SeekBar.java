package com.jaygoo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.text.DecimalFormat;


/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2018/5/8
 * 描    述:
 * ================================================
 */

public class SeekBar {
    //the indicator mode
    public static final int INDICATOR_MODE_SHOW_WHEN_TOUCH = 0;
    public static final int INDICATOR_MODE_ALWAYS_HIDE = 1;
    public static final int INDICATOR_MODE_ALWAYS_SHOW_AFTER_TOUCH = 2;
    public static final int INDICATOR_MODE_ALWAYS_SHOW = 3;

    private int indicatorShowMode;

    //进度提示背景的高度，宽度如果是0的话会自适应调整
    //Progress prompted the background height, width,
    //if width is 0, then adaptively adjust
    // indicatorHeight must > 0 if you want use indicator
    private int indicatorHeight;
    private int indicatorWidth;
    //进度提示背景与按钮之间的距离
    //The progress indicates the distance between the background and the button
    private int indicatorMargin;
    private int indicatorDrawableId;
    private int indicatorArrowSize;
    private int indicatorTextSize;
    private int indicatorTextColor;
    private int indicatorBackgroundColor;
    private int indicatorPaddingLeft, indicatorPaddingRight, indicatorPaddingTop, indicatorPaddingBottom;
    private int thumbDrawableId;
    private int thumbInactivatedDrawableId;
    private int thumbSize;

    //****************** the above is attr value  ******************//

    private int lineWidth;
    protected int left, right, top, bottom;
    protected float currPercent;
    protected float material = 0;
    private boolean isShowIndicator;
    private boolean isLeft;
    private Bitmap thumbBitmap;
    private Bitmap thumbInactivatedBitmap;
    private Bitmap indicatorBitmap;
    private ValueAnimator anim;
    private String userText2Draw;
    private boolean isActivate = false;
    private RangeSeekBar rangeSeekBar;
    private String indicatorTextStringFormat;
    private Path indicatorArrowPath = new Path();
    private Rect indicatorTextRect = new Rect();
    private Rect indicatorRect = new Rect();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private DecimalFormat indicatorTextDecimalFormat;


    public SeekBar(RangeSeekBar rangeSeekBar, AttributeSet attrs, boolean isLeft) {
        this.rangeSeekBar = rangeSeekBar;
        this.isLeft = isLeft;
        initAttrs(attrs);
        initVariables();
        initBitmap();
    }

    private void initAttrs(AttributeSet attrs){
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
        if (t == null)return;
        indicatorMargin = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_margin, 0);
        indicatorDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_indicator_drawable, 0);
        indicatorShowMode = t.getInt(R.styleable.RangeSeekBar_rsb_indicator_show_mode, INDICATOR_MODE_ALWAYS_HIDE);
        indicatorHeight = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_height, 0);
        indicatorWidth = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_width, 0);
        indicatorTextSize = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_text_size, Utils.dp2px(getContext(), 14));
        indicatorTextColor =  t.getColor(R.styleable.RangeSeekBar_rsb_indicator_text_color, Color.WHITE);
        indicatorBackgroundColor = t.getColor(R.styleable.RangeSeekBar_rsb_indicator_background_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
        indicatorPaddingLeft = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_left, 0);
        indicatorPaddingRight = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_right, 0);
        indicatorPaddingTop = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_top, 0);
        indicatorPaddingBottom = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_bottom, 0);
        indicatorArrowSize = (int)t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_arrow_size, 0);
        thumbDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_thumb_drawable, R.drawable.rsb_default_thumb);
        thumbInactivatedDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_thumb_inactivated_drawable, 0);
        thumbSize = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_thumb_size, Utils.dp2px(getContext(),26));
        t.recycle();
    }

    private void initVariables(){
        if (indicatorHeight <= 0 && indicatorShowMode != INDICATOR_MODE_ALWAYS_HIDE){
            throw new IllegalArgumentException("if you want to show indicator, the indicatorHeight must > 0");
        }
        if (indicatorArrowSize <= 0){
            indicatorArrowSize = thumbSize / 4;
        }
    }

    private Context getContext(){
        return rangeSeekBar.getContext();
    }

    private Resources getResources(){
        if (getContext() != null) return getContext().getResources();
        return null;
    }

    /**
     * 初始化进度提示的背景
     */
    private void initBitmap() {
        setIndicatorDrawableId(indicatorDrawableId);
        setThumbDrawableId(thumbDrawableId);
        setThumbInactivatedDrawableId(thumbInactivatedDrawableId);
    }

    /**
     * 计算每个按钮的位置和尺寸
     * Calculates the position and size of each button
     *
     * @param x position x
     * @param y position y
     * @param parentLineWidth the RangSeerBar progress line width
     */
    protected void onSizeChanged(int x, int y, int parentLineWidth) {
        left = x - thumbSize / 2;
        right = x + thumbSize / 2;
        top = y - thumbSize / 2;
        bottom = y + thumbSize / 2;
        lineWidth = parentLineWidth;
    }

    /**
     * This method will draw the indicator background dynamically according to the text.
     * you can use to set padding
     * @param canvas Canvas
     * @param text2Draw Indicator text
     */
    private void drawIndicator(Canvas canvas, String text2Draw){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(indicatorBackgroundColor);
        int contentWidth = indicatorTextRect.width() + indicatorPaddingLeft + indicatorPaddingRight;
        int realIndicatorWidth;
        if (indicatorWidth <= 0) {
            realIndicatorWidth = contentWidth;
        }else if (indicatorWidth < contentWidth) {
            realIndicatorWidth = contentWidth;
        }else {
            realIndicatorWidth = indicatorWidth;
        }

        indicatorRect.left = thumbSize / 2 - realIndicatorWidth / 2;
        indicatorRect.top = bottom - indicatorHeight - thumbSize - indicatorMargin;
        indicatorRect.right = indicatorRect.left + realIndicatorWidth;
        indicatorRect.bottom = indicatorRect.top + indicatorHeight;
        //draw default indicator arrow
        if (indicatorBitmap == null){
            //arrow three point
            //  b   c
            //    a
            int ax = thumbSize / 2;
            int ay = bottom - thumbSize - indicatorMargin;
            int bx = ax - indicatorArrowSize;
            int by = ay - indicatorArrowSize;
            int cx = ax + indicatorArrowSize;
            indicatorArrowPath.reset();
            indicatorArrowPath.moveTo(ax, ay);
            indicatorArrowPath.lineTo(bx, by);
            indicatorArrowPath.lineTo(cx, by);
            indicatorArrowPath.close();
            canvas.drawPath(indicatorArrowPath, paint);
            indicatorRect.bottom -= indicatorArrowSize;
            indicatorRect.top -= indicatorArrowSize;
        }

        //indicator background edge processing
        int defaultPaddingOffset = Utils.dp2px(getContext(), 1);
        int leftOffset = indicatorRect.width() / 2 - (int) (lineWidth * currPercent) - rangeSeekBar.getLineLeft() + defaultPaddingOffset;
        int rightOffset = indicatorRect.width() / 2 - (int) (lineWidth * (1 - currPercent)) - rangeSeekBar.getLinePaddingRight() + defaultPaddingOffset;
        if (leftOffset > 0){
            indicatorRect.left += leftOffset;
            indicatorRect.right += leftOffset;
        }else if (rightOffset > 0){
            indicatorRect.left -= rightOffset;
            indicatorRect.right -= rightOffset;
        }

        //draw indicator background
        if (indicatorBitmap != null){
            Utils.drawNinePath(canvas, indicatorBitmap, indicatorRect);
        }else {
            canvas.drawRect(indicatorRect, paint);
        }

        //draw indicator content text
        int tx, ty;
        if (indicatorPaddingLeft > 0){
            tx = indicatorRect.left + indicatorPaddingLeft;
        }else if (indicatorPaddingRight > 0){
            tx = indicatorRect.right - indicatorPaddingRight - indicatorTextRect.width();
        } else {
            tx = indicatorRect.left + (realIndicatorWidth - indicatorTextRect.width()) / 2;
        }

        if (indicatorPaddingTop > 0){
            ty = indicatorRect.top + indicatorTextRect.height() + indicatorPaddingTop;
        }else if (indicatorPaddingBottom > 0){
            ty = indicatorRect.bottom - indicatorTextRect.height() - indicatorPaddingBottom;
        }else {
            ty = indicatorRect.bottom - (indicatorHeight - indicatorTextRect.height()) / 2 + 1;
        }

        //draw indicator text
        paint.setColor(indicatorTextColor);
        canvas.drawText(text2Draw, tx, ty, paint);
    }

    /**
     * 绘制按钮和提示背景和文字
     * Draw buttons and tips for background and text
     *
     * @param canvas Canvas
     */
    protected void draw(Canvas canvas) {
        int offset = (int) (lineWidth * currPercent);
        canvas.save();
        canvas.translate(offset, 0);
        SeekBarState[] states = rangeSeekBar.getRangeSeekBarState();
        String text2Draw = userText2Draw;
        if (isLeft) {
            if (userText2Draw == null) {
                if (indicatorTextDecimalFormat != null){
                    text2Draw = indicatorTextDecimalFormat.format(states[0].value);
                }else {
                    text2Draw = states[0].indicatorText;
                }
            }
        } else {
            if (userText2Draw == null) {
                if (indicatorTextDecimalFormat != null){
                    text2Draw = indicatorTextDecimalFormat.format(states[1].value);
                }else {
                    text2Draw = states[1].indicatorText;
                }
            }
        }
        if (indicatorTextStringFormat != null){
            text2Draw = String.format(indicatorTextStringFormat, text2Draw);
        }
        paint.setTextSize(indicatorTextSize);
        paint.getTextBounds(text2Draw, 0, text2Draw.length(), indicatorTextRect);
        // translate canvas, then don't care left
        canvas.translate(left, 0);
        if (indicatorShowMode == INDICATOR_MODE_ALWAYS_SHOW) {
            setShowIndicatorEnable(true);
        }
        if (isShowIndicator) {
            drawIndicator(canvas, text2Draw);
        }
        drawThumb(canvas);
        canvas.restore();
    }

    /**
     * 绘制按钮
     * 如果没有图片资源，则绘制默认按钮
     * <p>
     * draw the thumb button
     * If there is no image resource, draw the default button
     *
     * @param canvas canvas
     */
    private void drawThumb(Canvas canvas) {
        if (thumbInactivatedBitmap != null && !isActivate){
                canvas.drawBitmap(thumbInactivatedBitmap, 0, rangeSeekBar.getLineTop() + (rangeSeekBar.getProgressHeight() - thumbSize) / 2, null);
        }else if (thumbBitmap != null){
            canvas.drawBitmap(thumbBitmap, 0, rangeSeekBar.getLineTop() + (rangeSeekBar.getProgressHeight() - thumbSize) / 2, null);
        }
    }


    /**
     * 拖动检测
     *
     * @return is collide
     */
    protected boolean collide(float x, float y) {
        int offset = (int) (lineWidth * currPercent);
        return x > left + offset && x < right + offset && y > top && y < bottom;
    }

    protected void slide(float percent) {
        if (percent < 0) percent = 0;
        else if (percent > 1) percent = 1;
        currPercent = percent;
    }

    protected void setShowIndicatorEnable(boolean isEnable) {
        switch (indicatorShowMode) {
            case INDICATOR_MODE_SHOW_WHEN_TOUCH:
                isShowIndicator = isEnable;
                break;
            case INDICATOR_MODE_ALWAYS_SHOW:
            case INDICATOR_MODE_ALWAYS_SHOW_AFTER_TOUCH:
                isShowIndicator = true;
                break;
            case INDICATOR_MODE_ALWAYS_HIDE:
                isShowIndicator = false;
                break;
        }
    }

    public void materialRestore() {
        if (anim != null) anim.cancel();
        anim = ValueAnimator.ofFloat(material, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                material = (float) animation.getAnimatedValue();
                if (rangeSeekBar != null) rangeSeekBar.invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                material = 0;
                if (rangeSeekBar != null) rangeSeekBar.invalidate();
            }
        });
        anim.start();
    }

    public void setIndicatorText(String text) {
        userText2Draw = text;
    }

    public void setIndicatorTextDecimalFormat(String formatPattern){
        indicatorTextDecimalFormat = new DecimalFormat(formatPattern);
    }

    public DecimalFormat getIndicatorTextDecimalFormat() {
        return indicatorTextDecimalFormat;
    }

    public void setIndicatorTextStringFormat(String formatPattern){
        indicatorTextStringFormat = formatPattern;
    }

    public int getIndicatorDrawableId() {
        return indicatorDrawableId;
    }

    public void setIndicatorDrawableId(int indicatorDrawableId) {
        if (indicatorDrawableId != 0) {
            this.indicatorDrawableId = indicatorDrawableId;
            indicatorBitmap = BitmapFactory.decodeResource(getResources(), indicatorDrawableId);
        }
    }

    public int getIndicatorArrowSize() {
        return indicatorArrowSize;
    }

    public void setIndicatorArrowSize(int indicatorArrowSize) {
        this.indicatorArrowSize = indicatorArrowSize;
    }

    public int getIndicatorPaddingLeft() {
        return indicatorPaddingLeft;
    }

    public void setIndicatorPaddingLeft(int indicatorPaddingLeft) {
        this.indicatorPaddingLeft = indicatorPaddingLeft;
    }

    public int getIndicatorPaddingRight() {
        return indicatorPaddingRight;
    }

    public void setIndicatorPaddingRight(int indicatorPaddingRight) {
        this.indicatorPaddingRight = indicatorPaddingRight;
    }

    public int getIndicatorPaddingTop() {
        return indicatorPaddingTop;
    }

    public void setIndicatorPaddingTop(int indicatorPaddingTop) {
        this.indicatorPaddingTop = indicatorPaddingTop;
    }

    public int getIndicatorPaddingBottom() {
        return indicatorPaddingBottom;
    }

    public void setIndicatorPaddingBottom(int indicatorPaddingBottom) {
        this.indicatorPaddingBottom = indicatorPaddingBottom;
    }

    public int getIndicatorMargin() {
        return indicatorMargin;
    }

    public void setIndicatorMargin(int indicatorMargin) {
        this.indicatorMargin = indicatorMargin;
    }

    public int getIndicatorShowMode() {
        return indicatorShowMode;
    }

    public void setIndicatorShowMode(int indicatorShowMode) {
        this.indicatorShowMode = indicatorShowMode;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    public int getIndicatorWidth() {
        return indicatorWidth;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
    }

    public int getIndicatorTextSize() {
        return indicatorTextSize;
    }

    public void setIndicatorTextSize(int indicatorTextSize) {
        this.indicatorTextSize = indicatorTextSize;
    }

    public int getIndicatorTextColor() {
        return indicatorTextColor;
    }

    public void setIndicatorTextColor(int indicatorTextColor) {
        this.indicatorTextColor = indicatorTextColor;
    }

    public int getIndicatorBackgroundColor() {
        return indicatorBackgroundColor;
    }

    public void setIndicatorBackgroundColor(int indicatorBackgroundColor) {
        this.indicatorBackgroundColor = indicatorBackgroundColor;
    }

    public int getThumbInactivatedDrawableId() {
        return thumbInactivatedDrawableId;
    }

    public void setThumbInactivatedDrawableId(int thumbInactivatedDrawableId) {
        if (thumbInactivatedDrawableId != 0 && getResources() != null){
            this.thumbInactivatedDrawableId = thumbInactivatedDrawableId;
            thumbInactivatedBitmap = Utils.drawableToBitmap(thumbSize, getResources().getDrawable(thumbInactivatedDrawableId));
        }
    }

    public int getThumbDrawableId() {
        return thumbDrawableId;
    }

    public void setThumbDrawableId(int thumbDrawableId) {
        if (thumbDrawableId != 0 && getResources() != null) {
            this.thumbDrawableId = thumbDrawableId;
            thumbBitmap = Utils.drawableToBitmap(thumbSize, getResources().getDrawable(thumbDrawableId));
        }
    }

    public int getThumbSize() {
        return thumbSize;
    }

    public void setThumbSize(int thumbSize) {
        this.thumbSize = thumbSize;
    }

    protected boolean getActivate() {
        return isActivate;
    }

    protected void setActivate(boolean activate) {
        isActivate = activate;
    }

    public void setTypeface(Typeface typeFace){
        paint.setTypeface(typeFace);
    }


}
