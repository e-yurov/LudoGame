package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;

public class Player {
    //Stack<Piece> homePieces;
    Piece[] pieces;
    int sixCounter;
    private PlayerColor color;

    Tile[] tiles;
    private Piece lastPiece;

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

    public int getSixCounter() {
        return sixCounter;
    }

    public void setSixCounter(int sixCounter) {
        this.sixCounter = sixCounter;
    }

    public Piece getLastPiece() {
        return lastPiece;
    }

    public void setLastPiece(Piece lastPiece) {
        this.lastPiece = lastPiece;
    }
}
