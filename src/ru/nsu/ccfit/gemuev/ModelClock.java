package ru.nsu.ccfit.gemuev;

public class ModelClock extends Observable{

    private int clock;

    public ModelClock(){
        clock = 0;
    }

    public synchronized void setClock(int clock){
        this.clock = clock;
        notifyObservers();
    }


    public synchronized void increaseClock(){
        ++clock;
        notifyObservers();
    }


    public synchronized int getClock(){
        return clock;
    }
}
