package ru.vsu.cs.yurov.graphics;

import ru.vsu.cs.yurov.logics.Player;
import ru.vsu.cs.yurov.logics.Tile;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class GameDrawer {
    private GameDrawer() {}

    public static void drawGame(GraphicPiece[] graphicPieces, GraphicTile[] graphicTiles, Player currentPlayer) {
        for (GraphicPiece graphicPiece: graphicPieces) {
            dispose(graphicTiles, graphicPiece);

            if (graphicPiece.getPiece().canMove() &&
                    graphicPiece.getPiece().getPlayer() == currentPlayer) {
                graphicPiece.setStrokeWidth(3F);
            } else {
                graphicPiece.setStrokeWidth(0F);
            }
        }
    }

    private static void dispose(GraphicTile[] graphicTiles, GraphicPiece graphicPiece) {
        if (graphicPiece.getPiece().hasFinished()) {
            disposeFinished(graphicPiece);
            return;
        }

        if (graphicPiece.getPiece().getHomeState() == HomeState.IN) {
            disposeInHome(graphicPiece);
            return;
        }

        Tile tile = graphicPiece.getPiece().getCurrentTile();
        GraphicTile graphicTile = graphicTiles[tile.getIndex()];
        disposeNormal(graphicTile, graphicPiece);
    }

    private static void disposeFinished(GraphicPiece graphicPiece) {
        int x = 450, y = 450, distance = 50;
        switch (graphicPiece.getPiece().getPlayer().getColor()) {
            case RED -> y -= distance;
            case BLUE -> x += distance;
            case YELLOW -> y += distance;
            case GREEN -> x -= distance;
        }

        graphicPiece.setCenterX(x);
        graphicPiece.setCenterY(y);
        graphicPiece.setRadius(15D);
    }

    private static void disposeInHome(GraphicPiece graphicPiece) {
        int x = 0, y = 0;

        switch (graphicPiece.getPiece().getPlayer().getColor()) {
            case RED -> {
                x = 159;
                y = 159;
            }
            case BLUE -> {
                x = 741;
                y = 159;
            }
            case YELLOW -> {
                x = 741;
                y = 741;
            }
            case GREEN -> {
                x = 159;
                y = 741;
            }
        }

        int distance = 60;
        switch (graphicPiece.getPiece().getIndex()) {
            case 0 -> {
                x -= distance;
                y -= distance;
            }
            case 1 -> {
                x += distance;
                y -= distance;
            }
            case 2 -> {
                x -= distance;
                y += distance;
            }
            case 3 -> {
                x += distance;
                y += distance;
            }
        }

        graphicPiece.setCenterX(x);
        graphicPiece.setCenterY(y);
        graphicPiece.setRadius(15D);
    }


    private static void disposeNormal(GraphicTile graphicTile, GraphicPiece graphicPiece) {
        Tile tile = graphicPiece.getPiece().getCurrentTile();

        switch (graphicTile.getForm()) {
            case HORIZONTAL -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        30, 67, 48.5,
                        21, 15, false);

            case VERTICAL -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        30, 67, 48.5,
                        21, 15, true);

            case CORNER_HORIZONTAL_LEFT -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        18, 46, 48.5,
                        23, 12, false);

            case CORNER_HORIZONTAL_RIGHT -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        graphicTile.getWidth() - 18, graphicTile.getWidth() - 46, 48.5,
                        23, 12, false);

            case CORNER_VERTICAL_TOP -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        18, 46, 48.5,
                        23, 12, true);

            case CORNER_VERTICAL_BOTTOM -> disposeSingleNormal(tile, graphicTile, graphicPiece,
                        graphicTile.getHeight() - 18, graphicTile.getHeight() - 46, 48.5,
                        23, 12, true);
        }
    }

    private static void disposeSingleNormal(Tile tile, GraphicTile graphicTile, GraphicPiece graphicPiece,
                                     double xLeft, double xRight, double xCenter,
                                     double yCenter, double radius, boolean isNeedToSwap) {
        double x = graphicTile.getX();
        double y = graphicTile.getY();
        if (isNeedToSwap) {
            double temp = x;
            x = y;
            y = temp;
        }

        if (tile.isFull()) {
            x += (graphicPiece.getPiece() == tile.getFirstPiece()) ? xLeft : xRight;
        } else {
            x += xCenter;
        }
        y += yCenter;

        if (isNeedToSwap) {
            double temp = x;
            x = y;
            y = temp;
        }

        graphicPiece.setCenterX(x);
        graphicPiece.setCenterY(y);
        graphicPiece.setRadius(radius);
    }
}
