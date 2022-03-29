package ru.nsu.ccfit.gemuev.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Observer;
import ru.nsu.ccfit.gemuev.model.GameModel;


public class TextClock implements Observer {

    private final GameModel model;
    private int clock;

    public int getClock(){
        return clock;
    }

    public TextClock(@NotNull GameModel model){
        this.model = model;
        clock = 0;
        model.addClockLabel(this);
    }

    @Override
    public void update() {
        clock = model.getClock();
    }
}
