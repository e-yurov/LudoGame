package ru.vsu.cs.yurov.logics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.vsu.cs.yurov.graphics.TextGameDrawer;
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

    private TextGameDrawer gameDrawer;

    private Piece selectedPiece = null;
    private Text text;
    private int currentNumber = -1;
    public Game() {
        createGame();
        /*board = new Board();
        players = new Player[4];
        currentPlayerIndex = 0;
        die = new Die();
        isPlaying = false;
        receiver = new PieceActionReceiver();
        playerActionMap = new HashMap<>();
        pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        normalTiles = new Tile[68];
        redTiles = new Tile[7];*/
    }

    /*private void createGame() {
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
        generateTiles();
        generatePlayers();
    }

    private void generatePlayers() {
        generatePlayer(0, 38, redTiles, PlayerColor.RED);
        generatePlayer(1, 21, blueTiles, PlayerColor.BLUE);
        generatePlayer(2, 4, yellowTiles, PlayerColor.YELLOW);
        generatePlayer(3, 55, greenTiles, PlayerColor.GREEN);
    }

    private void generatePlayer(int index, int startTileIndex, Tile[] colorTiles, PlayerColor color) {
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

        players[index] = player;
    }

    *//*private void generatePlayersOld() {
        Player redPlayer = new Player();
        Player bluePlayer = new Player();
        Player yellowPlayer = new Player();
        Player greenPlayer = new Player();

        Tile[] redPlayerTiles = new Tile[Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT; i++) {
            int tileIndex = (38 + i) % 68;
            redPlayerTiles[i] = normalTiles[tileIndex];
        }
        for (int i = 56, j = 0; i < 64; i++, j++) {
            redPlayerTiles[i] = redTiles[j];
        }
        redPlayer.setTiles(redPlayerTiles);
        Piece[] redPlayerPieces = new Piece[4];
        for (int i = 0; i < redPlayerPieces.length; i++) {
            Piece piece = new Piece();
            piece.setPlayer(redPlayer);
            piece.setTilesPassed(-1);
            piece.setCurrentTile(null);
            piece.setHasFinished(false);
            piece.setHomeState(HomeState.IN);
            redPlayerPieces[i] = piece;
        }
        redPlayerPieces[0].setHomeState(HomeState.OUT);
        redPlayerPieces[0].setTilesPassed(0);
        redPlayerPieces[0].setCurrentTile(redPlayerTiles[0]);
        redPlayerTiles[0].setFirstPiece(redPlayerPieces[0]);
        redPlayer.setPieces(redPlayerPieces);
        redPlayer.setColor(PlayerColor.RED);

        players[0] = redPlayer;
        players[1] = bluePlayer;
        players[2] = yellowPlayer;
        players[3] = greenPlayer;
    }*//*

    private void generateTiles() {
        HashSet<Integer> safeTilesIndexes = new HashSet<>(List.of(4, 11, 16, 21, 28, 33, 38, 45, 50, 55, 62,67));

        for (int i = 0; i < normalTiles.length; i++) {
            Tile tile = new Tile();
            tile.setIndex(i);
            tile.setSafe(safeTilesIndexes.contains(i));
            normalTiles[i] = tile;
        }

        for (int i = 0; i < redTiles.length; i++) {
            redTiles[i] = new Tile();
            redTiles[i].setIndex(68 + i);
            blueTiles[i] = new Tile();
            blueTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT);
            yellowTiles[i] = new Tile();
            yellowTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 2);
            greenTiles[i] = new Tile();
            greenTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 3);
        }
    }*/

    public void start() {
        isPlaying = true;
        Player player;

        while (isPlaying) {
            gameDrawer.drawGame();

            player = players[currentPlayerIndex];
            makeMove(-1);

            if (player.isAllFinished()) {
                isPlaying = false;
            }
            currentPlayerIndex++;
            currentPlayerIndex %= players.length;
        }

        //int number = die.getNumber();

        //pieceMoveAbilityComputer.handle(number, player);


        //PlayerActionType playerActionType = receiver.receive();
        //PlayerAction playerAction = playerActionMap.get(playerActionType);
        //playerAction.perform();

        //board.makeMove();
    }

    public void makeMoveGraphic(Piece piece) {
        Player player = players[currentPlayerIndex];
        //makeMove(-1, piece);
        gameDrawer.drawGame();

        if (player.isAllFinished()) {
            isPlaying = false;
        }
        currentPlayerIndex++;
        currentPlayerIndex %= players.length;
    }

    public void calcBeforeMove(int bonusNumber) {
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

    private void makeMove(int bonusNumber) {
        makeMove(receiver.receive(null, -1));
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

    public void setGameDrawer(TextGameDrawer gameDrawer) {
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
        }

        private void createGame() {
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
            generateTiles();
            generatePlayers();
        }

        private void generatePlayers() {
            generatePlayer(0, 38, redTiles, PlayerColor.RED);
            generatePlayer(1, 21, blueTiles, PlayerColor.BLUE);
            generatePlayer(2, 4, yellowTiles, PlayerColor.YELLOW);
            generatePlayer(3, 55, greenTiles, PlayerColor.GREEN);
        }

        private void generatePlayer(int index, int startTileIndex, Tile[] colorTiles, PlayerColor color) {
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

            players[index] = player;
        }

    /*private void generatePlayersOld() {
        Player redPlayer = new Player();
        Player bluePlayer = new Player();
        Player yellowPlayer = new Player();
        Player greenPlayer = new Player();

        Tile[] redPlayerTiles = new Tile[Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT; i++) {
            int tileIndex = (38 + i) % 68;
            redPlayerTiles[i] = normalTiles[tileIndex];
        }
        for (int i = 56, j = 0; i < 64; i++, j++) {
            redPlayerTiles[i] = redTiles[j];
        }
        redPlayer.setTiles(redPlayerTiles);
        Piece[] redPlayerPieces = new Piece[4];
        for (int i = 0; i < redPlayerPieces.length; i++) {
            Piece piece = new Piece();
            piece.setPlayer(redPlayer);
            piece.setTilesPassed(-1);
            piece.setCurrentTile(null);
            piece.setHasFinished(false);
            piece.setHomeState(HomeState.IN);
            redPlayerPieces[i] = piece;
        }
        redPlayerPieces[0].setHomeState(HomeState.OUT);
        redPlayerPieces[0].setTilesPassed(0);
        redPlayerPieces[0].setCurrentTile(redPlayerTiles[0]);
        redPlayerTiles[0].setFirstPiece(redPlayerPieces[0]);
        redPlayer.setPieces(redPlayerPieces);
        redPlayer.setColor(PlayerColor.RED);

        players[0] = redPlayer;
        players[1] = bluePlayer;
        players[2] = yellowPlayer;
        players[3] = greenPlayer;
    }*/

        private void generateTiles() {
            HashSet<Integer> safeTilesIndexes = new HashSet<>(List.of(4, 11, 16, 21, 28, 33, 38, 45, 50, 55, 62,67));

            for (int i = 0; i < normalTiles.length; i++) {
                Tile tile = new Tile();
                tile.setIndex(i);
                tile.setSafe(safeTilesIndexes.contains(i));
                normalTiles[i] = tile;
            }

            for (int i = 0; i < redTiles.length; i++) {
                redTiles[i] = new Tile();
                redTiles[i].setIndex(68 + i);
                blueTiles[i] = new Tile();
                blueTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT);
                yellowTiles[i] = new Tile();
                yellowTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 2);
                greenTiles[i] = new Tile();
                greenTiles[i].setIndex(68 + i + Piece.COLORED_TILES_COUNT * 3);
            }
        }
    }
}
