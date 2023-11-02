package ru.vsu.cs.yurov.graphics;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Player;

public class GraphicComponentsGenerator {
    private static final int TILE_LONG_SIDE = (int) Math.round(220 * ((double)GameApplication.BOARD_SIZE / 2048));
    private static final int TILE_SHORT_SIDE = (int) Math.round(96 * ((double)GameApplication.BOARD_SIZE / 2048));

    static GraphicTile[] generateGraphicTiles() {
        int tilesCount = 100;
        GraphicTile[] result = new GraphicTile[tilesCount];
        for (int i = 0; i < 7; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498, 845 - i * TILE_SHORT_SIDE, TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[7] = new GraphicTile(TileForm.CORNER_HORIZONTAL_LEFT, 7,
                498, 845 - TILE_SHORT_SIDE * 7 - 4,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        result[8] = new GraphicTile(TileForm.CORNER_VERTICAL_TOP, 8,
                552 - 4, 498,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        for (int i = 9; i < 16; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    552 + TILE_SHORT_SIDE * (i - 8), 498, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[16]= new GraphicTile(TileForm.VERTICAL, 16,
                552 + TILE_SHORT_SIDE * 7, 498 - TILE_LONG_SIDE, TILE_SHORT_SIDE, TILE_LONG_SIDE);

        for (int i = 17; i < 24; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    846 - TILE_SHORT_SIDE * (i - 17), 305, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[24] = new GraphicTile(TileForm.CORNER_VERTICAL_BOTTOM, 24,
                847 - TILE_SHORT_SIDE * 7 - 4, 305,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        result[25] = new GraphicTile(TileForm.CORNER_HORIZONTAL_LEFT, 25,
                498, 306,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        for (int i = 26; i < 33; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498, 306 - TILE_SHORT_SIDE * (i - 25), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[33] = new GraphicTile(TileForm.HORIZONTAL, 33,
                498 - TILE_LONG_SIDE, 12, TILE_LONG_SIDE, TILE_SHORT_SIDE);

        for (int i = 34; i < 41; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    305, 12 + TILE_SHORT_SIDE * (i - 34), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[41] = new GraphicTile(TileForm.CORNER_HORIZONTAL_RIGHT, 41,
                305, 12 + TILE_SHORT_SIDE * 7,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        result[42] = new GraphicTile(TileForm.CORNER_VERTICAL_BOTTOM, 42,
                306, 305,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        for (int i = 43; i < 50; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    306 - TILE_SHORT_SIDE * (i - 42), 305, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[50] = new GraphicTile(TileForm.VERTICAL, 50,
                12, 305 + TILE_LONG_SIDE, TILE_SHORT_SIDE, TILE_LONG_SIDE);

        for (int i = 51; i < 58; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    12 + TILE_SHORT_SIDE * (i - 51), 498, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        result[58] = new GraphicTile(TileForm.CORNER_VERTICAL_TOP, 58,
                12 + TILE_SHORT_SIDE * 7, 498,
                TILE_SHORT_SIDE + 4, TILE_LONG_SIDE);
        result[59] = new GraphicTile(TileForm.CORNER_HORIZONTAL_RIGHT, 59,
                305, 551 - 4,
                TILE_LONG_SIDE, TILE_SHORT_SIDE + 4);
        for (int i = 60; i < 67; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    305, 551 + TILE_SHORT_SIDE * (i - 59), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        result[67] = new GraphicTile(TileForm.HORIZONTAL, 67,
                401, 887 - TILE_SHORT_SIDE, TILE_LONG_SIDE, TILE_SHORT_SIDE);


        //color tiles
        //red
        for (int i = 68; i < 76; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    498 - TILE_LONG_SIDE, 54 + TILE_SHORT_SIDE * (i - 68), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        //blue
        for (int i = 76; i < 84; i++) {
            result[i]= new GraphicTile(TileForm.VERTICAL, i,
                    804 - TILE_SHORT_SIDE * (i - 76), 401, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        //yellow
        for (int i = 84; i < 92; i++) {
            result[i] = new GraphicTile(TileForm.HORIZONTAL, i,
                    401, 803 - TILE_SHORT_SIDE * (i - 84), TILE_LONG_SIDE, TILE_SHORT_SIDE);
        }
        //green
        for (int i = 92; i < 100; i++) {
            result[i] = new GraphicTile(TileForm.VERTICAL, i,
                    54 + TILE_SHORT_SIDE * (i - 92), 402, TILE_SHORT_SIDE, TILE_LONG_SIDE);
        }
        return result;
    }

    static GraphicPiece[] generateGraphicPieces(Piece[] pieces, GameApplication application) {
        GraphicPiece[] result = new GraphicPiece[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            result[i] = new GraphicPiece(pieces[i].getPlayer().getColor().getRgbColor(), application);
            result[i].setPiece(pieces[i]);
        }

        return result;
    }

    static public Text[] generatePlayersText(Player[] players) {
        Text[] texts = new Text[players.length];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = new Text("0");
            texts[i].setFont(Font.font(20D));
            players[i].setTextFinishedPiecesCounter(texts[i]);
        }

        //red
        texts[0].setX(465);
        texts[0].setY(415);

        //blue
        texts[1].setX(515);
        texts[1].setY(465);

        //yellow
        texts[2].setX(465);
        texts[2].setY(515);

        //green
        texts[3].setX(415);
        texts[3].setY(465);

        return texts;
    }
}
