package ru.vsu.cs.yurov.logics;

import javafx.scene.paint.Color;

public enum PlayerColor {
    RED(Color.color(0.6D, 0.0D, 0.0D)),
    BLUE(Color.color(0.0D, 0.0D, 0.6D)),
    YELLOW(Color.color(0.6D, 0.6D, 0.0D)),
    GREEN(Color.color(0.0D, 0.6D, 0.0D));

    private final Color rgbColor;

    PlayerColor(Color color) {
        this.rgbColor = color;
    }

    public Color getRgbColor() {
        return rgbColor;
    }
}
