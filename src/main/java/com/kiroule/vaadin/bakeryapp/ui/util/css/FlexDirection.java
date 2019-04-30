package com.kiroule.vaadin.bakeryapp.ui.util.css;

public enum FlexDirection {

    COLUMN("column"), COLUMN_REVERSE("column-reverse"), ROW("row"), ROW_REVERSE(
            "row-reverse");

    private String value;

    FlexDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
