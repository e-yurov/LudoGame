package ru.vsu.cs.yurov.logics;

import javafx.scene.text.Text;
import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

import java.util.*;

public class Game {
    public static final int NORMAL_TILES_COUNT = 68;
    public static final int COLOR_ROAD_TILES_COUNT = 8;

    public static final int PIECE_NORMAL_TILES_COUNT = 64;
    public static final int PIECE_ALL_TILES_COUNT = 72;

    private Player[] players;
    private int currentPlayerIndex;
    private Die die;

    private final Tile[] normalTiles;
    private final Tile[] redTiles;
    private final Tile[] blueTiles;
    private final Tile[] yellowTiles;
    private final Tile[] greenTiles;

    private Text text;
    private int currentNumber = -1;
    private Game() {
        players = new Player[4];
        currentPlayerIndex = 0;
        die = new Die();
        normalTiles = new Tile[NORMAL_TILES_COUNT];
        redTiles = new Tile[COLOR_ROAD_TILES_COUNT];
        blueTiles = new Tile[COLOR_ROAD_TILES_COUNT];
        yellowTiles = new Tile[COLOR_ROAD_TILES_COUNT];
        greenTiles = new Tile[COLOR_ROAD_TILES_COUNT];
    }

    public void calculateBeforeMove(int bonusNumber) {
        Player player = players[currentPlayerIndex];
        currentNumber = bonusNumber <= 0 ? die.getNumber() : bonusNumber;
        if (currentNumber == 6 && player.isAllOut()) {
            currentNumber++;
        }
        PieceMoveAbilityComputer.compute(player, currentNumber);

        if (currentNumber <= 6) {
            text.setText("Die roll: " + currentNumber + "\nPlayer " + player.getColor());
        } else {
            text.setText("BonusMove: " + currentNumber + "\nPlayer " + player.getColor());
        }
    }

    public int makeMove(Piece piece) {
        Player player = players[currentPlayerIndex];

        player.setLastPiece(piece);
        PieceActionType actionType = PieceActionTypeDefiner.defineAction(piece, currentNumber);
        actionType.getAction().perform(piece, currentNumber);

        if (actionType == PieceActionType.HIT) {
            return 20;
        }
        if (actionType == PieceActionType.FINISH) {
            return 10;
        }
        if (currentNumber == 6 || currentNumber == 7) {
            return 0;
        }
        return -1;
    }

    public void selectNextPlayer() {
        currentPlayer().setSixCounter(0);
        currentPlayerIndex++;
        currentPlayerIndex %= players.length;
    }

    public PlayerColor getFinishedPlayerColor() {
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
                game.normalTiles[i] = new Tile(i, safeTilesIndexes.contains(i));
            }

            for (int i = 0; i < game.redTiles.length; i++) {
                game.redTiles[i] = new Tile(68 + i);
                game.blueTiles[i] = new Tile(68 + i + COLOR_ROAD_TILES_COUNT);
                game.yellowTiles[i] = new Tile(68 + i + COLOR_ROAD_TILES_COUNT * 2);
                game.greenTiles[i] = new Tile(68 + i + COLOR_ROAD_TILES_COUNT * 3);
            }
        }

        private static void generatePlayers(Game game) {
            game.players[0] = generatePlayer(38, game.redTiles, PlayerColor.RED, game.normalTiles);
            game.players[1] = generatePlayer(21, game.blueTiles, PlayerColor.BLUE, game.normalTiles);
            game.players[2] = generatePlayer(4, game.yellowTiles, PlayerColor.YELLOW, game.normalTiles);
            game.players[3] = generatePlayer(55, game.greenTiles, PlayerColor.GREEN, game.normalTiles);
        }

        private static Player generatePlayer(int startTileIndex, Tile[] colorTiles, PlayerColor color, Tile[] normalTiles) {
            Tile[] playerTiles = new Tile[PIECE_ALL_TILES_COUNT];
            for (int i = 0; i < PIECE_NORMAL_TILES_COUNT; i++) {
                int tileIndex = (startTileIndex + i) % 68;
                playerTiles[i] = normalTiles[tileIndex];
            }
            for (int i = 64, j = 0; i < PIECE_ALL_TILES_COUNT; i++, j++) {
                playerTiles[i] = colorTiles[j];
            }

            Player player = new Player(playerTiles, color);

            Piece[] playerPieces = new Piece[4];
            playerPieces[0] = new Piece(playerTiles[0], HomeState.OUT, player, 0, 0);
            for (int i = 1; i < playerPieces.length; i++) {
                playerPieces[i] = new Piece(null, HomeState.IN, player, -1, i);
            }
            playerTiles[0].setFirstPiece(playerPieces[0]);
            player.setPieces(playerPieces);

            return player;
        }
    }


    public void setDie(Die die) {
        this.die = die;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
