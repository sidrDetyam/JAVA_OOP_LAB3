package ru.nsu.ccfit.gemuev.gui;

import ru.nsu.ccfit.gemuev.Model;
import ru.nsu.ccfit.gemuev.controller.Controller;

import javax.swing.*;


public class MainWindow extends JFrame{

    MainWindow(Model model, Controller controller){
        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("New Game");
        JMenu highScoresMenu = new JMenu("High scores");
        JMenu aboutMenu = new JMenu("About");

        JMenuItem easyItem = new JMenuItem("Easy");
        JMenuItem middleItem = new JMenuItem("Middle");
        JMenuItem hardItem = new JMenuItem("Hard");

        easyItem.addActionListener(e -> controller.execute(model, "init 10 10 12"));
        middleItem.addActionListener(e -> controller.execute(model, "init 18  18 40"));
        hardItem.addActionListener(e -> controller.execute(model, "init 24 24 99"));

        newGameMenu.add(easyItem);
        newGameMenu.add(middleItem);
        newGameMenu.add(hardItem);

        menuBar.add(newGameMenu);
        menuBar.add(highScoresMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

}
