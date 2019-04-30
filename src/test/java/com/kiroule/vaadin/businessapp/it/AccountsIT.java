package com.kiroule.vaadin.businessapp.it;

import org.junit.Test;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.testbench.TestBenchElement;

public class AccountsIT extends AbstractIT {

    private static final String NAVARRO_AVAILABILITY = "64 344,98";

    @Test
    public void accountDetailsCorrect() {
        getDriver().get("http://localhost:8080/accounts");
        GridElement accountsGrid = $(GridElement.class).id("accounts");

        GridTHTDElement availability = accountsGrid.getCell(0, 3);
        assertNumbers(NAVARRO_AVAILABILITY, availability.getText());

        accountsGrid.getCell(0, 0).click();

        TestBenchElement availabilityDetails = $(TestBenchElement.class)
                .id("availability").$("label").attributeContains("class", "h2")
                .first();
        assertNumbers(NAVARRO_AVAILABILITY, availabilityDetails.getText());
    }

}
