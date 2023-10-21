package ru.vsu.cs.yurov.logics.actions.piece;

import org.junit.jupiter.api.Test;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Player;
import ru.vsu.cs.yurov.logics.Tile;

public class PieceActionTest {
    @Test
    void test1() {
        Piece piece1 = new Piece();
        //Piece piece2 = new Piece();
        Player player = new Player();
        Tile zeroTile = new Tile();

        piece1.setCurrentTile(zeroTile);
        piece1.setTilesPassed(0);

        player.setPieces(new Piece[]{piece1});
        //player.setTiles();
    }

}
