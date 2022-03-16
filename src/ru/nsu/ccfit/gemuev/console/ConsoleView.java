package ru.nsu.ccfit.gemuev.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.model.MineField;
import ru.nsu.ccfit.gemuev.model.Model;
import ru.nsu.ccfit.gemuev.View;
import ru.nsu.ccfit.gemuev.controller.Controller;

import java.util.Scanner;


public class ConsoleView implements View {

    private final Model model;
    private boolean isClosed;
    private final TextClock textClock;

    public ConsoleView(@NotNull Model model, @NotNull Controller controller){
        this.model = model;
        isClosed = false;
        model.add(this);
        textClock = new TextClock(model);

        update();
        Scanner scanner = new Scanner(System.in);
        while(!isClosed){
            String command = scanner.nextLine();

            try {
                controller.execute(model, command);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private @NotNull String textForCell(MineField.@NotNull CellInfo info){

        if(info.isOpen()){
            return info.isMine()? "@" : Integer.toString(info.minesAround());
        }

        return info.isFlag()? "!" : ".";
    }

    @Override
    public void update(){

        if(model.isViewClosed()){
            close();
            return;
        }

        System.out.println(" Time: " + textClock.getClock());
        for(int j=0; j < model.sizeY(); ++j){
            for(int i=0; i < model.sizeX(); ++i){

                var info = model.cellInfo(i, j);

                System.out.print(' ');
                System.out.print(textForCell(info));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    @Override
    public void close(){
        isClosed = true;
    }
}
