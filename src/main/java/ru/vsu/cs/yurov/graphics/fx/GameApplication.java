package ru.vsu.cs.yurov.graphics.fx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.vsu.cs.yurov.logics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameApplication extends Application {
    public static final int BOARD_SIZE = 900;
    public static final int TILE_LONG_SIDE = (int) Math.round(220 * ((double)BOARD_SIZE / 2048));
    public static final int TILE_SHORT_SIDE = (int) Math.round(96 * ((double)BOARD_SIZE / 2048));

    private Game game;
    private GraphicPiece[] graphicPieces;
    private GraphicTile[] graphicTiles;

    private Stage stage;

    private  GraphicPiece[] testGraphicPieces;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Pane root = new Pane();
        //root.setOnMouseClicked(mouseEvent -> System.out.printf("x: %.0f, y: %.0f\n", mouseEvent.getX(), mouseEvent.getY()));
        ObservableList<Node> rootChildren = root.getChildren();
        Image board = new Image("board.png");
        ImageView view = new ImageView(board);
        view.setFitWidth(BOARD_SIZE);
        view.setFitHeight(BOARD_SIZE);

        Text text = new Text();
        text.setX(930);
        text.setY(30);

        game = Game.Creator.create();
        game.setText(text);
        graphicTiles = GraphicComponentsGenerator.generateGraphicTiles(TILE_SHORT_SIDE, TILE_LONG_SIDE);
        graphicPieces = GraphicComponentsGenerator.generateGraphicPieces(game.getPieces(), this);

        //testGraphicPieces = generateTestGraphicPieces(100);

        rootChildren.addAll(view);
        rootChildren.addAll(text);
        rootChildren.addAll(graphicPieces);
        rootChildren.addAll(GraphicComponentsGenerator.generatePlayersText(game.getPlayers()));

        rootChildren.addAll(generateTestGraphicPieces(100));

        Scene scene = new Scene(root, 1600, 900);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        drawGame();
        game.calculateBeforeMove(-1);

        //testFinish();
        System.out.printf("Long=%d, short=%d", TILE_LONG_SIDE, TILE_SHORT_SIDE);

    }

    public void drawGame() {
        GameDrawer.drawGame(graphicPieces, graphicTiles, game.currentPlayer());
    }

    public void checkWin() {
        PlayerColor wonColor = game.getFinishedPlayerColor();
        if (wonColor == null) {
            return;
        }

        Text victoryText = new Text(wonColor + " player won!");
        victoryText.setFont(Font.font(20D));
        Button closeButton = new Button("Close");

        VBox root = new VBox(victoryText, closeButton);
        root.setSpacing(50D);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 200, 200);
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.setTitle("Congratulations!");
        popupStage.show();

        closeButton.setOnAction(actionEvent -> {
            stage.close();
            popupStage.close();
        });
        popupStage.setOnCloseRequest(windowEvent -> closeButton.fire());
    }

    void handlePieceClick(Piece piece) {
        if (piece.canMove() && piece.getPlayer() == game.currentPlayer()){
            int moveNumber = game.makeMove(piece);
            if (moveNumber < 0) {
                game.selectNextPlayer();
            }
            game.calculateBeforeMove(moveNumber);
            drawGame();
        }

        if (piece.getPlayer() == game.currentPlayer() && !piece.getPlayer().canMove()) {
            game.selectNextPlayer();
            game.calculateBeforeMove(-1);
            drawGame();
        }
        checkWin();
    }

    public static void main(String[] args) {
        launch();
    }

    public void testFinish() {
        Player[] players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            for (Piece piece: player.getPieces()) {
                piece.setHasFinished(true);
            }
        }
    }

    public GraphicPiece[] generateTestGraphicPieces(int size) {
        List<GraphicPiece> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Tile tile = new Tile();
            tile.setIndex(i);

            Random random = new Random();
            int num = random.nextInt(1, 3);

            GraphicPiece graphicPiece1 = new GraphicPiece(Color.color(0.0D, 0.4D, 0.0D), this);
            Piece piece1 = new Piece();
            piece1.setCurrentTile(tile);
            tile.setFirstPiece(piece1);
            graphicPiece1.setPiece(piece1);
            GraphicPiece graphicPiece2 = null;
            if (num == 2) {
                graphicPiece2 = new GraphicPiece(Color.color(0.0D, 0.4D, 0.0D), this);
                Piece piece2 = new Piece();
                piece2.setCurrentTile(tile);
                tile.setSecondPiece(piece2);
                graphicPiece2.setPiece(piece2);
            }

            result.add(graphicPiece1);
            if (graphicPiece2 != null) {
                result.add(graphicPiece2);
            }
        }

        return result.toArray(new GraphicPiece[0]);
    }

    public void drawRectangles(GraphicTile[] graphicTiles, List<Node> children) {
        for (int i = 0; i < graphicTiles.length; i++) {
            Rectangle rectangle = new Rectangle();
            GraphicTile graphicTile = graphicTiles[i];

            rectangle.setX(graphicTile.getX());
            rectangle.setY(graphicTile.getY());
            rectangle.setWidth(graphicTile.getWidth());
            rectangle.setHeight(graphicTile.getHeight());
            int argb = i * 1183886;
            rectangle.setFill(Color.rgb((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, (argb) & 0xFF));

            children.add(rectangle);
        }
    }
}
