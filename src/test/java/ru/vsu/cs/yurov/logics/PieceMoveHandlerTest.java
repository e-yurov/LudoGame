package ru.vsu.cs.yurov.logics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.actions.PieceActionType;

public class PieceMoveHandlerTest {
    PieceMoveHandler pieceMoveHandler = new PieceMoveHandler();
    Piece piece;
    Player player;
    Tile zeroTile;

    @BeforeEach
    void beforeEachTest() {
        //pieceMoveHandler = new PieceMoveHandler();
        piece = new Piece();
        player = new Player();
        zeroTile = new Tile();

        piece.setPlayer(player);
    }

    @Test
    void testFinishOnLast() {
        /*PieceMoveHandler pieceMoveHandler = new PieceMoveHandler();
        Piece piece = new Piece();
        Player player = new Player();
        Tile zeroTile = new Tile();*/

        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - 2; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[Piece.TILES_COUNT - 2] = zeroTile;
        playerTiles[Piece.TILES_COUNT - 1] = new Tile();

        piece.setCurrentTile(zeroTile);
        piece.setTilesPassed(Piece.TILES_COUNT - 2);
        //piece.setPlayer(player);

        player.setTiles(playerTiles);


        Assertions.assertEquals(PieceActionType.FINISH, pieceMoveHandler.handle(piece, 1));
    }

    @Test
    void testFinish() {
        /*PieceMoveHandler pieceMoveHandler = new PieceMoveHandler();
        Piece piece = new Piece();
        Player player = new Player();
        Tile zeroTile = new Tile();*/

        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - 4; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[Piece.TILES_COUNT - 4] = zeroTile;
        playerTiles[Piece.TILES_COUNT - 3] = new Tile();
        playerTiles[Piece.TILES_COUNT - 2] = new Tile();
        playerTiles[Piece.TILES_COUNT - 1] = new Tile();

        piece.setCurrentTile(zeroTile);
        piece.setTilesPassed(Piece.TILES_COUNT - 4);
        //piece.setPlayer(player);

        player.setTiles(playerTiles);

        Assertions.assertEquals(PieceActionType.FINISH, pieceMoveHandler.handle(piece, 3));
    }

    @Test
    void testMoveAndTestHit() {
        /*PieceMoveHandler pieceMoveHandler = new PieceMoveHandler();
        Piece piece = new Piece();
        Player player = new Player();
        Tile zeroTile = new Tile();*/

        Piece enemyPiece = new Piece();
        Player enemyPlayer = new Player();
        Tile finalTile = new Tile();

        player.setColor(PlayerColor.RED);
        enemyPlayer.setColor(PlayerColor.BLUE);
        finalTile.setFirstPiece(enemyPiece);
        enemyPiece.setPlayer(enemyPlayer);

        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), finalTile});

        Assertions.assertEquals(PieceActionType.HIT, pieceMoveHandler.handle(piece, 3));
        Assertions.assertEquals(PieceActionType.MOVE, pieceMoveHandler.handle(piece, 2));
    }

    @Test
    void testMoveAndBlock() {
        Piece friendlyPiece = new Piece();
        Player friendlyPlayer = new Player();
        Tile finalTile = new Tile();

        player.setColor(PlayerColor.RED);
        friendlyPlayer.setColor(PlayerColor.RED);
        finalTile.setFirstPiece(friendlyPiece);
        friendlyPiece.setPlayer(friendlyPlayer);

        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), finalTile});

        Assertions.assertEquals(PieceActionType.MOVE_AND_BLOCK, pieceMoveHandler.handle(piece, 3));
        Assertions.assertEquals(PieceActionType.MOVE, pieceMoveHandler.handle(piece, 2));
    }
}
