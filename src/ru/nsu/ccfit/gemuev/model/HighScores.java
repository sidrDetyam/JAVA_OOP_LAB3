package ru.nsu.ccfit.gemuev.model;

import ru.nsu.ccfit.gemuev.Observable;

import java.util.Optional;

public class HighScores extends Observable {

    private HighScoreEntry[] statistics;
    private final int countOfEntries;
    private final Model model;

    public HighScores(Model model, int countOfEntries){
        statistics = null;
        this.countOfEntries = countOfEntries;
        this.model = model;
    }

    public void update(){
        statistics = model.gameServer().getTopPlayers(countOfEntries).orElse(null);
        notifyObservers();
    }

    public Optional<HighScoreEntry[]> statistics(){
        return statistics==null? Optional.empty() : Optional.of(statistics);
    }

}
