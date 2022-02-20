package ru.nsu.ccfit.gemuev.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import java.util.List;


public class OpenCellCommand extends AbstractIntegersCommand{

    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {
        parseArguments(arguments, 2);
        model.openCell(args[0], args[1]);
    }
}
