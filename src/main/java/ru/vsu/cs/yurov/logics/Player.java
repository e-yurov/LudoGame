package ru.vsu.cs.yurov.logics;

import javafx.scene.text.Text;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class Player {
    Piece[] pieces;
    int sixCounter = 0;
    private PlayerColor color;

    Tile[] tiles;
    private Piece lastPiece;

    private int finishedPiecesCounter = 0;
    private Text textFinishedPiecesCounter;

    public boolean canMove() {
        boolean result = false;
        for (Piece piece: pieces) {
            result |= piece.canMove();
        }

        return result;
    }

    public boolean isAllFinished() {
        boolean result = true;
        for (Piece piece: pieces) {
            result &= piece.hasFinished();
        }

        return result;
    }

    public void addFinishedPiecesCounter() {
        finishedPiecesCounter++;
        textFinishedPiecesCounter.setText(String.valueOf(finishedPiecesCounter));
    }

    public void addSixCounter() {
        sixCounter++;
    }

    public boolean isAllOut() {
        boolean result = true;
        for (Piece piece: pieces) {
            result &= piece.getHomeState() == HomeState.OUT;
        }

        return result;
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

    public int getFinishedPiecesCounter() {
        return finishedPiecesCounter;
    }

    public Text getTextFinishedPiecesCounter() {
        return textFinishedPiecesCounter;
    }

    public void setTextFinishedPiecesCounter(Text textFinishedPiecesCounter) {
        this.textFinishedPiecesCounter = textFinishedPiecesCounter;
    }
}
