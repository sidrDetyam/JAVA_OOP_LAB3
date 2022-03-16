package ru.nsu.ccfit.gemuev;


import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.console.ConsoleView;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.gui.GuiView;
import ru.nsu.ccfit.gemuev.model.GameLevels;
import ru.nsu.ccfit.gemuev.model.Model;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String @NotNull [] args){

        Model model = new Model();
        Controller controller = new Controller();

        Optional<String> defaultSetting = GameLevels.getSetting("middle");
        if(defaultSetting.isEmpty()){
            throw new LoadPropertiesException("something goes wrong");
        }
        controller.execute(model, defaultSetting.get());

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(model::increaseClock, 0, 1, TimeUnit.SECONDS);

        if(args.length==1 || !args[1].equals("console")){
            View v = new GuiView(model, controller);
        }
        else{
            View v = new ConsoleView(model, controller);
            executorService.shutdown();
        }

    }
}
