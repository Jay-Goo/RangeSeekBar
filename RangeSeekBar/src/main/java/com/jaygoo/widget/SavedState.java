package com.jaygoo.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2018/5/8
 * 描    述:
 * ================================================
 */
public class SavedState extends View.BaseSavedState {
    public float minValue;
    public float maxValue;
    public float rangeInterval;
    public int tickNumber;
    public float currSelectedMin;
    public float currSelectedMax;

    public SavedState(Parcelable superState) {
        super(superState);
    }

    private SavedState(Parcel in) {
        super(in);
        minValue = in.readFloat();
        maxValue = in.readFloat();
        rangeInterval = in.readFloat();
        tickNumber = in.readInt();
        currSelectedMin = in.readFloat();
        currSelectedMax = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeFloat(minValue);
        out.writeFloat(maxValue);
        out.writeFloat(rangeInterval);
        out.writeInt(tickNumber);
        out.writeFloat(currSelectedMin);
        out.writeFloat(currSelectedMax);
    }

    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
}
