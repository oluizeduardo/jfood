package br.com.jfood.model;

public enum Role {
    COMMON_USER(1),
    ADMIN(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
