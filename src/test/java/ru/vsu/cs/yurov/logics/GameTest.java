package ru.vsu.cs.yurov.logics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.actions.HomeState;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionReceiver;

public class GameTest {
    @Test
    void test1() {
        Player redPlayer = new Player();
        Player bluePlayer = new Player();
        Tile[] playerTiles = new Tile[Piece.TILES_COUNT];
        for (int i = 1; i < Piece.TILES_COUNT; i++) {
            playerTiles[i] = new Tile();
        }

        Tile startTile = new Tile();
        Tile firstTile = new Tile();
        Tile secondTile = new Tile();
        Tile thirdTile = new Tile();
        Tile finalTile = new Tile();

        playerTiles[8] = startTile;
        playerTiles[11] = firstTile;
        playerTiles[31] = secondTile;
        playerTiles[51] = thirdTile;
        playerTiles[71] = finalTile;

        Piece firstBluePiece = new Piece();
        firstBluePiece.setCurrentTile(firstTile);
        firstBluePiece.setHomeState(HomeState.OUT);
        firstBluePiece.setTilesPassed(11);
        firstBluePiece.setPlayer(bluePlayer);
        firstTile.setFirstPiece(firstBluePiece);
        Piece secondBluePiece = new Piece();
        secondBluePiece.setCurrentTile(secondTile);
        secondBluePiece.setHomeState(HomeState.OUT);
        secondBluePiece.setTilesPassed(31);
        secondBluePiece.setPlayer(bluePlayer);
        secondTile.setFirstPiece(secondBluePiece);
        Piece thirdBluePiece = new Piece();
        thirdBluePiece.setCurrentTile(thirdTile);
        thirdBluePiece.setHomeState(HomeState.OUT);
        thirdBluePiece.setTilesPassed(51);
        thirdBluePiece.setPlayer(bluePlayer);
        thirdTile.setFirstPiece(thirdBluePiece);

        bluePlayer.setPieces(new Piece[]{firstBluePiece, secondBluePiece, thirdBluePiece});
        bluePlayer.setTiles(playerTiles);
        bluePlayer.setColor(PlayerColor.BLUE);

        Piece redPiece = new Piece();
        redPiece.setCurrentTile(startTile);
        redPiece.setHomeState(HomeState.OUT);
        redPiece.setTilesPassed(8);
        redPiece.setPlayer(redPlayer);
        startTile.setFirstPiece(redPiece);

        redPlayer.setPieces(new Piece[]{redPiece});
        redPlayer.setTiles(playerTiles);
        redPlayer.setColor(PlayerColor.RED);

        Game game = new Game();
        game.setDie(new Die(){
            @Override
            public int getNumber() {
                return 3;
            }
        });
        game.setPlayers(new Player[]{redPlayer, bluePlayer});
        game.setCurrentPlayerIndex(0);
        game.setNormalTiles(playerTiles);
        game.setPiecesHandler(new PiecesHandler());
        game.setPieceMoveHandler(new PieceMoveHandler());
        game.setReceiver(new PieceActionReceiver(){
            @Override
            public Piece receive(Player player, int number) {
                return redPiece;
            }
        });

        game.start();

        Assertions.assertAll(
                () -> Assertions.assertEquals(HomeState.IN, firstBluePiece.getHomeState()),
                () -> Assertions.assertEquals(HomeState.IN, secondBluePiece.getHomeState()),
                () -> Assertions.assertEquals(HomeState.IN, thirdBluePiece.getHomeState()),

                () -> Assertions.assertEquals(finalTile, redPiece.getCurrentTile())
        );
    }
}
