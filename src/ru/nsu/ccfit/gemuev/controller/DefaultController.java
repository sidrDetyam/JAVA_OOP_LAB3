package ru.nsu.ccfit.gemuev.controller;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.GameModel;
import ru.nsu.ccfit.gemuev.controller.commands.Command;
import ru.nsu.ccfit.gemuev.controller.commands.CommandFactory;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class DefaultController implements Controller {

    private DefaultController(){}
    private static final DefaultController INSTANCE = new DefaultController();
    public static DefaultController getInstance(){return INSTANCE;}


    private static @NotNull ArrayList<String> parse(@NotNull String command){
        return new ArrayList<>(Arrays.asList(command.split(" ")));
    }


    public void execute(@NotNull GameModel model, @NotNull String commandStr) {

        var tokens = parse(commandStr);
        if(tokens.isEmpty()){
            return;
        }

        String commandName = tokens.remove(0);
        Optional<Command> opt = CommandFactory.getCommand(commandName);

        if(opt.isEmpty()){
            throw new IllegalArgumentException("Command not found: "+commandName);
        }

        try {
            opt.get().execute(model, tokens);
        } catch (CheckedIllegalArgsException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
