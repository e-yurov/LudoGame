package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;

public class PiecesHandler {
    public void handle(int number, Player player) {
        if (number == 6) {
            player.addSixCounter();

            boolean isAllOut = true;
            boolean hasBlocks = false;
            for (Piece piece : player.getPieces()) {
                boolean isOut = piece.getHomeState() == HomeState.OUT;
                isAllOut &= isOut;
                if (isOut) {
                    hasBlocks |= piece.getCurrentTile().isFull();
                }
            }

            for (Piece piece : player.getPieces()) {
                if (piece.getHomeState() == HomeState.IN) {
                    piece.setCanMove(!player.tiles[0].isFull());
                } else {
                    if (hasBlocks && isAllOut) {
                        piece.setCanMove(piece.getCurrentTile().isFull() &&
                                piece.getTilesPassed() + number < Piece.TILES_COUNT &&
                                !player.tiles[piece.getTilesPassed() + number].isFull());
                    } else {
                        piece.setCanMove(checkTile(number, piece, player));
                    }
                }
            }
            return;
        }

        player.setSixCounter(0);
        for (Piece piece : player.getPieces()) {
            if (piece.getHomeState() == HomeState.IN) {
                piece.setCanMove(number >= 5 && !player.tiles[0].isFull());
            } else {
                piece.setCanMove(checkTile(number, piece, player));
            }
        }

    }

    private boolean checkTile(int number, Piece piece, Player player) {
        int tilesPassed = piece.getTilesPassed();
        Tile currentTIle = player.tiles[tilesPassed];

        int nextTileIndex = tilesPassed + number;
        if (nextTileIndex >= Piece.TILES_COUNT) {
            return false;
        }
        for (int i = tilesPassed + 1; i <= nextTileIndex; i++) {
            if (player.tiles[i].isFull()) {
                return false;
            }
        }

        return true;
    }
}
