package ru.vsu.cs.yurov.logics.actions.piece;

import ru.vsu.cs.yurov.logics.Piece;

@FunctionalInterface
public interface PieceAction {
    void perform(Piece piece, int number);
}
