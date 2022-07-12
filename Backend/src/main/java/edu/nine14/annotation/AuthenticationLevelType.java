package edu.nine14.annotation;

public enum AuthenticationLevelType {
    NONE,
    USER,
    ADMIN;

    public static AuthenticationLevelType fromString(String str) {
        for (AuthenticationLevelType type : AuthenticationLevelType.values()) {
            if (type.name().substring(0, str.length()).toUpperCase().equals(str.toUpperCase())) {
                return type;
            }
        }
        return null;
    }
}
