package com.kiroule.vaadin.businessapp.it;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.parallel.ParallelTest;
import org.junit.Assert;
import org.junit.Rule;

public abstract class AbstractIT extends ParallelTest {

    public static String APP_URL= "http://localhost:8080";

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this,
            true);

    protected void assertNumbers(String expected, String actual) {
        // Remove any thousands and decimal separators before comparing
        Assert.assertEquals(stripSeparators(expected), stripSeparators(actual));
    }

    private String stripSeparators(String string) {
        return string.replaceAll("[\\., ]", "").replace((char) 160, ' ');
    }
}
