package com.kiroule.vaadin.businessapp.it;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.html.testbench.DivElement;

public class HomeIT extends AbstractIT {

    @Test
    public void homeView() {
        getDriver().get("http://localhost:8080");
        DivElement homeView = $(DivElement.class).id("home");
        Assert.assertNotNull(homeView);
    }

}
