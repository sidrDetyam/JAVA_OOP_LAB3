package ru.nsu.ccfit.gemuev.model;


import ru.nsu.ccfit.gemuev.Observer;
import ru.nsu.ccfit.gemuev.View;


public class GameModel{

    private MineField field;
    private boolean isGameEnd;
    private boolean isGameWin;
    private boolean isFirstMove;
    private final ModelClock clock;
    private final HighScores highScores;
    private final Client gameServer;
    private int levelID;
    private View view;

    public int getLevelID(){return levelID;}

    public Client getClient(){return gameServer;}

    public void addClockLabel(Observer label){
        clock.add(label);
    }

    public void removeClockLabel(Observer label){clock.remove(label);}

    public int minesLeft(){return field.countOfMines() - field.countOfFlags();}

    public int getClock(){
        return clock.getClock();
    }

    public boolean isGameEnd(){return isGameEnd;}

    public boolean isGameWin(){return isGameWin;}

    public boolean isFirstMove(){
        return isFirstMove;
    }


    public void closeView(){
        clock.resetClock();
        view.close();
    }

    public void setView(View view){
        this.view = view;
    }


    public void init(int fieldSizeX, int fieldSizeY, int countOfMines, int levelID){
        field = new MineField(fieldSizeX, fieldSizeY, countOfMines);
        this.levelID = levelID;
        isGameEnd = false;
        isFirstMove = true;
        clock.resetClock();
        clock.nullClock();
        if(view!=null){
            view.render();
        }
    }


    public HighScores getHighScores(){return highScores;}

    public GameModel(String serverUrl){
        clock = new ModelClock();
        gameServer = new Client(serverUrl);
        highScores = new HighScores(this,100);
    }


    public int sizeX(){
        return field.sizeX();
    }

    public int sizeY(){
        return field.sizeY();
    }


    public void winGame(){
        isGameEnd = true;
        isGameWin = true;
        clock.resetClock();
        view.render();
    }


    public void openCell(int x, int y){

        if(!isGameEnd && !field.cellInfo(x, y).isOpen()) {
            isGameEnd = field.openCell(x, y, isFirstMove);
            if(isFirstMove){
                clock.startClock();
                isFirstMove = false;
            }
            if(isGameEnd){
                clock.resetClock();
                isGameWin = field.leftToOpen()==0;
            }
            view.render();
        }
    }


    public void changeFlag(int x, int y){

        var info = field.cellInfo(x, y);

        if(!isGameEnd && !info.isOpen() && !isFirstMove){
            if(!info.isFlag() && minesLeft()==0){
                return;
            }

            field.changeFlag(x, y);
            view.render();
        }
    }


    public MineField.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }

    public void viewShowHighScores(){view.showHighScores();}

    public void viewShowAboutInfo(){view.showAboutInfo();}

    public String aboutGameInfo(){
        return "Java OOP Lab 3 | Minesweeper | a.gemuev | 20209";
    }
}
