package ru.vsu.cs.yurov.logics;

public class Tile {
    private int index;
    private Piece firstPiece = null;
    private Piece secondPiece = null;
    private boolean isSafe;

    public boolean isEmpty() {
        return firstPiece == null && secondPiece == null;
    }

    public boolean isFull() {
        return firstPiece != null && secondPiece != null;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Piece getFirstPiece() {
        return firstPiece;
    }

    public void setFirstPiece(Piece firstPiece) {
        this.firstPiece = firstPiece;
    }

    public Piece getSecondPiece() {
        return secondPiece;
    }

    public void setSecondPiece(Piece secondPiece) {
        this.secondPiece = secondPiece;
    }

    public void removePiece(Piece piece) {
        if (piece == firstPiece) {
            firstPiece = secondPiece;
        }
        secondPiece = null;
    }

    public void setPiece(Piece piece) {
        if (firstPiece == null) {
            firstPiece = piece;
        } else if (secondPiece == null) {
            secondPiece = piece;
        }
    }
}
