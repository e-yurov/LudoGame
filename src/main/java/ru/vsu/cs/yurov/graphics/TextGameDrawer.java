package ru.vsu.cs.yurov.graphics;

import ru.vsu.cs.yurov.logics.Game;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Tile;

import java.io.PrintStream;

public class TextGameDrawer {
    private final Game game;
    private final PrintStream out;

    public TextGameDrawer(Game game, PrintStream out) {
        this.game = game;
        this.out = out;
    }

    public void drawGame() {
        Tile[] normalTiles = game.getNormalTiles();
        for (int i = 0; i < normalTiles.length; i++) {
            printTile(normalTiles[i], i + 1);
            if ((i + 1) % 20 == 0) {
                out.println();
            }
        }

        out.println("\nRed tiles:");
        Tile[] redTiles = game.getRedTiles();
        for (int i = 0; i < redTiles.length; i++) {
            printTile(redTiles[i], i + 1);
        }
        out.println("\nBlue tiles:");
        Tile[] blueTiles = game.getBlueTiles();
        for (int i = 0; i < blueTiles.length; i++) {
            printTile(blueTiles[i], i + 1);
        }
        out.println("\nYellow tiles:");
        Tile[] yellowTiles = game.getYellowTiles();
        for (int i = 0; i < yellowTiles.length; i++) {
            printTile(yellowTiles[i], i + 1);
        }
        out.println("\nGreen tiles:");
        Tile[] greenTiles = game.getGreenTiles();
        for (int i = 0; i < greenTiles.length; i++) {
            printTile(greenTiles[i], i + 1);
        }
        out.println();
    }

    private void printTile(Tile tile, int index) {
        out.print("[" + index);
        if (tile.isEmpty()) {
            out.print("| ");
        } else if (tile.isFull()) {
            Piece firstPiece = tile.getFirstPiece();
            Piece secondPiece = tile.getSecondPiece();
            out.print("| " + firstPiece.getPlayer().getColor() + findPieceIndex(firstPiece) + " ");
            out.print("| " + secondPiece.getPlayer().getColor() + findPieceIndex(secondPiece) + " ");
        } else {
            Piece firstPiece = tile.getFirstPiece();
            out.print("| " + firstPiece.getPlayer().getColor() + findPieceIndex(firstPiece) + " ");
        }
        out.print("]");
    }

    private int findPieceIndex(Piece piece) {
        Piece[] playerPieces = piece.getPlayer().getPieces();

        for (int i = 0; i < playerPieces.length; i++) {
            if (playerPieces[i] == piece) {
                return i;
            }
        }

        return -1;
    }
}
