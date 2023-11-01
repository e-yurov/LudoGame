package ru.vsu.cs.yurov.graphics.fx;

import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.vsu.cs.yurov.logics.Game;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

public class GraphicPiece extends Circle {
    private Piece piece;
    private Game game;
    private boolean isBonusMove;

    public GraphicPiece(Color color, Game game, Stage stage) {
        piece = new Piece();
        this.game = game;
        //setFill(Color.color(0.4D, 0.0D, 0.0D));
        setFill(color);

        //setFill(Color.color(0.0D, 0.4D, 0.0D));
//        setFill(new RadialGradient(0, 0, 100, 100, 25, false, CycleMethod.REPEAT,
//                new Stop(0, Color.RED), new Stop(1, Color.BLUEVIOLET)));

        //setStroke(Color.color(color.getBlue() + 0.2, color.getRed() + 0.2, color.getGreen() + 0.2));
        setStroke(Color.MAGENTA);
        setStrokeWidth(3F);
        setCenterX(-100);
        setCenterY(-100);
        setRadius(15);

        setOnMouseClicked(mouseEvent -> {
            //game.setSelectedPiece(piece.canMove() ? piece : null);
            if (piece.canMove() && piece.getPlayer() == game.getPlayers()[game.getCurrentPlayerIndex()]){
                PieceActionType actionType = game.makeMove(piece);
                if (actionType == PieceActionType.HIT) {
                    game.calculateBeforeMove(20);
                } else if (actionType == PieceActionType.FINISH) {
                    game.calculateBeforeMove(10);
                } else {
                    game.selectNextPlayer();
                    game.calculateBeforeMove(-1);
                }
                game.draw();
            }

            if (piece.getPlayer() == game.getPlayers()[game.getCurrentPlayerIndex()] && !piece.getPlayer().canMove()) {
                game.selectNextPlayer();
                game.calculateBeforeMove(-1);
                game.draw();
            }
            game.checkWin(stage);
        });
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
