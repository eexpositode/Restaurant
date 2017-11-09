package com.eexposito.restaurant._support;

import com.eexposito.restaurant._support.injections.components.TestApplicationComponent;
import com.eexposito.restaurant._support.runners.CustomTestRunner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(CustomTestRunner.class)
@Config(manifest= "app/src/main/AndroidManifest.xml")
@Ignore("No testing class")
public class BaseTest {

    protected TestApplicationComponent mTestApplicationComponent;

    @Before
    public void setUp() {
        mTestApplicationComponent = createApplicationComponent();
    }

    private TestApplicationComponent createApplicationComponent() {
        TestApplication application = (TestApplication) RuntimeEnvironment.application;
        return application.getApplicationComponent();
    }

}
