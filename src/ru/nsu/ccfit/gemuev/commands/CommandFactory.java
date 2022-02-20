package ru.nsu.ccfit.gemuev.commands;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;


public class CommandFactory{

    private CommandFactory() throws IOException, ReflectiveOperationException {

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
    }


    private final HashMap<String, Constructor<?>> commandConstructors;
    private static CommandFactory INSTANCE;

    public static CommandFactory getInstance(){

        if(INSTANCE==null){
            try {
                INSTANCE = new CommandFactory();

            } catch (ReflectiveOperationException | IOException e) {
                throw new NoSuchElementException("Terminate!!!!!!!!!!", e);
            }

        }

        return INSTANCE;
    }


    public Optional<Command> getCommand(String commandName){

        var constructor = commandConstructors.get(commandName);
        if(constructor==null){
            return Optional.empty();
        }

        try{
            return Optional.of( (Command) constructor.newInstance());
        }
        catch(ReflectiveOperationException e){
            throw new NoSuchElementException("Terminate!!!!!!!!!!", e);
        }
    }
}
