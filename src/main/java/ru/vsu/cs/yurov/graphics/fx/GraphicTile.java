package ru.vsu.cs.yurov.graphics.fx;

public class GraphicTile {
    private TileForm form;
    private int index;

    private int x;
    private int y;
    private int width;
    private int height;

    public GraphicTile(TileForm form, int index, int x, int y, int width, int height) {
        this.form = form;
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public TileForm getForm() {
        return form;
    }

    public void setForm(TileForm form) {
        this.form = form;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
