package com.eexposito.restaurant._support;


import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class CustomJUnitTestRunner extends AndroidJUnitRunner {

    private static final String MY_CUSTOM_APP = JUnitTestApplication.class.getCanonicalName();

    @Override
    public Application newApplication(final ClassLoader cl, final String className, final Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        return super.newApplication(cl, MY_CUSTOM_APP, context);
    }
}
