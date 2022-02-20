package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import java.util.List;

public class InitGameCommand extends AbstractIntegersCommand{

    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {
        parseArguments(arguments, 3);
        model.init(args[0], args[1], args[2]);
    }
}
