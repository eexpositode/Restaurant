package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ResizableLayout extends FrameLayout {

    public static final double DIALOG_WIDTH_FACTOR = 0.75;
    public static final double DIALOG_HEIGHT_FACTOR = 0.85;

    public ResizableLayout(@NonNull final Context context) {

        super(context);
    }

    public ResizableLayout(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public ResizableLayout(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public ResizableLayout(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int parentHeight = (int) (MeasureSpec.getSize(heightMeasureSpec) * DIALOG_HEIGHT_FACTOR);
        int parentWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * DIALOG_WIDTH_FACTOR);
        super.onMeasure(MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY));
    }
}
