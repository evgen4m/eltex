package com.esoft.eltex.app;

public class ErrorMessageEvent {

    private static ErrorMessageEvent instance;

    public String message;

    public ErrorMessageEvent(String message) {
        this.message = message;
    }

    private ErrorMessageEvent(){

    }

    public static ErrorMessageEvent getInstance() {
        if (instance == null) {
            instance = new ErrorMessageEvent();
        }
        return instance;
    }
}
