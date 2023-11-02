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
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyApplication extends Application {
    public static final int BOARD_SIZE = 900;
    public static final int TILE_LONG_SIDE = (int) Math.round(220 * ((double)BOARD_SIZE / 2048));
    public static final int TILE_SHORT_SIDE = (int) Math.round(96 * ((double)BOARD_SIZE / 2048));

    private Game game;
    //private GraphicPieceDisposer graphicPieceDisposer = new GraphicPieceDisposer();
    private GraphicPiece[] graphicPieces;
    private GraphicTile[] graphicTiles;

    private Stage stage;
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
        graphicTiles = generateGraphicTiles();
        graphicPieces = generateGraphicPieces();
        /*game.setGameDrawer(new GameDrawer(){
            @Override
            public void drawGame() {
                for (GraphicPiece graphicPiece: graphicPieces) {
                    GraphicPieceDisposer.dispose(graphicTiles, graphicPiece);
                }
                game.calcGraphicPieces(graphicPieces);
            }
        });*/
        //game.setGameDrawer();

        rootChildren.addAll(view);
        rootChildren.addAll(text);
        rootChildren.addAll(graphicPieces);
        rootChildren.addAll(generatePlayersText());

        //rootChildren.addAll(generateTestGraphicPieces(100));

        Scene scene = new Scene(root, 1600, 900);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        drawGame();
        game.calculateBeforeMove(-1);

        //testFinish();
        /*game.setDie(new Die(){
            @Override
            public int getNumber() {
                return 6;
            }
        });*/
        //game.start();
        System.out.printf("Long=%d, short=%d", TILE_LONG_SIDE, TILE_SHORT_SIDE);


        /*Button go = new Button("Game over!");
        Pane popupPane = new Pane(go);
        Scene popupScene = new Scene(popupPane, 300, 300);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.show();

        go.setOnMouseClicked(mouseEvent -> {
            stage.close();
            popupStage.close();
        });*/
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

    public GraphicTile[] generateGraphicTiles() {
        int tilesCount = 100;
        GraphicTile[] result = new GraphicTile[tilesCount];
        for (int i = 0; i < 7; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498, 845 - i * TILE_SHORT_SIDE, TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[7] = new GraphicTile(TileForm.CORNER_HORIZONTAL_LEFT, 7,
                498, 845 - TILE_SHORT_SIDE * 7 - 4,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        result[8] = new GraphicTile(TileForm.CORNER_VERTICAL_TOP, 8,
                552 - 4, 498,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        for (int i = 9; i < 16; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    552 + TILE_SHORT_SIDE * (i - 8), 498, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[16]= new GraphicTile(TileForm.VERTICAL, 16,
                552 + TILE_SHORT_SIDE * 7, 498 - TILE_LONG_SIDE, TILE_SHORT_SIDE, TILE_LONG_SIDE);

        for (int i = 17; i < 24; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    846 - TILE_SHORT_SIDE * (i - 17), 305, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[24] = new GraphicTile(TileForm.CORNER_VERTICAL_BOTTOM, 24,
                847 - TILE_SHORT_SIDE * 7 - 4, 305,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        result[25] = new GraphicTile(TileForm.CORNER_HORIZONTAL_LEFT, 25,
                498, 306,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        for (int i = 26; i < 33; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498, 306 - TILE_SHORT_SIDE * (i - 25), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[33] = new GraphicTile(TileForm.HORIZONTAL, 33,
                498 - TILE_LONG_SIDE, 12, TILE_LONG_SIDE, TILE_SHORT_SIDE);

        for (int i = 34; i < 41; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    305, 12 + TILE_SHORT_SIDE * (i - 34), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[41] = new GraphicTile(TileForm.CORNER_HORIZONTAL_RIGHT, 41,
                305, 12 + TILE_SHORT_SIDE * 7,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        result[42] = new GraphicTile(TileForm.CORNER_VERTICAL_BOTTOM, 42,
                306, 305,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        for (int i = 43; i < 50; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    306 - TILE_SHORT_SIDE * (i - 42), 305, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[50] = new GraphicTile(TileForm.VERTICAL, 50,
                12, 305 + TILE_LONG_SIDE, TILE_SHORT_SIDE, TILE_LONG_SIDE);

        for (int i = 51; i < 58; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    12 + TILE_SHORT_SIDE * (i - 51), 498, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[58] = new GraphicTile(TileForm.CORNER_VERTICAL_TOP, 58,
                12 + TILE_SHORT_SIDE * 7, 498,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        result[59] = new GraphicTile(TileForm.CORNER_HORIZONTAL_RIGHT, 59,
                305, 551 - 4,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        for (int i = 60; i < 67; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    305, 551 + TILE_SHORT_SIDE * (i - 59), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[67] = new GraphicTile(TileForm.HORIZONTAL, 67,
                401, 887 - TILE_SHORT_SIDE, TILE_LONG_SIDE, TILE_SHORT_SIDE);


        //color tiles
        //red
        for (int i = 68; i < 76; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498 - TILE_LONG_SIDE, 54 + TILE_SHORT_SIDE * (i - 68), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        //blue
        for (int i = 76; i < 84; i++) {
            result[i]= new GraphicTile(TileForm.VERTICAL, i,
                    804 - TILE_SHORT_SIDE * (i - 76), 401, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        //yellow
        for (int i = 84; i < 92; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    401, 803 - TILE_SHORT_SIDE * (i - 84), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        //green
        for (int i = 92; i < 100; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    54 + TILE_SHORT_SIDE * (i - 92), 402, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        return result;
    }

    public GraphicPiece[] generateTestGraphicPieces(int size) {
        List<GraphicPiece> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Tile tile = new Tile();
            tile.setIndex(i);

            Random random = new Random();
            int num = random.nextInt(1, 3);

            GraphicPiece graphicPiece1 = new GraphicPiece(Color.color(0.0D, 0.4D, 0.0D), game, stage, this);
            Piece piece1 = new Piece();
            piece1.setCurrentTile(tile);
            tile.setFirstPiece(piece1);
            graphicPiece1.setPiece(piece1);
            GraphicPiece graphicPiece2 = null;
            if (num == 2) {
                graphicPiece2 = new GraphicPiece(Color.color(0.0D, 0.4D, 0.0D), game, stage, this);
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

    public GraphicPiece[] generateGraphicPieces() {
        Piece[] pieces = game.getPieces();
        GraphicPiece[] result = new GraphicPiece[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            result[i] = new GraphicPiece(pieces[i].getPlayer().getColor().getRgbColor(), game, stage, this);
            result[i].setPiece(pieces[i]);
        }

        return result;
    }

    public Text[] generatePlayersText() {
        Player[] players = game.getPlayers();

        Text[] texts = new Text[players.length];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = new Text("0");
            texts[i].setFont(Font.font(20D));
            players[i].setTextFinishedPiecesCounter(texts[i]);
        }

        //red
        texts[0].setX(465);
        texts[0].setY(415);

        //blue
        texts[1].setX(515);
        texts[1].setY(465);

        //yellow
        texts[2].setX(465);
        texts[2].setY(515);

        //green
        texts[3].setX(415);
        texts[3].setY(465);

        return texts;
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
}
