package ru.vsu.cs.yurov.logics.actions.piece;

import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Tile;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public enum PieceActionType {
    MOVE((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                nextTile.setFirstPiece(piece);

                leaveCurrentTile(piece, number);
    }),
    MOVE_AND_BLOCK((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                nextTile.setSecondPiece(piece);

                leaveCurrentTile(piece, number);
    }),
    HIT((piece, number) -> {
                Tile nextTile = piece.getNextTile(number);
                Piece enemyPiece = nextTile.getFirstPiece();
                /*enemyPiece.setHomeState(HomeState.IN);
                enemyPiece.setTilesPassed(-1);
                enemyPiece.setCurrentTile(null);*/
                enemyPiece.kill();

                nextTile.setFirstPiece(piece);

                leaveCurrentTile(piece, number);
    }),
    FINISH((piece, number) -> {
                piece.setHasFinished(true);
                piece.getPlayer().addFinishedPiecesCounter();

                leaveCurrentTile(piece, number);
    }),
    LEAVE_HOME((piece, number) -> {
                Tile startTile = piece.getPlayer().getTiles()[0];

                piece.setHomeState(HomeState.OUT);
                piece.setTilesPassed(0);
                piece.setCurrentTile(startTile);

                if (startTile.isEmpty()) {
                    startTile.setFirstPiece(piece);
                } else {
                    startTile.setSecondPiece(piece);
                }
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

        piece.setCurrentTile(piece.getNextTile(number));
        piece.setTilesPassed(piece.getTilesPassed() + number);
    }
}
