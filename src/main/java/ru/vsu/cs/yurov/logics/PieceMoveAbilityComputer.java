package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;

public class PieceMoveAbilityComputer {
    private PieceMoveAbilityComputer() {}

    public static void compute(Player player, int number) {
        if (number == 6 || number == 7) {
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
                    piece.setCanMove(!player.getTiles()[0].isFull());
                } else {
                    if (hasBlocks && isAllOut) {
                        piece.setCanMove(piece.getCurrentTile().isFull() &&
                                piece.getTilesPassed() + number < Game.PIECE_ALL_TILES_COUNT &&
                                !player.getTiles()[piece.getTilesPassed() + number].isFull());
                    } else {
                        piece.setCanMove(checkTile(player, piece, number));
                    }
                }
            }
            return;
        }

        player.setSixCounter(0);
        for (Piece piece : player.getPieces()) {
            if (piece.getHomeState() == HomeState.IN) {
                piece.setCanMove(number >= 5 && !player.getTiles()[0].isFull());
            } else {
                piece.setCanMove(checkTile(player, piece, number));
            }
        }

    }

    private static boolean checkTile(Player player, Piece piece, int number) {
        int tilesPassed = piece.getTilesPassed();

        int nextTileIndex = tilesPassed + number;
        if (nextTileIndex >= Game.PIECE_ALL_TILES_COUNT) {
            return false;
        }
        if (number == 20 || number == 10) {
            return !player.getTiles()[nextTileIndex].isFull();
        }
        Tile[] playerTiles = player.getTiles();
        for (int i = tilesPassed + 1; i <= nextTileIndex; i++) {
            if (playerTiles[i].isFull()) {
                return false;
            }
        }

        return true;
    }
}
