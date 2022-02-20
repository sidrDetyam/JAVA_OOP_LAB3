package ru.nsu.ccfit.gemuev;

import java.util.Observer;

public interface View extends Observer {

    void run();
    void render();
}
