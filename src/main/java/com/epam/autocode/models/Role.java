package com.epam.autocode.models;

public enum Role {
    SYSTEM("system"),
    USER("user"),
    AI("assistant");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
