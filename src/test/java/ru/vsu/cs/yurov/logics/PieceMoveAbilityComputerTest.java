package ru.vsu.cs.yurov.logics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class PieceMoveAbilityComputerTest {
    @Test
    void testSix() {
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile finalTile = new Tile();

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), finalTile});
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer.compute(player, 6);

        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testSixButHasBlockOnEnd() {
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile finalTile = new Tile();
        finalTile.setFirstPiece(new Piece());
        finalTile.setSecondPiece(new Piece());

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), finalTile});
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer.compute(player, 6);

        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testSixAndOnePieceInBlock() {
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile firstTile = new Tile();
        Tile finalTile = new Tile();

        Piece piece1 = new Piece();
        piece1.setTilesPassed(0);
        piece1.setCurrentTile(zeroTile);
        piece1.setHomeState(HomeState.OUT);
        Piece piece2 = new Piece();
        piece2.setTilesPassed(1);
        piece2.setCurrentTile(firstTile);
        piece2.setHomeState(HomeState.OUT);

        zeroTile.setFirstPiece(piece1);
        zeroTile.setSecondPiece(new Piece());
        firstTile.setFirstPiece(piece2);

        player.setTiles(new Tile[]{zeroTile, firstTile, new Tile(), new Tile(), new Tile(), new Tile(), finalTile, new Tile()});
        player.setPieces(new Piece[]{piece1, piece2});

        PieceMoveAbilityComputer.compute(player, 6);

        Assertions.assertTrue(piece1.canMove());
        Assertions.assertFalse(piece2.canMove());
    }

    @Test
    void testHasBlockOnRoad() {
        Player player = new Player();

        Tile zeroTile = new Tile();
        Tile blockTile = new Tile();
        blockTile.setFirstPiece(new Piece());
        blockTile.setSecondPiece(new Piece());

        Piece piece = new Piece();
        piece.setTilesPassed(0);
        piece.setCurrentTile(zeroTile);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), blockTile, new Tile(), new Tile()});
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer.compute(player, 5);

        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testOutOfRoad() {
        Player player = new Player();

        Tile zeroTile = new Tile();

        Piece piece = new Piece();
        piece.setTilesPassed(70);
        piece.setCurrentTile(zeroTile);

        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        player.setTiles(playerTiles);
        for (int i = 0; i < 70; i++) {
            playerTiles[i] = new Tile();
        }
        playerTiles[70] = zeroTile;
        playerTiles[71] = new Tile();
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer.compute(player, 1);
        Assertions.assertTrue(piece.canMove());

        PieceMoveAbilityComputer.compute(player, 2);
        Assertions.assertFalse(piece.canMove());

        PieceMoveAbilityComputer.compute(player, 6);
        Assertions.assertFalse(piece.canMove());
    }

    @Test
    void testPiecesCanExitHome() {
        Player player = new Player();

        Piece piece = new Piece();
        piece.setHomeState(HomeState.IN);

        player.setTiles(new Tile[]{new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile()});
        player.setPieces(new Piece[]{piece});

        PieceMoveAbilityComputer.compute(player, 6);
        Assertions.assertTrue(piece.canMove());

        PieceMoveAbilityComputer.compute(player, 5);
        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testCanNotExitHomeAndCanMoveOnBoard() {
        Player player = new Player();

        Tile zeroTile = new Tile();
        zeroTile.setFirstPiece(new Piece());
        zeroTile.setSecondPiece(new Piece());

        Piece piece1 = new Piece();
        piece1.setTilesPassed(0);
        piece1.setCurrentTile(zeroTile);
        Piece piece2 = new Piece();
        piece2.setHomeState(HomeState.IN);

        player.setTiles(new Tile[]{zeroTile, new Tile(), new Tile(), new Tile(), new Tile(), new Tile(), new Tile()});
        player.setPieces(new Piece[]{piece1, piece2});

        PieceMoveAbilityComputer.compute(player, 5);
        Assertions.assertTrue(piece1.canMove());
        Assertions.assertFalse(piece2.canMove());
    }

    @Test
    void testBonusMoveWithBlocksOnRoad() {
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

        PieceMoveAbilityComputer.compute(player, 10);
        Assertions.assertTrue(piece.canMove());

        PieceMoveAbilityComputer.compute(player, 20);
        Assertions.assertTrue(piece.canMove());
    }

    @Test
    void testBonusMoveWithBlocksOnEnd() {
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

        PieceMoveAbilityComputer.compute(player, 10);
        Assertions.assertFalse(piece.canMove());

        PieceMoveAbilityComputer.compute(player, 20);
        Assertions.assertFalse(piece.canMove());
    }
}
