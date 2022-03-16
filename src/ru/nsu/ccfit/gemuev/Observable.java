package ru.nsu.ccfit.gemuev;

import java.util.ArrayList;

public class Observable {

    private final ArrayList<Observer> observers;

    public void add(Observer observer){
        observers.add(observer);
    }

    public boolean remove(Observer observer){
        return observers.remove(observer);
    }

    public void removeAll(){observers.clear();}

    public void notifyObservers(){
        for(var i : observers){
            i.update();
        }
    }

    public Observable(){
        observers = new ArrayList<>();
    }
}
