package ru.vsu.cs.yurov.graphics.fx;

import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import ru.vsu.cs.yurov.logics.Game;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

public class GraphicPiece extends Circle {
    private Piece piece;
    private Game game;
    private boolean isBonusMove;

    public GraphicPiece(Color color, Game game) {
        piece = new Piece();
        this.game = game;
        //setFill(Color.color(0.4D, 0.0D, 0.0D));
        setFill(color);
        //setFill(Color.color(0.0D, 0.4D, 0.0D));
//        setFill(new RadialGradient(0, 0, 100, 100, 25, false, CycleMethod.REPEAT,
//                new Stop(0, Color.RED), new Stop(1, Color.BLUEVIOLET)));
        //setStroke(Color.DARKBLUE);
        setStroke(Color.MAGENTA);
        setStrokeWidth(3F);
        setCenterX(10);
        setCenterY(10);
        setRadius(15);

        setOnMouseClicked(mouseEvent -> {
            //game.setSelectedPiece(piece.canMove() ? piece : null);
            if (piece.canMove() && piece.getPlayer() == game.getPlayers()[game.getCurrentPlayerIndex()]){
                PieceActionType actionType = game.makeMove(piece);
                if (actionType == PieceActionType.HIT) {
                    game.calcBeforeMove(20);
                } else if (actionType == PieceActionType.FINISH) {
                    game.calcBeforeMove(10);
                } else {
                    game.selectNextPlayer();
                    game.calcBeforeMove(-1);
                }
                game.draw();
            }

            if (!piece.getPlayer().canMove()) {
                game.selectNextPlayer();
                game.calcBeforeMove(-1);
            }
            game.checkWin();
        });
    }

    public void init() {

    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
