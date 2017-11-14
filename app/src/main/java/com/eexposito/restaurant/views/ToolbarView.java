package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.eexposito.restaurant.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

@EViewGroup(R.layout.view_toolbar)
public class ToolbarView extends FrameLayout {

    public interface OnToolbarActionCallback {

        void onCancelClicked();

        void onAcceptClicked();
    }

    private OnToolbarActionCallback mCallback;

    public ToolbarView(@NonNull final Context context) {

        super(context);
    }

    public ToolbarView(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public ToolbarView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    public ToolbarView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(@NonNull final OnToolbarActionCallback callback) {

        mCallback = callback;
    }

    @Click(R.id.toolbar_accept)
    public void onAcceptClicked() {

        if (mCallback != null) {
            mCallback.onAcceptClicked();
        }
    }

    @Click(R.id.toolbar_cancel)
    public void onCancelClicked() {

        if (mCallback != null) {
            mCallback.onCancelClicked();
        }
    }
}
