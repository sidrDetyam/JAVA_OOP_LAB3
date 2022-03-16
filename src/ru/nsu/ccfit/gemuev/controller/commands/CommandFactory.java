package ru.nsu.ccfit.gemuev.controller.commands;

import ru.nsu.ccfit.gemuev.LoadPropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;


public class CommandFactory{

    private CommandFactory(){

        try(InputStream inputStream = CommandFactory.class
                .getResourceAsStream("controller_commands.properties")) {

            commandConstructors = new HashMap<>();
            var commandNames = new Properties();
            commandNames.load(inputStream);

            for (var i : commandNames.entrySet()) {
                Class<?> commandClass = Class.forName((String) i.getValue());
                Constructor<?> commandConstructor = commandClass.getConstructor();

                commandConstructors.put((String) i.getKey(), commandConstructor);
            }
        }
        catch (IOException | ReflectiveOperationException e){
            throw new LoadPropertiesException("Terminate!!!!!!!", e);
        }
    }


    private final HashMap<String, Constructor<?>> commandConstructors;
    private static final CommandFactory INSTANCE = new CommandFactory();


    public static Optional<Command> getCommand(String commandName){

        var constructor = INSTANCE.commandConstructors.get(commandName);
        if(constructor==null){
            return Optional.empty();
        }

        try{
            return Optional.of( (Command) constructor.newInstance());
        }
        catch(ReflectiveOperationException e){
            throw new LoadPropertiesException("Terminate!!!!!!!!!!", e);
        }
    }
}
