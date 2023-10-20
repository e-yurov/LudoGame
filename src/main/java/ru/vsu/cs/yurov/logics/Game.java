package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.ActionReceiver;
import ru.vsu.cs.yurov.logics.actions.PlayerAction;
import ru.vsu.cs.yurov.logics.actions.PlayerActionType;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayer;
    private Die die;
    private boolean isPlaying;

    private ActionReceiver receiver;
    private Map<PlayerActionType, PlayerAction> playerActionMap;
    private PiecesHandler piecesHandler;

    private Tile[] normalTiles;
    Tile[] redTiles;
    Tile[] blueTiles;
    Tile[] yellowTiles;
    Tile[] greenTiles;

    public Game() {
        board = new Board();
        players = new Player[4];
        currentPlayer = 0;
        die = new Die();
        isPlaying = false;
        receiver = new ActionReceiver();
        playerActionMap = new HashMap<>();
        piecesHandler = new PiecesHandler();
        normalTiles = new Tile[68];
        redTiles = new Tile[7];
    }

    public void start() {
        isPlaying = true;

        int number = die.getNumber();
        Player player = players[currentPlayer];
        piecesHandler.handle(number, player);


        PlayerActionType playerActionType = receiver.receive();
        PlayerAction playerAction = playerActionMap.get(playerActionType);
        playerAction.perform();

        //board.makeMove();
    }
}
