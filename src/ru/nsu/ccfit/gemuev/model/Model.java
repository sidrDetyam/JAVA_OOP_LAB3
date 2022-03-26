package ru.nsu.ccfit.gemuev.model;


import ru.nsu.ccfit.gemuev.Observable;
import ru.nsu.ccfit.gemuev.Observer;


public class Model extends Observable {

    private MineField field;
    private boolean isGameEnd;
    private boolean isGameWin;
    private boolean isFirstMove;
    private volatile boolean isViewClosed;
    private final ModelClock clock;
    private final HighScores highScores;
    private final Server gameServer;

    public Server gameServer(){return gameServer;}

    public void addClockLabel(Observer label){
        clock.add(label);
    }

    public int minesLeft(){return field.countOfMines() - field.countOfFlags();}

    public int getClock(){
        return clock.getClock();
    }

    public boolean isGameEnd(){return isGameEnd;}

    public boolean isGameWin(){return isGameWin;}

    public boolean isFirstMove(){
        return isFirstMove;
    }

    public boolean isViewClosed() {return isViewClosed;}



    public void closeView(){
        isViewClosed = true;
        clock.resetClock();
        notifyObservers();
        removeAll();
    }

    @Override
    public void add(Observer observer){
        super.add(observer);
        isViewClosed = false;
    }


    public void init(int fieldSizeX, int fieldSizeY, int countOfMines){
        field = new MineField(fieldSizeX, fieldSizeY, countOfMines);
        isGameEnd = false;
        isFirstMove = true;
        clock.resetClock();
        clock.nullClock();
        notifyObservers();
    }


    public HighScores getHighScores(){return highScores;}

    public Model(String serverUrl){
        clock = new ModelClock();
        gameServer = new Server(serverUrl);
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
        notifyObservers();
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
            notifyObservers();
        }
    }


    public void changeFlag(int x, int y){

        var info = field.cellInfo(x, y);

        if(!isGameEnd && !info.isOpen() && !isFirstMove){
            if(!info.isFlag() && minesLeft()==0){
                return;
            }

            field.changeFlag(x, y);
            notifyObservers();
        }
    }


    public MineField.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }

}
