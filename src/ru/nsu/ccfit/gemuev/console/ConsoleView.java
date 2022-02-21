package ru.nsu.ccfit.gemuev.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.Model;
import ru.nsu.ccfit.gemuev.View;
import ru.nsu.ccfit.gemuev.controller.Controller;


import java.util.Scanner;


public class ConsoleView implements View {

    private final Model model;
    private boolean isClosed;


    public ConsoleView(@NotNull Model model, @NotNull Controller controller){
        this.model = model;
        isClosed = false;
        model.setView(this);

        render();
        Scanner scanner = new Scanner(System.in);
        while(!isClosed){
            String command = scanner.nextLine();
            controller.execute(model, command);
        }
    }

    @Override
    public void render(){

        for(int j=0; j < model.sizeY(); ++j){
            for(int i=0; i < model.sizeX(); ++i){

                char symbol = '.';
                var cell = model.cellInfo(i, j);

                if(cell.isFlag()){
                    symbol = '!';
                }

                if(cell.isOpen()){
                    if(cell.isMine()){
                        symbol = '@';
                    }
                    else{
                        symbol = (char) ((char) cell.minesAround() + '0');
                    }
                }


                System.out.print(' ');
                System.out.print(symbol);
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
