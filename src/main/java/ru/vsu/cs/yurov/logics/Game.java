package ru.vsu.cs.yurov.logics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.vsu.cs.yurov.graphics.fx.GraphicPiece;
import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionReceiver;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

import java.util.*;

public class Game {
    //private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private Die die;
    private boolean isPlaying;

    private PieceActionTypeDefiner pieceActionTypeDefiner;
    private PieceActionReceiver receiver;
    //private Map<PlayerActionType, PlayerAction> playerActionMap;
    private PieceMoveAbilityComputer pieceMoveAbilityComputer;

    private Tile[] normalTiles;
    Tile[] redTiles;
    Tile[] blueTiles;
    Tile[] yellowTiles;
    Tile[] greenTiles;

    private GameDrawer gameDrawer;

    private Piece selectedPiece = null;
    private Text text;
    private int currentNumber = -1;
    private Game() {
        players = new Player[4];
        currentPlayerIndex = 0;
        die = new Die();
        pieceActionTypeDefiner = new PieceActionTypeDefiner();
        pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        normalTiles = new Tile[68];
        redTiles = new Tile[Piece.COLORED_TILES_COUNT];
        blueTiles = new Tile[Piece.COLORED_TILES_COUNT];
        yellowTiles = new Tile[Piece.COLORED_TILES_COUNT];
        greenTiles = new Tile[Piece.COLORED_TILES_COUNT];
    }

    /*public void makeMoveGraphic(Piece piece) {
        Player player = players[currentPlayerIndex];
        //makeMove(-1, piece);
        gameDrawer.drawGame();

        if (player.isAllFinished()) {
            isPlaying = false;
        }
        currentPlayerIndex++;
        currentPlayerIndex %= players.length;
    }*/

    public void calculateBeforeMove(int bonusNumber) {
        Player player = players[currentPlayerIndex];
        currentNumber = bonusNumber < 0 ? die.getNumber() : bonusNumber;
        if (currentNumber == 6 && player.getSixCounter() == 2) {
            player.getLastPiece().bust();
        }
        pieceMoveAbilityComputer.handle(currentNumber, player);

        /*if (!player.canMove()) {
            return;
        }*/

        if (currentNumber <= 6) {
            text.setText("Die roll: " + currentNumber + "\nPlayer " + player.getColor());
        } else {
            text.setText("BonusMove: " + currentNumber + "\nPlayer " + player.getColor());
        }
    }

    public void draw() {
        gameDrawer.drawGame();
    }

    public PieceActionType makeMove(Piece piece) {
        Player player = players[currentPlayerIndex];


        //Piece piece = receiver.receive(player, number);
        player.setLastPiece(piece);
        PieceActionType actionType = pieceActionTypeDefiner.handle(piece, currentNumber);
        actionType.getAction().perform(piece, currentNumber);

        return actionType;
    }

    public void selectNextPlayer() {
        currentPlayerIndex++;
        currentPlayerIndex %= players.length;
    }

    public void calcGraphicPieces(GraphicPiece[] graphicPieces) {
        for (GraphicPiece graphicPiece: graphicPieces) {
            if (graphicPiece.getPiece().canMove() &&
                    graphicPiece.getPiece().getPlayer() == players[currentPlayerIndex]) {
                graphicPiece.setStrokeWidth(3F);
            } else {
                graphicPiece.setStrokeWidth(0F);
            }
        }
    }

    public void checkWin(Stage mainStage) {
        PlayerColor wonColor = isOver();
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
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Congratulations!");
        stage.show();

        closeButton.setOnAction(actionEvent -> {
            mainStage.close();
            stage.close();
        });
        stage.setOnCloseRequest(windowEvent -> closeButton.fire());
    }

    public PlayerColor isOver() {
        for (Player player: players) {
            if (player.isAllFinished()) {
                return player.getColor();
            }
        }

        return null;
    }

    public Piece[] getPieces() {
        List<Piece> result = new ArrayList<>();
        for (Player player: players) {
            result.addAll(Arrays.asList(player.getPieces()));
        }

        return result.toArray(new Piece[0]);
    }

