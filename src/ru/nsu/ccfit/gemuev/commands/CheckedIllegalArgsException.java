package ru.nsu.ccfit.gemuev.commands;

public class CheckedIllegalArgsException extends Exception{

    CheckedIllegalArgsException(String message){
        super(message);
    }

    CheckedIllegalArgsException(String message, Throwable cause){
        super(message, cause);
    }
}