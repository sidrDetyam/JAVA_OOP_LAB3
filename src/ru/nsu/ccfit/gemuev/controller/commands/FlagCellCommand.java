package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.controller.CheckedIllegalArgsException;
import ru.nsu.ccfit.gemuev.model.GameModel;
import java.util.List;

public class FlagCellCommand extends AbstractIntegersCommand{

    @Override
    public void execute(@NotNull GameModel model, @NotNull List<String> arguments) throws CheckedIllegalArgsException {
        parseArguments(arguments, 2);

        if(args[0]<0 || model.sizeX()<=args[0] || args[1]<0 || model.sizeY()<=args[1]){
            throw new CheckedIllegalArgsException("Incorrect index");
        }

        model.changeFlag(args[0], args[1]);
    }
}