    public Player currentPlayer() {
        return players[currentPlayerIndex];
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setDie(Die die) {
        this.die = die;
    }


    public void setPieceMoveHandler(PieceActionTypeDefiner pieceActionTypeDefiner) {
        this.pieceActionTypeDefiner = pieceActionTypeDefiner;
    }

    public void setReceiver(PieceActionReceiver receiver) {
        this.receiver = receiver;
    }

    public void setPiecesHandler(PieceMoveAbilityComputer pieceMoveAbilityComputer) {
        this.pieceMoveAbilityComputer = pieceMoveAbilityComputer;
    }

    public void setNormalTiles(Tile[] normalTiles) {
        this.normalTiles = normalTiles;
    }

    public void setRedTiles(Tile[] redTiles) {
        this.redTiles = redTiles;
    }

    public void setBlueTiles(Tile[] blueTiles) {
        this.blueTiles = blueTiles;
    }

    public void setYellowTiles(Tile[] yellowTiles) {
        this.yellowTiles = yellowTiles;
    }

    public void setGreenTiles(Tile[] greenTiles) {
        this.greenTiles = greenTiles;
    }


    public Player[] getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Die getDie() {
        return die;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public PieceActionTypeDefiner getPieceMoveHandler() {
        return pieceActionTypeDefiner;
    }

    public PieceActionReceiver getReceiver() {
        return receiver;
    }

    public PieceMoveAbilityComputer getPiecesHandler() {
        return pieceMoveAbilityComputer;
    }

    public Tile[] getNormalTiles() {
        return normalTiles;
    }

    public Tile[] getRedTiles() {
        return redTiles;
    }

    public Tile[] getBlueTiles() {
        return blueTiles;
    }

    public Tile[] getYellowTiles() {
        return yellowTiles;
    }

    public Tile[] getGreenTiles() {
        return greenTiles;
    }

    public void setGameDrawer(GameDrawer gameDrawer) {
        this.gameDrawer = gameDrawer;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public static class Creator {
        public static Game create() {
            Game game = new Game();
            generateTiles(game);
            generatePlayers(game);

            return game;
        }

        private static void generateTiles(Game game) {
            HashSet<Integer> safeTilesIndexes = new HashSet<>(List.of(4, 11, 16, 21, 28, 33, 38, 45, 50, 55, 62,67));

            for (int i = 0; i < game.normalTiles.length; i++) {
                Tile tile = new Tile();
                tile.setIndex(i);
                tile.setSafe(safeTilesIndexes.contains(i));
                game.normalTiles[i] = tile;
            }

            for (int i = 0; i < game.redTiles.length; i++) {
                game.redTiles[i] = new Tile();
                game.redTiles[i].setIndex(68 + i);
                game.blueTiles[i] = new Tile();
                game.blueTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT);
                game.yellowTiles[i] = new Tile();
                game.yellowTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 2);
                game.greenTiles[i] = new Tile();
                game.greenTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 3);
            }
        }

        private static void generatePlayers(Game game) {
            game.players[0] = generatePlayer(0, 38, game.redTiles, PlayerColor.RED, game.normalTiles);
            game.players[1] = generatePlayer(1, 21, game.blueTiles, PlayerColor.BLUE, game.normalTiles);
            game.players[2] = generatePlayer(2, 4, game.yellowTiles, PlayerColor.YELLOW, game.normalTiles);
            game.players[3] = generatePlayer(3, 55, game.greenTiles, PlayerColor.GREEN, game.normalTiles);
        }

        private static Player generatePlayer(int index, int startTileIndex, Tile[] colorTiles, PlayerColor color, Tile[] normalTiles) {
            Player player = new Player();

            Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
            for (int i = 0; i < Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT; i++) {
                int tileIndex = (startTileIndex + i) % 68;
                playerTiles[i] = normalTiles[tileIndex];
            }
            for (int i = 64, j = 0; i < Piece.TILES_COUNT; i++, j++) {
                playerTiles[i] = colorTiles[j];
            }
            player.setTiles(playerTiles);
            Piece[] playerPieces = new Piece[4];
            for (int i = 0; i < playerPieces.length; i++) {
                Piece piece = new Piece();
                piece.setPlayer(player);
                piece.setTilesPassed(-1);
                piece.setCurrentTile(null);
                piece.setHasFinished(false);
                piece.setHomeState(HomeState.IN);
                piece.setIndex(i);
                playerPieces[i] = piece;
            }
            playerPieces[0].setHomeState(HomeState.OUT);
            playerPieces[0].setTilesPassed(0);
            playerPieces[0].setCurrentTile(playerTiles[0]);
            playerPieces[0].setCanMove(true);
            playerTiles[0].setFirstPiece(playerPieces[0]);
            player.setPieces(playerPieces);
            player.setColor(color);

           return player;
        }
    }
}
