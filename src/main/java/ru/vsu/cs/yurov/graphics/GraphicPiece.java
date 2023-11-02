package ru.vsu.cs.yurov.graphics;

import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import ru.vsu.cs.yurov.logics.Piece;

public class GraphicPiece extends Circle {
    private Piece piece;

    public GraphicPiece(Color color, GameApplication application) {
        piece = new Piece();
        setFill(color);
        setStroke(Color.MAGENTA);
        setStrokeWidth(3F);
        setCenterX(-100);
        setCenterY(-100);
        setRadius(15);

        setOnMouseClicked(mouseEvent -> application.handleGraphicPieceClick(piece));
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
