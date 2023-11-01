package ru.vsu.cs.yurov.logics.actions.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.*;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class PieceActionTest {
    PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
    PieceActionTypeDefiner pieceActionTypeDefiner = new PieceActionTypeDefiner();

    Piece piece;
    Player player;
    Tile startTile;
    Tile finalTile;

    @BeforeEach
    void beforeEach() {
        piece = new Piece();
        player = new Player();
        startTile = new Tile();
        finalTile = new Tile();
    }

    @Test
    void testMove() {
        startTile.setFirstPiece(piece);

        piece.setCurrentTile(startTile);
        piece.setTilesPassed(0);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece});
        player.setTiles(new Tile[]{startTile, new Tile(), new Tile(), finalTile});

        pieceMoveAbilityComputer.compute(3, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 3);
        actionType.getAction().perform(piece, 3);

        Assertions.assertAll(
                () -> Assertions.assertNull(startTile.getFirstPiece()),
                () -> Assertions.assertEquals(PieceActionType.MOVE, actionType),
                () -> Assertions.assertEquals(piece, finalTile.getFirstPiece()),
                () -> Assertions.assertEquals(3, piece.getTilesPassed())
        );
    }

    @Test
    void testMoveAndBlockFriendly() {
        Piece friendlyPiece = new Piece();
        friendlyPiece.setPlayer(player);
        friendlyPiece.setCurrentTile(finalTile);
        friendlyPiece.setTilesPassed(3);
        finalTile.setFirstPiece(friendlyPiece);

        startTile.setFirstPiece(piece);

        piece.setCurrentTile(startTile);
        piece.setTilesPassed(0);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece, friendlyPiece});
        player.setTiles(new Tile[]{startTile, new Tile(), new Tile(), finalTile, new Tile(), new Tile(), new Tile()});

        pieceMoveAbilityComputer.compute(3, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 3);
        actionType.getAction().perform(piece, 3);

        Assertions.assertAll(
                () -> Assertions.assertNull(startTile.getFirstPiece()),
                () -> Assertions.assertEquals(PieceActionType.MOVE_AND_BLOCK, actionType),
                () -> Assertions.assertEquals(friendlyPiece, finalTile.getFirstPiece()),
                () -> Assertions.assertEquals(piece, finalTile.getSecondPiece()),
                () -> Assertions.assertEquals(3, piece.getTilesPassed())
        );
    }

    @Test
    void testMoveAndBlockEnemy() {
        Player enemyPlayer = new Player();

        Piece enemyPiece = new Piece();
        enemyPiece.setPlayer(enemyPlayer);
        enemyPiece.setCurrentTile(finalTile);
        enemyPiece.setTilesPassed(0);

        finalTile.setFirstPiece(enemyPiece);
        finalTile.setSafe(true);

        startTile.setFirstPiece(piece);

        piece.setCurrentTile(startTile);
        piece.setTilesPassed(0);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece});
        player.setTiles(new Tile[]{startTile, new Tile(), new Tile(), finalTile});
        player.setColor(PlayerColor.RED);

        enemyPlayer.setPieces(new Piece[]{enemyPiece});
        enemyPlayer.setTiles(new Tile[]{finalTile});

        pieceMoveAbilityComputer.compute(3, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 3);
        actionType.getAction().perform(piece, 3);

        Assertions.assertAll(
                () -> Assertions.assertNull(startTile.getFirstPiece()),
                () -> Assertions.assertEquals(PieceActionType.MOVE_AND_BLOCK, actionType),
                () -> Assertions.assertEquals(enemyPiece, finalTile.getFirstPiece()),
                () -> Assertions.assertEquals(piece, finalTile.getSecondPiece()),
                () -> Assertions.assertEquals(3, piece.getTilesPassed())
        );
    }

    @Test
    void testHit() {
        Player enemyPlayer = new Player();
        Piece enemyPiece = new Piece();
        Piece friendlyPiece = new Piece();

        enemyPiece.setPlayer(enemyPlayer);
        enemyPiece.setCurrentTile(finalTile);
        enemyPiece.setTilesPassed(0);

        finalTile.setFirstPiece(enemyPiece);

        startTile.setFirstPiece(piece);
        startTile.setSecondPiece(friendlyPiece);

        piece.setCurrentTile(startTile);
        piece.setTilesPassed(0);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece});
        player.setTiles(new Tile[]{startTile, new Tile(), new Tile(), finalTile});
        player.setColor(PlayerColor.RED);

        enemyPlayer.setPieces(new Piece[]{enemyPiece});
        enemyPlayer.setTiles(new Tile[]{finalTile});
        enemyPlayer.setColor(PlayerColor.BLUE);

        pieceMoveAbilityComputer.compute(3, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 3);
        actionType.getAction().perform(piece, 3);

        Assertions.assertAll(
                () -> Assertions.assertEquals(friendlyPiece, startTile.getFirstPiece()),
                () -> Assertions.assertNull(startTile.getSecondPiece()),
                () -> Assertions.assertEquals(PieceActionType.HIT, actionType),
                () -> Assertions.assertEquals(piece, finalTile.getFirstPiece()),
                () -> Assertions.assertEquals(HomeState.IN, enemyPiece.getHomeState()),
                () -> Assertions.assertEquals(-1, enemyPiece.getTilesPassed()),
                () -> Assertions.assertNull(enemyPiece.getCurrentTile()),
                () -> Assertions.assertEquals(3, piece.getTilesPassed())
        );
    }

    @Test
    void testFinish() {
        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        for (int i = 0; i < Piece.TILES_COUNT - 2; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[Piece.TILES_COUNT - 2] = startTile;
        playerTiles[Piece.TILES_COUNT - 1] = finalTile;

        Piece friendlyPiece = new Piece();
        startTile.setFirstPiece(friendlyPiece);
        startTile.setSecondPiece(piece);

        piece.setCurrentTile(startTile);
        piece.setTilesPassed(Piece.TILES_COUNT - 2);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece});
        player.setTiles(playerTiles);

        pieceMoveAbilityComputer.compute(1, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 1);
        actionType.getAction().perform(piece, 1);

        Assertions.assertAll(
                () -> Assertions.assertEquals(friendlyPiece, startTile.getFirstPiece()),
                () -> Assertions.assertNull(startTile.getSecondPiece()),
                () -> Assertions.assertEquals(PieceActionType.FINISH, actionType),
                //() -> Assertions.assertEquals(piece, finalTile.getFirstPiece()),
                () -> Assertions.assertEquals(Piece.TILES_COUNT - 1, piece.getTilesPassed())
        );
    }

    @Test
    void testLeaveHome() {
        piece.setHomeState(HomeState.IN);
        piece.setPlayer(player);

        player.setPieces(new Piece[]{piece});
        player.setTiles(new Tile[]{startTile, new Tile(), new Tile(), finalTile});

        pieceMoveAbilityComputer.compute(6, player);
        PieceActionType actionType = pieceActionTypeDefiner.defineAction(piece, 6);
        actionType.getAction().perform(piece, 6);

        Assertions.assertAll(
                () -> Assertions.assertEquals(piece, startTile.getFirstPiece()),
                () -> Assertions.assertEquals(piece.getCurrentTile(), startTile),
                () -> Assertions.assertEquals(PieceActionType.LEAVE_HOME, actionType),
                () -> Assertions.assertEquals(0, piece.getTilesPassed())
        );
    }
}
