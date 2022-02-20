package ru.nsu.ccfit.gemuev.controller.commands;

public class CommandFactoryException extends RuntimeException{

    CommandFactoryException(String message){
        super(message);
    }

    CommandFactoryException(String message, Throwable cause){
        super(message, cause);
    }
}
