package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.graphics.TextGameDrawer;
import ru.vsu.cs.yurov.logics.actions.ActionReceiver;
import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.PlayerAction;
import ru.vsu.cs.yurov.logics.actions.PlayerActionType;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionReceiver;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

import java.util.HashMap;
import java.util.Map;

public class Game {
    //private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private Die die;
    private boolean isPlaying;

    private PieceMoveHandler pieceMoveHandler;
    private PieceActionReceiver receiver;
    //private Map<PlayerActionType, PlayerAction> playerActionMap;
    private PiecesHandler piecesHandler;

    private Tile[] normalTiles;
    Tile[] redTiles;
    Tile[] blueTiles;
    Tile[] yellowTiles;
    Tile[] greenTiles;

    private TextGameDrawer gameDrawer;
    public Game() {
        createGame();
        /*board = new Board();
        players = new Player[4];
        currentPlayerIndex = 0;
        die = new Die();
        isPlaying = false;
        receiver = new PieceActionReceiver();
        playerActionMap = new HashMap<>();
        piecesHandler = new PiecesHandler();
        normalTiles = new Tile[68];
        redTiles = new Tile[7];*/
    }

    private void createGame() {
        players = new Player[4];
        currentPlayerIndex = 0;
        die = new Die();
        pieceMoveHandler= new PieceMoveHandler();
        piecesHandler = new PiecesHandler();
        normalTiles = new Tile[Piece.TILES_COUNT];
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

        Tile[] playerTiles = new Tile[Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - Piece.COLORED_TILES_COUNT; i++) {
            int tileIndex = (startTileIndex + i) % 68;
            playerTiles[i] = normalTiles[tileIndex];
        }
        for (int i = 56, j = 0; i < 64; i++, j++) {
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
            playerPieces[i] = piece;
        }
        playerPieces[0].setHomeState(HomeState.OUT);
        playerPieces[0].setTilesPassed(0);
        playerPieces[0].setCurrentTile(playerTiles[0]);
        playerTiles[0].setFirstPiece(playerPieces[0]);
        player.setPieces(playerPieces);
        player.setColor(color);

        players[index] = player;
    }

    private void generatePlayersOld() {
        /*for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }*/
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
    }

    private void generateTiles() {
        for (int i = 0; i < normalTiles.length; i++) {
            normalTiles[i] = new Tile();
        }

        for (int i = 0; i < redTiles.length; i++) {
            redTiles[i] = new Tile();
            blueTiles[i] = new Tile();
            yellowTiles[i] = new Tile();
            greenTiles[i] = new Tile();
        }
    }

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

        //piecesHandler.handle(number, player);


        //PlayerActionType playerActionType = receiver.receive();
        //PlayerAction playerAction = playerActionMap.get(playerActionType);
        //playerAction.perform();

        //board.makeMove();
    }

    private void makeMove(int bonusNumber) {
        Player player = players[currentPlayerIndex];
        int number = (bonusNumber > 0) ? bonusNumber : die.getNumber();
        if (number == 6 && player.getSixCounter() == 2) {
            player.getLastPiece().bust();
        }
        piecesHandler.handle(number, player);

        if (!player.canMove()) {
            return;
        }

        Piece piece = receiver.receive(player, number);
        player.setLastPiece(piece);
        PieceActionType actionType = pieceMoveHandler.handle(piece, number);
        actionType.getAction().perform(piece, number);

        if (actionType == PieceActionType.HIT) {
            makeMove(20);
        }
        if (actionType == PieceActionType.FINISH) {
            makeMove(10);
        }
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


    public void setPieceMoveHandler(PieceMoveHandler pieceMoveHandler) {
        this.pieceMoveHandler = pieceMoveHandler;
    }

    public void setReceiver(PieceActionReceiver receiver) {
        this.receiver = receiver;
    }

    public void setPiecesHandler(PiecesHandler piecesHandler) {
        this.piecesHandler = piecesHandler;
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

    public PieceMoveHandler getPieceMoveHandler() {
        return pieceMoveHandler;
    }

    public PieceActionReceiver getReceiver() {
        return receiver;
    }

    public PiecesHandler getPiecesHandler() {
        return piecesHandler;
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
}
