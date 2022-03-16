package ru.nsu.ccfit.gemuev.gui;

import ru.nsu.ccfit.gemuev.LoadPropertiesException;
import ru.nsu.ccfit.gemuev.model.GameLevels;
import ru.nsu.ccfit.gemuev.model.Model;
import ru.nsu.ccfit.gemuev.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainForm extends JFrame{

    public JPanel mainPanel;
    public JPanel secondPanel;


    MainForm(Model model, Controller controller, Image icon){

        setTitle("Minesweeper");
        setIconImage(icon);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("New Game");
        JMenu highScoresMenu = new JMenu("High scores");
        JMenuItem aboutMenu = new JMenuItem("About");

        aboutMenu.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Lab 3", "About",
                JOptionPane.PLAIN_MESSAGE));

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

        menuBar.add(newGameMenu);
        menuBar.add(highScoresMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

        ClockLabel clockLabel = new ClockLabel(model);
        mainPanel.add(clockLabel, 0);
    }
}
