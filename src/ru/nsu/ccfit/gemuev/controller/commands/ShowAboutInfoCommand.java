package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.GameModel;

import java.util.List;

public class ShowAboutInfoCommand implements Command{

    @Override
    public void execute(@NotNull GameModel model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {

        if(!arguments.isEmpty()){
            throw new CheckedIllegalArgsException("This command expects 0 arguments");
        }

        model.viewShowAboutInfo();
    }
}
