package ru.nsu.ccfit.gemuev;


import ru.nsu.ccfit.gemuev.console.ConsoleView;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.gui.GuiView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        Model model = new Model();
        Controller controller = new Controller();
        controller.execute(model, "init 18 18 40");
        View v = new GuiView(model, controller);

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(model::increaseClock, 0, 1, TimeUnit.SECONDS);

    }
}
