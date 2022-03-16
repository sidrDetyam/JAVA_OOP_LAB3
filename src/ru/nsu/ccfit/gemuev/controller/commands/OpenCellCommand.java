package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.Model;
import java.util.List;

public class OpenCellCommand extends AbstractIntegersCommand{

    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {
        parseArguments(arguments, 2);
        model.openCell(args[0], args[1]);
    }
}
