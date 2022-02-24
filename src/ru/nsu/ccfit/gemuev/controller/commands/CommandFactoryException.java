package ru.nsu.ccfit.gemuev.controller.commands;

public class CommandFactoryException extends RuntimeException{

    CommandFactoryException(String message){
        super(message);
    }

    public CommandFactoryException(String message, Throwable cause){
        super(message, cause);
    }
}
