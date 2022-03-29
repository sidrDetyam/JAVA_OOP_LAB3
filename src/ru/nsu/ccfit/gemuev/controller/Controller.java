package ru.nsu.ccfit.gemuev.controller;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.Model;

public interface Controller {

    void execute(@NotNull Model model, @NotNull String commandStr);
}
