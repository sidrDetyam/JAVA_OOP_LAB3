package ru.nsu.ccfit.gemuev.model;


import ru.nsu.ccfit.gemuev.Observable;
import ru.nsu.ccfit.gemuev.Observer;

public class Model extends Observable {

    private MineField field;
    private boolean isGameEnd;
    private boolean isFirstMove;
    private volatile boolean isViewClosed;
    private final ModelClock clock;


    public void addClockLabel(Observer label){
        clock.add(label);
    }

    public int getClock(){
        return clock.getClock();
    }

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
        notifyObservers();
    }


    public Model(){
        clock = new ModelClock();
    }


    public int sizeX(){
        return field.sizeX();
    }


    public int sizeY(){
        return field.sizeY();
    }


    public void openCell(int x, int y){

        if(!isGameEnd && !field.cellInfo(x, y).isOpen()) {
            isGameEnd = field.openCell(x, y, isFirstMove);
            if(isGameEnd){
                clock.resetClock();
            }
            if(isFirstMove){
                clock.startClock();
                isFirstMove = false;
            }
            notifyObservers();
        }
    }


    public void changeFlag(int x, int y){

        if(!isGameEnd && !field.cellInfo(x, y).isOpen() && !isFirstMove){
            field.changeFlag(x, y);
            notifyObservers();
        }
    }


    public MineField.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }

}
