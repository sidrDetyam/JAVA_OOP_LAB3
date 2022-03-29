package ru.nsu.ccfit.gemuev;


import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.console.ConsoleView;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.controller.DefaultController;
import ru.nsu.ccfit.gemuev.gui.GuiView;
import ru.nsu.ccfit.gemuev.model.GameLevels;
import ru.nsu.ccfit.gemuev.model.GameModel;
import java.util.Optional;


public class Main {

    public static void main(String @NotNull [] args){

        GameModel model = new GameModel("https://Bot4VK.pythonanywhere.com");
        Controller controller = DefaultController.getInstance();

        Optional<String> defaultSetting = GameLevels.getSetting("middle");
        if(defaultSetting.isEmpty()){
            throw new LoadPropertiesException("something goes wrong");
        }
        controller.execute(model, defaultSetting.get());

        if(args.length!=2 || !args[1].equals("console")){
            View v = new GuiView(model, controller);
        }
        else{
            View v = new ConsoleView(model, controller);
        }
    }
}
