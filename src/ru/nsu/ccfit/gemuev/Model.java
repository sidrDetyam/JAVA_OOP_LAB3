package ru.nsu.ccfit.gemuev;


import org.jetbrains.annotations.NotNull;

public class Model{


    private Field field;
    private boolean isGameEnd;
    private boolean isFirstMove;
    private boolean isViewClose;
    private View view;


    public boolean isFirstMove(){
        return isFirstMove;
    }


    public void setView(@NotNull View view) {
        this.view = view;
    }


    private void updateView(){
        if(view!=null){
            view.render();
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
            updateView();
        }
    }


    public void changeFlag(int x, int y){

        if(!isGameEnd && field.isCellClosed(x, y) && !isFirstMove){
            field.changeFlag(x, y);
            updateView();
        }
    }


    public Field.CellInfo cellInfo(int x, int y){
        return field.cellInfo(x, y);
    }


}
