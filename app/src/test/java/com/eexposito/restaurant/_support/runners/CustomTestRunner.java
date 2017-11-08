package com.eexposito.restaurant._support.runners;


import android.app.Application;
import android.support.annotation.NonNull;

import com.eexposito.restaurant._support.TestApplication;

import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

public class CustomTestRunner extends RobolectricTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public CustomTestRunner(Class<?> testClass) throws InitializationError {

        super(testClass);
    }

    @NonNull
    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {

        return MyTestLifecycle.class;
    }

    public static class MyTestLifecycle extends DefaultTestLifecycle {

        @Override
        public Application createApplication(Method method, AndroidManifest appManifest, Config config) {

            return new TestApplication();
        }
    }
}
