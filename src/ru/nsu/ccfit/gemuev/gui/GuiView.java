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

    private final ImageIcon baseMine;
    private ImageIcon scaledMine;
    private final ImageIcon[] baseDigits;
    private ImageIcon[] scaledDigits;
    private final ImageIcon baseClosed;
    private ImageIcon scaledClosed;
    private final ImageIcon baseFlag;
    private ImageIcon scaledFlag;


    public GuiView(@NotNull Model model, @NotNull Controller controller){
        this.model = model;
        this.controller = controller;
        model.setView(this);

        String recoursesPath = "src/ru/nsu/ccfit/gemuev/recourses/";
        baseMine = new ImageIcon(recoursesPath + "mine.png");
        baseDigits = new ImageIcon[9];
        for(int i=0; i<9; ++i){
            baseDigits[i] = new ImageIcon(recoursesPath + i + ".png");
        }
        baseClosed = new ImageIcon(recoursesPath + "closed.png");
        baseFlag = new ImageIcon(recoursesPath + "flag.png");


        mainWindow = new MainWindow(model, controller);
        mainWindow.setTitle("Minesweeper");
        mainWindow.setIconImage(baseMine.getImage());


        render();
    }


    private void init_render() {

        mainWindow.getContentPane().removeAll();
        var layout = new GridLayout(model.sizeY(), model.sizeX(), 0, 0);
        mainWindow.setLayout(layout);
        mainWindow.setBackground(Color.GREEN);
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

        mainWindow.setSize(30 * model.sizeX() + 60, 30*model.sizeY() + 45);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);


        scaledMine = new ImageIcon(baseMine.getImage().
                getScaledInstance(cells[0][0].getWidth(), cells[0][0].getHeight(), Image.SCALE_SMOOTH));

        scaledClosed = new ImageIcon(baseClosed.getImage().
                getScaledInstance(cells[0][0].getWidth(), cells[0][0].getHeight(), Image.SCALE_SMOOTH));

        scaledFlag = new ImageIcon(baseFlag.getImage().
                getScaledInstance(cells[0][0].getWidth(), cells[0][0].getHeight(), Image.SCALE_SMOOTH));

        scaledDigits = new ImageIcon[9];
        for(int i=0; i<9; ++i){
            scaledDigits[i] = new ImageIcon(baseDigits[i].getImage().
                    getScaledInstance(cells[0][0].getWidth(), cells[0][0].getHeight(), Image.SCALE_SMOOTH));
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
                cells[i][j].setText("");

                if(info.isOpen()){
                    cells[i][j].setBorder(BorderFactory.createEtchedBorder());
                    if(info.isMine()){
                        cells[i][j].setIcon(scaledMine);
                    }
                    else{
                        cells[i][j].setIcon(scaledDigits[info.minesAround()]);
                    }
                }
                else{
                    if(info.isFlag()){
                        cells[i][j].setIcon(scaledFlag);
                    }else{
                        cells[i][j].setIcon(scaledClosed);
                    }
                }
            }
        }

        mainWindow.setVisible(true);
    }

    @Override
    public void close(){}
}
