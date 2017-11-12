package com.eexposito.restaurant.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.eexposito.restaurant.R;

import org.androidannotations.annotations.EViewGroup;

@EViewGroup(R.layout.view_customer_item)
public class CustomerListItemView extends FrameLayout {

    public CustomerListItemView(@NonNull final Context context) {

        super(context);
    }

    public CustomerListItemView(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public CustomerListItemView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public CustomerListItemView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
