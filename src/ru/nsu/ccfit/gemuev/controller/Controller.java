package ru.nsu.ccfit.gemuev.controller;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.GameModel;

public interface Controller {

    void execute(@NotNull GameModel model, @NotNull String commandStr);
}
