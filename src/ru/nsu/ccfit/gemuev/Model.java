package ru.nsu.ccfit.gemuev;


import org.jetbrains.annotations.NotNull;

public class Model extends Observable{


    private Field field;
    private boolean isGameEnd;
    private boolean isFirstMove;
    private boolean isViewClose;
    private View view;

    public final ModelClock clock;
    private boolean haveChanges;


    public void increaseClock(){
        if(!isFirstMove && !isGameEnd){
            clock.increaseClock();
        }
    }


    public int getClock(){
        return clock.getClock();
    }

    public void nullClock(){
        clock.setClock(0);
    }


    public boolean isFirstMove(){
        return isFirstMove;
    }

    public boolean isHaveChanges(){
        return haveChanges;
    }


    public void closeView(){
        if(view!=null){
            view.close();
            view = null;
        }
    }


    public void init(int fieldSizeX, int fieldSizeY, int countOfMines){
        field = new Field(fieldSizeX, fieldSizeY, countOfMines);
        isGameEnd = false;
        isFirstMove = true;
        haveChanges = true;
        nullClock();
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

        if(!isGameEnd && field.isCellClosed(x, y)) {
            isGameEnd = field.openCell(x, y, isFirstMove);
            isFirstMove = false;

            haveChanges = true;
            notifyObservers();
        }
    }


    public void changeFlag(int x, int y){

        if(!isGameEnd && field.isCellClosed(x, y) && !isFirstMove){
            field.changeFlag(x, y);

            haveChanges = true;
            notifyObservers();
        }
    }


    public Field.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }

}
