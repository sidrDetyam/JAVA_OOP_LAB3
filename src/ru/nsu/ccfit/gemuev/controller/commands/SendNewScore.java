package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.Model;
import java.util.List;

public class SendNewScore implements Command{
    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {

        try{
            int score = Integer.parseInt(arguments.get(1));
            Thread sendThread = new Thread(() -> model.gameServer().addEntry(arguments.get(0), score));
            sendThread.start();
        }
        catch(IndexOutOfBoundsException | NumberFormatException e){
            throw new CheckedIllegalArgsException("Something goes wrong", e);
        }
    }
}
