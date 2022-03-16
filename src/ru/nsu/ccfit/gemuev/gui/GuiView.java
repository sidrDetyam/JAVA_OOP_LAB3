package ru.nsu.ccfit.gemuev.gui;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.MineField;
import ru.nsu.ccfit.gemuev.model.Model;
import ru.nsu.ccfit.gemuev.View;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.LoadPropertiesException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class GuiView implements View {

    private final Model model;
    private final Controller controller;
    private final MainForm mainWindow;
    private JButton[][] cells;


    private final int cellSize = 30;
    private final HashMap<String, ImageIcon> icons;



    public GuiView(@NotNull Model model, @NotNull Controller controller){
        this.model = model;
        this.controller = controller;
        model.add(this);
        icons = new HashMap<>();

        try(InputStream inputStream = GuiView.class
                .getResourceAsStream("recourses.properties")) {

            Properties recourses = new Properties();
            recourses.load(inputStream);

            for (var i : recourses.entrySet()) {
                ImageIcon icon = new ImageIcon((String)i.getValue());

                ImageIcon scaledIcon = new ImageIcon(icon.getImage().
                        getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

                icons.put((String) i.getKey(), scaledIcon);
            }
        }
        catch (IOException e){
            throw new LoadPropertiesException("Terminate!!!!!!!", e);
        }

        mainWindow = new MainForm(model, controller, icons.get("mine").getImage());
        update();
    }


    private void init_render() {

        var layout = new GridLayout(model.sizeY(), model.sizeX(), 0, 0);

        mainWindow.secondPanel.removeAll();
        mainWindow.secondPanel.setLayout(layout);
        cells = new JButton[model.sizeX()][model.sizeY()];

        mainWindow.setSize(cellSize * model.sizeX(), cellSize*model.sizeY()+50);
        mainWindow.setResizable(false);


        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(new Dimension(30, 30));
                cells[i][j].setText("");

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

                mainWindow.secondPanel.add(cells[i][j]);
            }
        }
    }


    private ImageIcon iconForCell(MineField.@NotNull CellInfo info){

        if(info.isOpen()){
            return icons.get(info.isMine()? "mine" : Integer.toString(info.minesAround()));
        }

        return icons.get(info.isFlag()? "flag" : "closed");
    }


    @Override
    public void update() {

        if(model.isViewClosed()){
            close();
            return;
        }

        if(model.isFirstMove()) {
            init_render();
        }

        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                var info = model.cellInfo(i, j);
                if(info.isOpen()){
                    cells[i][j].setBorder(BorderFactory.createEtchedBorder());
                }

                cells[i][j].setIcon(iconForCell(info));
            }
        }

        mainWindow.setVisible(true);
    }


    @Override
    public void close(){}
}