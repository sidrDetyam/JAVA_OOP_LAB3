package ru.nsu.ccfit.gemuev.controller;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.Model;
import ru.nsu.ccfit.gemuev.controller.commands.Command;
import ru.nsu.ccfit.gemuev.controller.commands.CommandFactory;


import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller{


    private @NotNull ArrayList<String> parse(@NotNull String command){

        ArrayList<String> tokens = new ArrayList<>();

        Pattern pattern = Pattern.compile("[a-zA-Z0-9.]+");
        Matcher matcher = pattern.matcher(command);

        while (matcher.find()) {
            String token = command.substring(matcher.start(), matcher.end());
            tokens.add(token);
        }

        return tokens;
    }


    public void execute(Model model, String commandStr) {

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
