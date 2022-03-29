package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.GameModel;
import java.util.List;

public class InitGameCommand extends AbstractIntegersCommand{

    @Override
    public void execute(@NotNull GameModel model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {
        parseArguments(arguments, 4);
        model.init(args[0], args[1], args[2], args[3]);
    }
}
