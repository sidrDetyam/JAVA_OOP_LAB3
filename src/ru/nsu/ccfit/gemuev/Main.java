package ru.nsu.ccfit.gemuev;


import ru.nsu.ccfit.gemuev.console.ConsoleView;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.gui.GuiView;


public class Main {

    public static void main(String[] args) {

        Model model = new Model();
        Controller controller = new Controller();
        controller.execute(model, "init 18 18 40");

        View v = new GuiView(model, controller);
    }
}
