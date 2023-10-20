package ru.vsu.cs.yurov.logics;

import java.util.Stack;

public class Player {
    Stack<Piece> homePieces;
    Piece[] pieces;
    int sixCounter;
    private PlayerColor color;

    Tile[] tiles;

    public void makeMove(int number, Piece piece) {

    }

    public void addSixCounter() {
        sixCounter++;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
}
