package com.ecom.enums;

public enum Constants {
    FIXED("FIXED"),
    PERCENTAGE("PERCENTAGE"),
    SUCCESS("Success"),
    FAILED("Failed"),
    INDIVIDUAL("INDIVIDUAL");


    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
