package ru.vsu.cs.yurov.graphics.fx;

import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import ru.vsu.cs.yurov.logics.Game;
import ru.vsu.cs.yurov.logics.Piece;

public class GraphicPiece extends Circle {
    private Piece piece;
    private Game game;
    public GraphicPiece(Color color, Game game) {
        piece = new Piece();
        this.game = game;
        //setFill(Color.color(0.4D, 0.0D, 0.0D));
        setFill(color);
        //setFill(Color.color(0.0D, 0.4D, 0.0D));
//        setFill(new RadialGradient(0, 0, 100, 100, 25, false, CycleMethod.REPEAT,
//                new Stop(0, Color.RED), new Stop(1, Color.BLUEVIOLET)));
        setStroke(Color.DARKBLUE);
        setStrokeWidth(3F);
        setCenterX(10);
        setCenterY(10);
        setRadius(15);

        setOnMouseClicked(mouseEvent -> {
            //game.setSelectedPiece(piece.canMove() ? piece : null);
            if (piece.canMove() && piece.getPlayer() == game.getPlayers()[game.getCurrentPlayerIndex()]){
                game.makeMoveGraphic(piece);
            }
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
