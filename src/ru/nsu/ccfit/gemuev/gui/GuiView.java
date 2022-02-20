package ru.nsu.ccfit.gemuev.gui;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import ru.nsu.ccfit.gemuev.View;
import ru.nsu.ccfit.gemuev.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GuiView implements View {

    private final Model model;
    private final Controller controller;
    private final MainWindow mainWindow;
    private JButton[][] cells;


    public GuiView(@NotNull Model model, @NotNull Controller controller){
        this.model = model;
        this.controller = controller;
        model.setView(this);

        mainWindow = new MainWindow(model, controller);

        render();
    }


    private void init_render() {

        mainWindow.getContentPane().removeAll();
        mainWindow.setLayout(new GridLayout(model.sizeY(), model.sizeX()));
        cells = new JButton[model.sizeX()][model.sizeY()];

        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                cells[i][j] = new JButton();
                final Integer x = i;
                final Integer y = j;
                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            controller.execute(model, "open " + x + " " + y);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            controller.execute(model, "flag " + x + " " + y);
                        }
                    }
                });

                mainWindow.add(cells[i][j]);
            }
        }
    }


    @Override
    public void render() {

        if(model.isFirstMove()) {
            init_render();
        }

        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                var info = model.cellInfo(i, j);

                if(info.isOpen()){
                    if(info.isMine()){
                        cells[i][j].setText("@");
                    }
                    else{
                        cells[i][j].setText(Integer.toString(info.minesAround()));
                    }
                }
                else{
                    cells[i][j].setText(info.isFlag()? "!" : "");
                }
            }
        }

        mainWindow.setVisible(true);
    }

    @Override
    public void close(){}
}
