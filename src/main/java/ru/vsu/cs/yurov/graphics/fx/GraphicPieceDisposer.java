package ru.vsu.cs.yurov.graphics.fx;

import ru.vsu.cs.yurov.logics.Tile;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class GraphicPieceDisposer {
    // long 97
    // short 42

    public void dispose(GraphicTile[] tiles, GraphicPiece graphicPiece) {
        if (graphicPiece.getPiece().hasFinished()) {
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
            return;
        }

        if (graphicPiece.getPiece().getHomeState() == HomeState.IN) {
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
            return;
        }
        Tile tile = graphicPiece.getPiece().getCurrentTile();
        GraphicTile graphicTile = tiles[tile.getIndex()];

        switch (graphicTile.getForm()) {
            case HORIZONTAL -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterX(graphicTile.getX() + 30);
                    } else {
                        graphicPiece.setCenterX(graphicTile.getX() + 67);
                    }
                } else {
                    graphicPiece.setCenterX(graphicTile.getX() + 48.5D);
                }
                graphicPiece.setCenterY(graphicTile.getY() + 21);
                graphicPiece.setRadius(15);
            }
            case VERTICAL -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterY(graphicTile.getY() + 30);
                    } else {
                        graphicPiece.setCenterY(graphicTile.getY() + 67);
                    }
                } else {
                    graphicPiece.setCenterY(graphicTile.getY() + 48.5);
                }
                graphicPiece.setCenterX(graphicTile.getX() + 21);
                graphicPiece.setRadius(15);
            }

            case CORNER_HORIZONTAL_LEFT -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterX(graphicTile.getX() + 18);
                    } else {
                        graphicPiece.setCenterX(graphicTile.getX() + 46);
                    }
                } else {
                    graphicPiece.setCenterX(graphicTile.getX() + 48.5);
                }

                graphicPiece.setCenterY(graphicTile.getY() + 23);
                graphicPiece.setRadius(12);
            }
            case CORNER_HORIZONTAL_RIGHT -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterX(graphicTile.getX() + graphicTile.getWidth() - 18);
                    } else {
                        graphicPiece.setCenterX(graphicTile.getX() + graphicTile.getWidth() - 46);
                    }
                } else {
                    graphicPiece.setCenterX(graphicTile.getX() + 48.5);
                }

                graphicPiece.setCenterY(graphicTile.getY() + 23);
                graphicPiece.setRadius(12);
            }
            case CORNER_VERTICAL_TOP -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterY(graphicTile.getY() + 18);
                    } else {
                        graphicPiece.setCenterY(graphicTile.getY() + 46);
                    }
                } else {
                    graphicPiece.setCenterY(graphicTile.getY() + 48.5);
                }
                graphicPiece.setCenterX(graphicTile.getX() + 23);
                graphicPiece.setRadius(12);
            }
            case CORNER_VERTICAL_BOTTOM -> {
                if (tile.isFull()) {
                    if (graphicPiece.getPiece() == tile.getFirstPiece()) {
                        graphicPiece.setCenterY(graphicTile.getY() + graphicTile.getHeight() - 18);
                    } else {
                        graphicPiece.setCenterY(graphicTile.getY() + graphicTile.getHeight() - 46);
                    }
                } else {
                    graphicPiece.setCenterY(graphicTile.getY() + 48.5);
                }
                graphicPiece.setCenterX(graphicTile.getX() + 23);
                graphicPiece.setRadius(12);
            }
        }
    }

    private GraphicTile calculateTile(GraphicTile[] tiles, int index) {
        return tiles[index];
    }
}
