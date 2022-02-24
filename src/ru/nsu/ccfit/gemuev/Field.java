package ru.nsu.ccfit.gemuev;

import java.util.Random;


public class Field {

    public int sizeX() {
        return sizeX;
    }

    public int sizeY() {
        return sizeY;
    }

    private static class Cell{
        private boolean isMine;
        private boolean isFlag;
        private boolean isOpen;
        private byte minesAround;
    }


    public record CellInfo(boolean isMine,
                           boolean isFlag,
                           boolean isOpen,
                           byte minesAround){}



    private final Cell[][] cells;
    private final int sizeX, sizeY;
    private final int countOfMines;


    private boolean isInBounds(int x, int y){
        return 0<=x && x<sizeX && 0<=y && y<sizeY;
    }

    private boolean isNeighbours(int x1, int y1, int x2, int y2){
        return Math.abs(x1-x2)<2 && Math.abs(y1-y2)<2;
    }

    private void actionWithCells(TripleConsumer<Cell, Integer, Integer> action){

        for(int i=0; i<sizeX; ++i){
            for(int j=0; j<sizeY; ++j){
                action.accept(cells[i][j], i, j);
            }
        }
    }


    private void placeMines(int x, int y){

        Random generator = new Random();
        for(int i=0; i<countOfMines; ){
            int x1 = generator.nextInt(sizeX);
            int y1 = generator.nextInt(sizeY);

            if(!cells[x1][y1].isMine && !isNeighbours(x, y, x1, y1)){
                cells[x1][y1].isMine = true;
                ++i;
            }
        }
    }


    private void firstMove(int x, int y){

        placeMines(x, y);

        actionWithCells((cell, x1, y1) -> {

            for(int i=-1; i<2; ++i){
                for(int j=-1; j<2; ++j){
                    if(isInBounds(x1+i, y1+j) && cells[x1+i][y1+j].isMine){
                        ++cell.minesAround;
                    }
                }
            }

        });

    }

    public Field(int sizeX, int sizeY, int countOfMines){

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.countOfMines = countOfMines;
        cells = new Cell[sizeX][sizeY];
        for(int i=0; i<sizeX; ++i){
            for(int j=0;  j<sizeY; ++j){
                cells[i][j] = new Cell();
                cells[i][j].isMine = cells[i][j].isFlag = cells[i][j].isOpen = false;
                cells[i][j].minesAround = 0;
            }
        }
    }


    public boolean openCell(int x, int y, boolean isFirstMove){

        if(isFirstMove){
            firstMove(x, y);
        }

        if(cells[x][y].isFlag || cells[x][y].isOpen){
            return false;
        }

        if(cells[x][y].isMine) {
            actionWithCells((cell, i, j) -> {
                cell.isFlag = false;
                if(cell.isMine){
                    cell.isOpen = true;
                }
            });

            return true;
        }

        if(cells[x][y].minesAround==0){
            dfs_open(x, y);
        }

        cells[x][y].isOpen = true;
        return false;
    }

    public boolean isCellClosed(int x, int y){
        return !cells[x][y].isOpen;
    }

    public byte minesAround(int x, int y){
        return cells[x][y].isOpen? cells[x][y].minesAround : -1;
    }


    public void changeFlag(int x, int y){
        cells[x][y].isFlag = !cells[x][y].isFlag;
    }


    public boolean isFlag(int x, int y){
        return cells[x][y].isFlag;
    }



    private void dfs_open(int x, int y){

        if(!isInBounds(x, y) || cells[x][y].isOpen || cells[x][y].isFlag){
            return;
        }

        cells[x][y].isOpen = true;
        if(cells[x][y].minesAround == 0) {
            for(int i=-1; i<2; ++i){
                for(int j=-1; j<2; ++j){
                    dfs_open(x+i, y+j);
                }
            }
        }
    }


    public CellInfo cellInfo(int x, int y){
        return new CellInfo(
                cells[x][y].isOpen && cells[x][y].isMine,
                cells[x][y].isFlag,
                cells[x][y].isOpen,
                cells[x][y].isOpen? cells[x][y].minesAround : -1
        );
    }

}
