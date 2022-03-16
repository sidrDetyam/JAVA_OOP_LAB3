package ru.nsu.ccfit.gemuev.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Observer;
import ru.nsu.ccfit.gemuev.model.Model;


public class TextClock implements Observer {

    private final Model model;
    private int clock;

    public int getClock(){
        return clock;
    }

    public TextClock(@NotNull Model model){
        this.model = model;
        clock = 0;
        model.addClockLabel(this);
    }

    @Override
    public void update() {
        clock = model.getClock();
    }
}
