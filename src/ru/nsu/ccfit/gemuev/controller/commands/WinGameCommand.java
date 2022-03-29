package ru.nsu.ccfit.gemuev.controller.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.Model;

import java.util.List;

public class WinGameCommand implements Command{

    @Override
    public void execute(@NotNull Model model, @NotNull List<String> arguments){
        model.winGame();
    }
}
