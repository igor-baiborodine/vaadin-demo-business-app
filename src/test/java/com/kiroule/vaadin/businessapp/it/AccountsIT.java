package com.kiroule.vaadin.businessapp.it;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.testbench.TestBenchElement;
import org.junit.Test;

public class AccountsIT extends AbstractIT {

    @Test
    public void accountDetailsCorrect() {
        getDriver().get("http://localhost:8080/accounts");
        GridElement accountsGrid = $(GridElement.class).id("accounts");

        GridTHTDElement availability = accountsGrid.getCell(0, 3);
        String gridViewAvailability = availability.getText();

        accountsGrid.getCell(0, 0).click();

        TestBenchElement availabilityDetails = $(TestBenchElement.class)
                .id("availability").$("label").attributeContains("class", "h2")
                .first();
        assertNumbers(gridViewAvailability, availabilityDetails.getText());
    }

}
