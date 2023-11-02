package ru.vsu.cs.yurov.logics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class PieceMoveAbilityComputerTest {
    @Test
    void testSix() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile finalTile = new Tile();

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.tiles = new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), finalTile};
        player.pieces = new Piece[]{piece};

        pieceMoveAbilityComputer.compute(6, player);

        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testSixButHasBlockOnEnd() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile finalTile = new Tile();
        finalTile.setFirstPiece(new Piece());
        finalTile.setSecondPiece(new Piece());

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.tiles = new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), finalTile};
        player.pieces = new Piece[]{piece};

        pieceMoveAbilityComputer.compute(6, player);

        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testSixAndOnePieceInBlock() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile firstTile = new Tile();
        Tile finalTile = new Tile();

        Piece piece1 = new Piece();
        piece1.setTilesPassed(0);
        piece1.setCurrentTile(zeroTile);
        Piece piece2 = new Piece();
        piece2.setTilesPassed(1);
        piece2.setCurrentTile(firstTile);

        zeroTile.setFirstPiece(piece1);
        zeroTile.setSecondPiece(new Piece());
        firstTile.setFirstPiece(piece2);

        player.tiles = new Tile[]{zeroTile, firstTile, new Tile(), new Tile(), new Tile(), new Tile(), finalTile, new Tile()};
        player.pieces = new Piece[]{piece1, piece2};

        pieceMoveAbilityComputer.compute(6, player);

        Assertions.assertTrue(piece1.canMove());
        Assertions.assertFalse(piece2.canMove());
    }

    @Test
    void testHasBlockOnRoad() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile blockTile = new Tile();
        blockTile.setFirstPiece(new Piece());
        blockTile.setSecondPiece(new Piece());

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.tiles = new Tile[]{zeroTile, new Tile(), new Tile(), blockTile, new Tile(), new Tile()};
        player.pieces = new Piece[]{piece};

        pieceMoveAbilityComputer.compute(5, player);

        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testOutOfRoad() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();

        Piece piece = new Piece();
        piece.setTilesPassed(70);
        piece.setCurrentTile(zeroTile);

        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        player.tiles = playerTiles;
        for (int i = 0; i < 70; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[70] = zeroTile;
        playerTiles[71] = new Tile();
        player.pieces = new Piece[]{piece};

        pieceMoveAbilityComputer.compute(1, player);
        Assertions.assertTrue(piece.canMove());

        pieceMoveAbilityComputer.compute(2, player);
        Assertions.assertFalse(piece.canMove());

        pieceMoveAbilityComputer.compute(6, player);
        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testPiecesCanExitHome() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Piece piece = new Piece();
        piece.setHomeState(HomeState.IN);

        player.tiles = new Tile[]{new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile()};
        player.pieces = new Piece[]{piece};

        pieceMoveAbilityComputer.compute(6, player);
        Assertions.assertTrue(piece.canMove());

        pieceMoveAbilityComputer.compute(5, player);
        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testCanNotExitHomeAndCanMoveOnBoard() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();

        Tile zeroTile = new Tile();
        zeroTile.setFirstPiece(new Piece());
        zeroTile.setSecondPiece(new Piece());

        Piece piece1 = new Piece();
        piece1.setTilesPassed(0);
        piece1.setCurrentTile(zeroTile);
        Piece piece2 = new Piece();
        piece2.setHomeState(HomeState.IN);

        player.tiles = new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile()};
        player.pieces = new Piece[]{piece1, piece2};

        pieceMoveAbilityComputer.compute(5, player);
        Assertions.assertTrue(piece1.canMove());
        Assertions.assertFalse(piece2.canMove());
    }

    @Test
    void testBonusMoveWithBlocksOnRoad() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();
        Piece piece = new Piece();
        Tile zeroTile = new Tile();
        Tile finishTile = new Tile();
        Tile middleTile = new Tile();

        zeroTile.setFirstPiece(piece);
        middleTile.setFirstPiece(new Piece());
        middleTile.setSecondPiece(new Piece());

        Tile[] playerTiles = new Tile[21];
        for (int i = 1; i < 21; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[0] = zeroTile;
        playerTiles[5] = middleTile;
        playerTiles[20] = finishTile;

        piece.setTilesPassed(0);

        player.setPieces(new Piece[]{piece});
        player.setTiles(playerTiles);

        pieceMoveAbilityComputer.compute(10, player);
        Assertions.assertTrue(piece.canMove());

        pieceMoveAbilityComputer.compute(20, player);
        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testBonusMoveWithBlocksOnEnd() {
        PieceMoveAbilityComputer pieceMoveAbilityComputer = new PieceMoveAbilityComputer();
        Player player = new Player();
        Piece piece = new Piece();
        Tile zeroTile = new Tile();
        Tile finishTile = new Tile();
        Tile middleTile = new Tile();

        zeroTile.setFirstPiece(piece);
        middleTile.setFirstPiece(new Piece());
        middleTile.setSecondPiece(new Piece());
        finishTile.setFirstPiece(new Piece());
        finishTile.setSecondPiece(new Piece());

        Tile[] playerTiles = new Tile[21];
        for (int i = 1; i < 21; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[0] = zeroTile;
        playerTiles[10] = middleTile;
        playerTiles[20] = finishTile;

        piece.setTilesPassed(0);

        player.setPieces(new Piece[]{piece});
        player.setTiles(playerTiles);

        pieceMoveAbilityComputer.compute(10, player);
        Assertions.assertFalse(piece.canMove());

        pieceMoveAbilityComputer.compute(20, player);
        Assertions.assertFalse(piece.canMove());
    }
}