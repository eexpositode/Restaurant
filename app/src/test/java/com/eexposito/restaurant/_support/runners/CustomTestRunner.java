package com.eexposito.restaurant._support.runners;

import org.junit.runners.model.InitializationError;
import org.robolectric.annotation.Config;

public class CustomTestRunner extends BaseTestRunner<LifecycleFactory.CustomTestLifecycle> {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public CustomTestRunner(final Class<?> testClass) throws InitializationError {

        super(testClass);
        setTestLifecycleClass(LifecycleFactory.CustomTestLifecycle.class);
    }
}
