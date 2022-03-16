package ru.nsu.ccfit.gemuev.gui;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.Model;
import ru.nsu.ccfit.gemuev.Observer;

import javax.swing.*;

public class ClockLabel extends JLabel implements Observer {

    private final Model model;

    public ClockLabel(@NotNull Model model){
        this.model = model;
        setText(" Time: 0");
        model.addClockLabel(this);
    }

    @Override
    public void update() {
        setText(" Time: " + model.getClock());
        setVisible(true);
    }
}
