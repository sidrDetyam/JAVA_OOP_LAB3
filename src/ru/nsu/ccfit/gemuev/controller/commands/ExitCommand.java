package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.Model;

import java.util.List;

public class ExitCommand implements Command{
    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {

        if(!arguments.isEmpty()){
            throw new CheckedIllegalArgsException("Wrong args count");
        }

        model.closeView();
    }
}
