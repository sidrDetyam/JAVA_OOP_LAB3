package ru.nsu.ccfit.gemuev.gui;

import ru.nsu.ccfit.gemuev.LoadPropertiesException;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.model.GameLevels;
import ru.nsu.ccfit.gemuev.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

public class MainForm extends JFrame{

    public JPanel mainPanel;
    public JPanel cellsPanel;
    public JLabel minesLeft;
    public JPanel timerPanel;

    MainForm(GameModel model, Controller controller, Image icon){

        setTitle("Minesweeper");
        setIconImage(icon);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        var thisFrame = this;
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                controller.execute(model, "close");
            }
        });

        JMenuItem highScores = new JMenuItem("High scores");
        highScores.addActionListener(e -> controller.execute(model, "show_highscores"));

        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.addActionListener(e -> controller.execute(model, "show_about_info"));

        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(e -> controller.execute(model, "close"));

        JMenu newGameMenu = new JMenu("New Game");
        JMenuItem easyItem = new JMenuItem("Easy");
        JMenuItem middleItem = new JMenuItem("Middle");
        JMenuItem hardItem = new JMenuItem("Hard");

        Optional<String> easySetting = GameLevels.getSetting("easy");
        Optional<String> middleSetting = GameLevels.getSetting("middle");
        Optional<String> hardSetting = GameLevels.getSetting("hard");
        if(easySetting.isEmpty() || middleSetting.isEmpty() || hardSetting.isEmpty()){
            throw new LoadPropertiesException("bruh");
        }

        easyItem.addActionListener(e -> controller.execute(model, easySetting.get()));
        middleItem.addActionListener(e -> controller.execute(model, middleSetting.get()));
        hardItem.addActionListener(e -> controller.execute(model, hardSetting.get()));

        newGameMenu.add(easyItem);
        newGameMenu.add(middleItem);
        newGameMenu.add(hardItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(newGameMenu);
        menuBar.add(highScores);
        menuBar.add(aboutMenu);
        menuBar.add(exitButton);

        //these two buttons only for debug
        JMenuItem winItem = new JMenuItem("Win button");
        winItem.addActionListener(e -> controller.execute(model,"win"));
        menuBar.add(winItem);

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> controller.execute(model, "clear"));
        menuBar.add(clearItem);

        setJMenuBar(menuBar);
        ClockLabel clockLabel = new ClockLabel(model);
        timerPanel.add(clockLabel, 0);
    }
}
