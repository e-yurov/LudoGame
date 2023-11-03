package ru.vsu.cs.yurov.graphics;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
import ru.vsu.cs.yurov.logics.actions.HomeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameApplication extends Application {
    static final int BOARD_SIZE = 900;

    private Game game;
    private GraphicPiece[] graphicPieces;
    private GraphicTile[] graphicTiles;

    private Stage stage;

    private GraphicPiece[] testGraphicPieces;
    private Text dieNumberText;
    private Text sixesInRowText;

    private int playersCount;

    @Override
    public void init() {
        dieNumberText = new Text();
        dieNumberText.setFont(Font.font(20D));
        dieNumberText.setX(930);
        dieNumberText.setY(20);

        sixesInRowText = new Text();
        sixesInRowText.setFont(Font.font(20D));
        sixesInRowText.setX(930);
        sixesInRowText.setY(100);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();

        Text text = new Text("Select number of players:");
        text.setFont(Font.font(20D));

        RadioButton two = new RadioButton("2");
        two.setUserData(2);
        two.setFont(Font.font(20D));
        RadioButton three = new RadioButton("3");
        three.setUserData(3);
        three.setFont(Font.font(20D));
        RadioButton four = new RadioButton("4");
        four.setUserData(4);
        four.setFont(Font.font(20D));

        ToggleGroup toggleGroup = new ToggleGroup();
        two.setToggleGroup(toggleGroup);
        three.setToggleGroup(toggleGroup);
        four.setToggleGroup(toggleGroup);

        Button button = new Button("Select");
        button.setFont(Font.font(20D));

        root.getChildren().addAll(text, two, three, four, button);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10D);

        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Players");
        stage.setScene(scene);
        stage.show();

        button.setOnAction(actionEvent -> {
            stage.close();
            playersCount = (int) toggleGroup.getSelectedToggle().getUserData();
            startGame();
        });
    }

    public void startGame() {
        this.stage = new Stage();
        Pane root = new Pane();
        ObservableList<Node> rootChildren = root.getChildren();
        Image board = new Image("board.png");
        ImageView view = new ImageView(board);
        view.setFitWidth(BOARD_SIZE);
        view.setFitHeight(BOARD_SIZE);

        game = Game.Creator.create(playersCount);
        game.setText(dieNumberText);
        graphicTiles = GraphicComponentsGenerator.generateGraphicTiles();
        graphicPieces = GraphicComponentsGenerator.generateGraphicPieces(game.getPieces(), this);

        rootChildren.addAll(view, dieNumberText, sixesInRowText);
        rootChildren.addAll(graphicPieces);
        rootChildren.addAll(GraphicComponentsGenerator.generatePlayersText(game.getPlayers()));

        Scene scene = new Scene(root, 1500, 900);
        stage.setTitle("Ludo game");
        stage.setScene(scene);
        stage.show();

        //enableTestFinish();
        //enableTestSixes();

        game.calculateBeforeMove(-1);
        drawGame();
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

    void handleGraphicPieceClick(Piece piece) {
        Player player = piece.getPlayer();
        Piece lastPiece = player.getLastPiece();
        if (player.getSixCounter() == 3) {
            if (lastPiece.isOnColorTile()) {
                sixesInRowText.setText("Three \"6\" in a row!\nLast piece was returned to the color road's beginning");
            } else {
                sixesInRowText.setText("Three \"6\" in a row!\nLast piece was killed :(");
            }

            player.setSixCounter(0);
            if (!lastPiece.getCurrentTile().isSafe()) {
                lastPiece.kill();
            } else {
                sixesInRowText.setText("Three \"6\" in a row!\nFortunately, last piece was on the safe tile :)");
            }

            game.selectNextPlayer();
            game.calculateBeforeMove(-1);
            drawGame();
            return;
        }
        sixesInRowText.setText(null);

        if (piece.canMove() && player == game.currentPlayer()){
            int moveNumber = game.makeMove(piece);
            if (moveNumber < 0) {
                game.selectNextPlayer();
            }
            game.calculateBeforeMove(moveNumber);
            drawGame();
        }
        if (player == game.currentPlayer() && !player.canMove()) {
            game.selectNextPlayer();
            game.calculateBeforeMove(-1);
            drawGame();
        }
        checkWin();
    }


    public static void main(String[] args) {
        launch();
    }

    public void enableTestFinish() {
        Player[] players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            for (Piece piece: player.getPieces()) {
                piece.setHasFinished(true);
            }
        }
    }

    public void enableTestSixes() {
        Player player = game.getPlayers()[0];

        Piece piece0 = player.getPieces()[0];
        Piece piece1 = player.getPieces()[1];
        Piece piece2 = player.getPieces()[2];
        Piece piece3 = player.getPieces()[3];

        piece0.setTilesPassed(64);
        piece0.setCurrentTile(player.getTiles()[64]);
        player.getTiles()[64].setPiece(piece0);
        player.setLastPiece(piece0);

        piece1.setHomeState(HomeState.OUT);
        piece1.setTilesPassed(0);
        piece1.setCurrentTile(player.getTiles()[0]);

        piece2.setHomeState(HomeState.OUT);
        piece2.setTilesPassed(0);
        piece2.setCurrentTile(player.getTiles()[0]);

        piece3.setHomeState(HomeState.OUT);
        piece3.setTilesPassed(0);
        piece3.setCurrentTile(player.getTiles()[0]);

        game.setDie(new Die() {
            @Override
            public int getNumber() {
                return 6;
            }
        });
    }

    public GraphicPiece[] generateTestGraphicPieces(int size) {
        List<GraphicPiece> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Tile tile = new Tile(i);

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
