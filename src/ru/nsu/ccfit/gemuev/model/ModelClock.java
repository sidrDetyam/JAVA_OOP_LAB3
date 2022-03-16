package ru.nsu.ccfit.gemuev.model;

import ru.nsu.ccfit.gemuev.Observable;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

public class ModelClock extends Observable {

    private volatile int clock;
    private volatile boolean isClockRun;

    public ModelClock(){}

    public boolean isClockRun(){
        return isClockRun;
    }

    public void resetClock(){
        clock = 0;
        isClockRun = false;
        notifyObservers();
    }

    public void startClock(){

        if(isClockRun){
            return;
        }

        isClockRun = true;
        Thread timeThread = new Thread(() -> {

            long startTime = Clock.systemUTC().millis();
            while(isClockRun){

                clock = (int)(Clock.systemUTC().millis() - startTime)/1000;
                notifyObservers();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }

    public int getClock(){
        return clock;
    }
}
