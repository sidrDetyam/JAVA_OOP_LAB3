package ru.nsu.ccfit.gemuev.controller;

import ru.nsu.ccfit.gemuev.Model;
import ru.nsu.ccfit.gemuev.controller.commands.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.controller.commands.Command;
import ru.nsu.ccfit.gemuev.controller.commands.CommandFactory;


import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller{


    private ArrayList<String> parse(String command){

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
        Optional<Command> opt = CommandFactory.getInstance().getCommand(commandName);

        if(opt.isEmpty()){
            System.out.println("Command not found");
            return;
        }

        try {
            opt.get().execute(model, tokens);
        } catch (CheckedIllegalArgsException e) {
            System.out.println("Wrong arguments");
        }

    }

}
