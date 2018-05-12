package com.jaygoo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2018/5/8
 * 描    述:
 * ================================================
 */
public class Utils {

    /**
     *  make a drawable to a bitmap
     * @param drawable drawable you want convert
     * @return converted bitmap
     */
    public static Bitmap drawableToBitmap(int size, Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && bitmap.getHeight() > 0) {
                Matrix matrix = new Matrix();
                float scaleHeight = size * 1.0f / bitmapDrawable.getIntrinsicHeight();
                matrix.postScale(scaleHeight, scaleHeight);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                return bitmap;
            }
        }
        bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * draw 9Path
     *
     * @param canvas Canvas
     * @param bmp 9path bitmap
     * @param rect 9path rect
     */
    public static void drawNinePath(Canvas canvas, Bitmap bmp, Rect rect) {
        NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null || compareFloat(0f,dpValue) == 0)return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Compare the size of two floating point numbers
     * @param a
     * @param b
     * @return 1 is a > b
     * -1 is a < b
     * 0 is a == b
     */
    public static int compareFloat(float a, float b) {
        int ta = Math.round(a * 100000);
        int tb = Math.round(b * 100000);
        if (ta > tb) {
            return 1;
        } else if (ta < tb) {
            return -1;
        } else {
            return 0;
        }
    }
}
