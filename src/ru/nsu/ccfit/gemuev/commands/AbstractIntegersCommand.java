package ru.nsu.ccfit.gemuev.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractIntegersCommand implements Command{

    protected int[] args;

    protected void parseArguments(@NotNull List<String> arguments, int count) throws CheckedIllegalArgsException {

        if(arguments.size()!=count){
            throw new CheckedIllegalArgsException("Wrong count of arguments for this command" + arguments.size());
        }

        try{
            args = new int[count];

            for(int i=0; i<count; ++i){
                args[i] = Integer.parseInt(arguments.get(i));
            }
        }
        catch(NumberFormatException e){
            throw new CheckedIllegalArgsException("Something wrong with arguments", e);
        }
    }

}
