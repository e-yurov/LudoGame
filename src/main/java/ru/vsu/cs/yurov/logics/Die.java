package ru.vsu.cs.yurov.logics;

import java.util.Random;

public class Die {
    Random random;

    public Die() {
        random = new Random();
    }

    public int getNumber() {
        return random.nextInt(1, 7);
    }
}
