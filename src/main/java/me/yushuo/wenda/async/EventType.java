package me.yushuo.wenda.async;

public enum  EventType {
    LIKE(0),
    COMMENT(0),
    LOGIN(0),
    MAIL(0);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
