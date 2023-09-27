package com.app.trackmeuser.model;

public enum UserRoleType {

    ROLE_ADMIN("ADMIN"),
    ROLE_CUSTOMER("CUSTOMER");

    private final String value;

    UserRoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
