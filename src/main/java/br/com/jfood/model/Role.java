package br.com.jfood.model;

public enum Role {
    CUSTUMER(1),
    ADMIN(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role getFromValue(int value) {
        return switch (value) {
            case 1 -> CUSTUMER;
            case 2 -> ADMIN;
            default -> CUSTUMER;
        };
    }
}
