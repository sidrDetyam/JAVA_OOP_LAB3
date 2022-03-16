package ru.nsu.ccfit.gemuev.controller;

public class CheckedIllegalArgsException extends Exception{

    public CheckedIllegalArgsException(String message){
        super(message);
    }

    public CheckedIllegalArgsException(String message, Throwable cause){
        super(message, cause);
    }
}