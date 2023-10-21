package ru.vsu.cs.yurov.logics.actions.piece;

import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Tile;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public enum PieceActionType {
    MOVE((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                nextTile.setFirstPiece(piece);

                leaveCurrentTile(piece, number);
                /*if (currentTile.isFull()) {
                    if (currentTile.getFirstPiece() == piece) {
                        currentTile.setFirstPiece(currentTile.getSecondPiece());
                    }
                    currentTile.setSecondPiece(null);
                } else {
                    currentTile.setFirstPiece(null);
                }

                piece.setTilesPassed(piece.getTilesPassed() + number);
                piece.setCurrentTile(nextTile);*/
    }),
    MOVE_AND_BLOCK((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                nextTile.setSecondPiece(piece);

                leaveCurrentTile(piece, number);
    }),
    HIT((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                Piece enemyPiece = nextTile.getFirstPiece();
                enemyPiece.setHomeState(HomeState.IN);
                enemyPiece.setTilesPassed(-1);
                enemyPiece.setCurrentTile(null);

                nextTile.setFirstPiece(piece);

                leaveCurrentTile(piece, number);
    }),
    FINISH((piece, number) -> {
                piece.setHasFinished(true);

                leaveCurrentTile(piece, number);
    }),
    LEAVE_HOME((piece, number) -> {
                piece.setHomeState(HomeState.OUT);
                piece.setTilesPassed(0);
                piece.setCurrentTile(piece.getPlayer().getTiles()[0]);
    }),
    DO_NOTHING((piece, number) -> {});

    private final PieceAction action;

    PieceActionType(PieceAction action) {
        this.action = action;
    }

    public PieceAction getAction() {
        return action;
    }

    private static void leaveCurrentTile(Piece piece, int number) {
        Tile currentTile = piece.getCurrentTile();

        if (currentTile.isFull()) {
            if (currentTile.getFirstPiece() == piece) {
                currentTile.setFirstPiece(currentTile.getSecondPiece());
            }
            currentTile.setSecondPiece(null);
        } else {
            currentTile.setFirstPiece(null);
        }

        piece.setTilesPassed(piece.getTilesPassed() + number);
        piece.setCurrentTile(piece.getNextTile(number));
    }
}
