package ru.nsu.ccfit.gemuev.gui;

import ru.nsu.ccfit.gemuev.Observer;

import javax.swing.*;

public class ClockLabel extends JLabel implements Observer {

    private final GuiView guiView;

    public ClockLabel(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void update() {
        setText(Integer.toString(guiView.getClock()));
        setVisible(true);
    }
}
