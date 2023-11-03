package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

public class PieceActionTypeDefiner {
    private PieceActionTypeDefiner() {}

    public static PieceActionType defineAction(Piece piece, int number) {
        if (!piece.canMove()){
            return PieceActionType.DO_NOTHING;
        }
        if (piece.getHomeState() == HomeState.IN) {
            return PieceActionType.LEAVE_HOME;
        }

        Player player = piece.getPlayer();
        int tileToStepIndex = piece.getTilesPassed() + number;
        Tile tileToStep = player.getTiles()[tileToStepIndex];

        if (tileToStepIndex == Game.PIECE_ALL_TILES_COUNT - 1) {
            piece.setHasFinished(true);
            return PieceActionType.FINISH;
        }
        if (!tileToStep.isEmpty()) {
            if (tileToStep.isSafe() ||
                    tileToStep.getFirstPiece().getPlayer().getColor() == piece.getPlayer().getColor()) {
                return PieceActionType.MOVE_AND_BLOCK;
            }
            return PieceActionType.HIT;
        }

        return PieceActionType.MOVE;
    }
}
