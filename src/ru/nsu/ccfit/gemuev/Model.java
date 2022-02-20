package ru.nsu.ccfit.gemuev;

import java.util.Observable;


public class Model extends Observable {


    private Field field;
    private boolean isGameEnd;


    public void init(int fieldSizeX, int fieldSizeY, int countOfMines){
        field = new Field(fieldSizeX, fieldSizeY, countOfMines);
        isGameEnd = false;

        setChanged();
        notifyObservers();
    }



    public Model(int fieldSizeX, int fieldSizeY, int countOfMines){
        init(fieldSizeX, fieldSizeY, countOfMines);
    }


    public int sizeX(){
        return field.sizeX();
    }


    public int sizeY(){
        return field.sizeY();
    }


    public void openCell(int x, int y){

        if(!isGameEnd) {
            isGameEnd = field.openCell(x, y);
            setChanged();
            notifyObservers();
        }
    }

    public void changeFlag(int x, int y){

        if(!isGameEnd && !field.isCellOpened(x, y)){
            field.changeFlag(x, y);
            setChanged();
            notifyObservers();
        }
    }


    public Field.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }


}
