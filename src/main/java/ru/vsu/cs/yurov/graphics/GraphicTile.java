package ru.vsu.cs.yurov.graphics;

public class GraphicTile {
    private final TileForm form;

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public GraphicTile(TileForm form, int x, int y, int width, int height) {
        this.form = form;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public TileForm getForm() {
        return form;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
