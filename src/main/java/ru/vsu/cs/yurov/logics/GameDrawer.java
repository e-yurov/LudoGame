package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.graphics.fx.GraphicPiece;
import ru.vsu.cs.yurov.graphics.fx.GraphicPieceDisposer;
import ru.vsu.cs.yurov.graphics.fx.GraphicTile;

public class GameDrawer {
    public void drawGame() {
    }

    public void drawGame(GraphicPiece[] graphicPieces, GraphicTile[] graphicTiles, Player currentPlayer) {
        for (GraphicPiece graphicPiece: graphicPieces) {
            GraphicPieceDisposer.dispose(graphicTiles, graphicPiece);
        }

        for (GraphicPiece graphicPiece: graphicPieces) {
            if (graphicPiece.getPiece().canMove() &&
                    graphicPiece.getPiece().getPlayer() == currentPlayer) {
                graphicPiece.setStrokeWidth(3F);
            } else {
                graphicPiece.setStrokeWidth(0F);
            }
        }
    }
}
