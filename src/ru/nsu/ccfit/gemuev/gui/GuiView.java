package ru.nsu.ccfit.gemuev.gui;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import ru.nsu.ccfit.gemuev.View;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.controller.commands.CommandFactoryException;

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
        model.setView(this);
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
            throw new CommandFactoryException("Terminate!!!!!!!", e);
        }

        mainWindow = new MainForm(model, controller);
        mainWindow.setTitle("Minesweeper");
        mainWindow.setIconImage(icons.get("mine").getImage());
        mainWindow.setVisible(true);

        render();
    }


    private void init_render() {

        var layout = new GridLayout(model.sizeY(), model.sizeX(), 0, 0);

        mainWindow.secondPanel.removeAll();
        mainWindow.secondPanel.setLayout(layout);
        cells = new JButton[model.sizeX()][model.sizeY()];

        mainWindow.setSize(cellSize * model.sizeX() + 50, cellSize*model.sizeY());
        mainWindow.setResizable(false);


        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(new Dimension(30, 30));

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

        mainWindow.setVisible(true);
    }


    @Override
    public void render() {

        if(model.isFirstMove()) {
            init_render();
        }

        ///TODO
        for (int i = 0; i < model.sizeX(); ++i) {
            for (int j = 0; j < model.sizeY(); ++j) {

                var info = model.cellInfo(i, j);
                cells[i][j].setText("");

                if(info.isOpen()){
                    cells[i][j].setBorder(BorderFactory.createEtchedBorder());
                    if(info.isMine()){
                        cells[i][j].setIcon(icons.get("mine"));
                    }
                    else{
                        cells[i][j].setIcon(icons.get(Integer.toString(info.minesAround())));
                    }
                }
                else{
                    if(info.isFlag()){
                        cells[i][j].setIcon(icons.get("flag"));
                    }else{
                        cells[i][j].setIcon(icons.get("closed"));
                    }
                }
            }
        }

        mainWindow.setVisible(true);
    }

    @Override
    public void close(){}
}
