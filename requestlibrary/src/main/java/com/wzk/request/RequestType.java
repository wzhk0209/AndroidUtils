package com.wzk.request;

public enum RequestType {


    POST(1), GET(2);

    int value;

    RequestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
