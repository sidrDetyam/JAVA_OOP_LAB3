package ru.nsu.ccfit.gemuev.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import java.util.List;

public interface Command {

    void execute(@NotNull Model model, @NotNull List<String> arguments)
            throws CheckedIllegalArgsException;
}
