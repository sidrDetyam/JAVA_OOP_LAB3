package ru.nsu.ccfit.gemuev;


import org.jetbrains.annotations.NotNull;

public class Model{


    private Field field;
    private boolean isGameEnd;
    private boolean isFirstMove;
    private boolean isViewClose;
    private View view;

    private int clocks;
    private boolean haveChanges;


    public boolean isFirstMove(){
        return isFirstMove;
    }

    public boolean isHaveChanges(){
        return haveChanges;
    }



    public void setView(@NotNull View view) {
        this.view = view;
    }


    public void updateView() {
        if (view != null) {
            view.render();
            haveChanges = false;
        }
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
        clocks = 0;
        updateView();
    }


    public Model(){}


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
            updateView();
        }
    }


    public void changeFlag(int x, int y){

        if(!isGameEnd && field.isCellClosed(x, y) && !isFirstMove){
            field.changeFlag(x, y);

            haveChanges = true;
            updateView();
        }
    }


    public Field.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }


}
