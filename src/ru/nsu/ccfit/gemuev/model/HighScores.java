package ru.nsu.ccfit.gemuev.model;

import ru.nsu.ccfit.gemuev.Observable;

import java.util.Optional;

public class HighScores extends Observable {

    private HighScoreEntry[] statistics;
    private final int countOfEntries;
    private final GameModel model;

    public HighScores(GameModel model, int countOfEntries){
        statistics = null;
        this.countOfEntries = countOfEntries;
        this.model = model;
    }

    public synchronized void update(){
        var tmp = model.getClient().getTopPlayers(countOfEntries).orElse(null);
        if(tmp!=null) {
            statistics = tmp;
            notifyObservers();
        }
    }

    public synchronized Optional<HighScoreEntry[]> statistics(){
        return statistics==null? Optional.empty() : Optional.of(statistics);
    }

}
