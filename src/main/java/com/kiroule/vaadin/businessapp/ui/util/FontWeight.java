package com.kiroule.vaadin.businessapp.ui.util;

public enum FontWeight {

    BOLD("bold"),
    BOLDER("bolder"),
    LIGHTER("lighter"),
    NORMAL("normal"),
    _100("100"),
    _200("200"),
    _300("300"),
    _400("400"),
    _500("500"),
    _600("600"),
    _700("700"),
    _800("800"),
    _900("900");

    private String value;

    FontWeight(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

