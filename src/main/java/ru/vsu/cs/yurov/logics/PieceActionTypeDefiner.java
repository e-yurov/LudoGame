package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

public class PieceActionTypeDefiner {
    public PieceActionType defineAction(Piece piece, int number) {
        if (!piece.canMove()){
            return PieceActionType.DO_NOTHING;
        }
        if (piece.getHomeState() == HomeState.IN) {
            return PieceActionType.LEAVE_HOME;
        }

        Player player = piece.getPlayer();
        int tileToStepIndex = piece.getTilesPassed() + number;
        Tile tileToStep = player.tiles[tileToStepIndex];

        if (tileToStepIndex == Piece.TILES_COUNT - 1) {
            piece.setHasFinished(true);
            return PieceActionType.FINISH;
        }
        /*if (!tileToStep.isEmpty() &&
                tileToStep.getFirstPiece().getPlayer().getColor() != piece.getPlayer().getColor()) {
            return PieceActionType.HIT;
        }
        if (!tileToStep.isEmpty()) {
            return PieceActionType.MOVE_AND_BLOCK;
        }*/
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
