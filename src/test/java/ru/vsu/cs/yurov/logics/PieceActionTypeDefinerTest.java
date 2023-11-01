package ru.vsu.cs.yurov.logics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionType;

public class PieceActionTypeDefinerTest {
    private PieceActionTypeDefiner pieceActionTypeDefiner = new PieceActionTypeDefiner();
    private Piece piece;
    private Player player;
    private Tile zeroTile;

    @BeforeEach
    void beforeEachTest() {
        //pieceActionTypeDefiner = new PieceActionTypeDefiner();
        piece = new Piece();
        piece.setCanMove(true);
        player = new Player();
        zeroTile = new Tile();

        piece.setPlayer(player);
    }

    @Test
    void testFinishOnLast() {
        /*PieceActionTypeDefiner pieceActionTypeDefiner = new PieceActionTypeDefiner();
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


        Assertions.assertEquals(PieceActionType.FINISH, pieceActionTypeDefiner.handle(piece, 1));
    }

    @Test
    void testFinish() {
        /*PieceActionTypeDefiner pieceActionTypeDefiner = new PieceActionTypeDefiner();
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

        Assertions.assertEquals(PieceActionType.FINISH, pieceActionTypeDefiner.handle(piece, 3));
    }

    @Test
    void testMoveAndTestHit() {
        /*PieceActionTypeDefiner pieceActionTypeDefiner = new PieceActionTypeDefiner();
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

        Assertions.assertEquals(PieceActionType.HIT, pieceActionTypeDefiner.handle(piece, 3));
        Assertions.assertEquals(PieceActionType.MOVE, pieceActionTypeDefiner.handle(piece, 2));
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

        Assertions.assertEquals(PieceActionType.MOVE_AND_BLOCK, pieceActionTypeDefiner.handle(piece, 3));
        Assertions.assertEquals(PieceActionType.MOVE, pieceActionTypeDefiner.handle(piece, 2));
    }

    @Test
    void testDoNothing() {
        Tile finalTile = new Tile();
        finalTile.setFirstPiece(new Piece());
        finalTile.setSecondPiece(new Piece());

        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), finalTile});
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        pieceMoveAbilityComputer.handle(3, player);

        Assertions.assertEquals(PieceActionType.DO_NOTHING, pieceActionTypeDefiner.handle(piece, 3));
    }

    @Test
    void testCanLeaveHome() {
        piece.setHomeState(HomeState.IN);

        Assertions.assertEquals(PieceActionType.LEAVE_HOME, pieceActionTypeDefiner.handle(piece, 6));
    }
}
