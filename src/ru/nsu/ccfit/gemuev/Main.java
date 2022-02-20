package ru.nsu.ccfit.gemuev;

public class Main {

    public static void main(String[] args) {

        Model m = new Model(9, 9, 10);
        ConsoleController c = new ConsoleController();
        View v = new ConsoleView(m, c);

    }
}
