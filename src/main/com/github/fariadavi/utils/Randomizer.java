package main.com.github.fariadavi.utils;

import java.util.Random;

public class Randomizer {

    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
