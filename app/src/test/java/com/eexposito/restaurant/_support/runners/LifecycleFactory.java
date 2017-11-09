package com.eexposito.restaurant._support.runners;


import android.app.Application;

import com.eexposito.restaurant._support.TestApplication;

import java.lang.reflect.Method;

import org.robolectric.DefaultTestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

public class LifecycleFactory {

    public static class CustomTestLifecycle extends DefaultTestLifecycle {

        @Override
        public Application createApplication(Method method, AndroidManifest appManifest, Config config) {

            return new TestApplication();
        }
    }
}
