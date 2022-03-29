package ru.nsu.ccfit.gemuev.console;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.LoadPropertiesException;
import ru.nsu.ccfit.gemuev.controller.Controller;
import ru.nsu.ccfit.gemuev.model.GameLevels;
import ru.nsu.ccfit.gemuev.model.MineField;
import ru.nsu.ccfit.gemuev.model.GameModel;
import ru.nsu.ccfit.gemuev.View;

import java.util.Scanner;


public class ConsoleView implements View{

    private final GameModel model;
    private final Controller controller;
    private boolean isClosed;
    private final TextClock textClock;

    public ConsoleView(@NotNull GameModel model, @NotNull Controller controller){
        this.model = model;
        this.controller = controller;
        isClosed = false;
        model.setView(this);
        textClock = new TextClock(model);

        render();
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


    private void gameOver(){

        if(model.isGameWin()){

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("You win! Enter your name: ");
                String name = scanner.nextLine();

                if (!name.contains(" ")) {
                    controller.execute(model, "addscore %s %d %d".
                            formatted(name, model.getClock(), model.getLevelID()));
                    break;
                }
            }
        }
        else{
            System.out.println("\nDude you lost\n");
        }
    }


    private @NotNull String textForCell(MineField.@NotNull CellInfo info){

        if(info.isOpen()){
            return info.isMine()? "@" : Integer.toString(info.minesAround());
        }

        return info.isFlag()? "!" : ".";
    }


    @Override
    public void showHighScores() {

        System.out.println("\nName  Score  Level (Wait for a sec...)\n");
        model.getHighScores().update();
        var statistics = model.getHighScores().statistics();
        if(statistics.isPresent()){
            for(var entry : statistics.get()){
                var opt = GameLevels.getLevelNameByID(entry.levelID());
                if(opt.isEmpty()){
                    throw new LoadPropertiesException("Terminate!!!...!>>!>!>!");
                }
                System.out.printf("%s %d %s%n", entry.name(), entry.score(), opt.get());
            }
        }
        System.out.println();
    }

    @Override
    public void showAboutInfo() {
        System.out.println(model.aboutGameInfo());
    }

    @Override
    public void render() {
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

        if(model.isGameEnd()){
            gameOver();
        }
    }

    @Override
    public void close(){
        isClosed = true;
    }
}
