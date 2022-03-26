package ru.nsu.ccfit.gemuev.gui;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Observer;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.model.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class HighScoresForm extends JFrame implements Observer {

    private JPanel mainPanel;
    private final Model model;
    private final JTable table;

    private void tableUpdate(){

        var tableModel = (DefaultTableModel) table.getModel();
        var statistics = model.getHighScores().statistics();

        tableModel.setRowCount(0);
        if(statistics.isPresent()){
            for(var entry : statistics.get()){
                String[] row = new String[]{entry.name(), entry.score().toString()};
                tableModel.addRow(row);
            }
        }
    }


    public HighScoresForm(@NotNull Model model, Controller controller, Image icon){

        this.model = model;
        setTitle("High scores (Updating...)");
        model.getHighScores().add(this);
        setIconImage(icon);

        String[][] array = new String[][]{};
        String[] columnsHeader = new String[]{"Player", "Score"};

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        var thisFrame = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.getHighScores().remove(thisFrame);
                thisFrame.dispose();
            }
        });

        DefaultTableModel tableModel = new DefaultTableModel(array, columnsHeader);
        table = new JTable(tableModel);
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.BOLD, 14));

        Thread sendThread = new Thread(() -> model.getHighScores().update());
        sendThread.start();
        tableUpdate();

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));

        setContentPane(contents);
        setSize(500, 400);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void update() {
        setTitle("High scores");
        tableUpdate();
        setVisible(true);
    }
}
