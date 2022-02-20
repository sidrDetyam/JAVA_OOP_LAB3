package ru.nsu.ccfit.gemuev;

import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ConsoleView implements View{

    private final Model model;
    private final ConsoleController controller;


    public ConsoleView(Model model, ConsoleController controller){
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        run();
    }


    @Override
    public void run(){
        render();

        Scanner scanner = new Scanner(System.in);

        while(true){
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
                        symbol = '#';
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
    public void update(Observable o, Object arg) {
        render();
    }
}
