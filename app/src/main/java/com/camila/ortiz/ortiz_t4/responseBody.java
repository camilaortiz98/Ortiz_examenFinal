package com.camila.ortiz.ortiz_t4;

public class responseBody {

    boolean success;
    int status;

    data data;

    public responseBody() {
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public com.camila.ortiz.ortiz_t4.data getData() {
        return data;
    }
}
