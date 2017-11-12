package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eexposito.restaurant.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_toolbar)
public class ToolbarView extends FrameLayout {

    public interface OnToolbarActionCallback {

        void onCancelClicked();

        void onAcceptClicked();
    }

    @ViewById(R.id.toolbar_cancel)
    View mCancel;

    @ViewById(R.id.toolbar_accept)
    View mAccept;

    @ViewById(R.id.toolbar_text)
    TextView mHeadLine;

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

    public void updateTitle(@NonNull @StringRes int headlineID) {

        mHeadLine.setText(headlineID);
    }
}
