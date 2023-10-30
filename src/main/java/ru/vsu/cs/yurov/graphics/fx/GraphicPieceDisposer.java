package ru.vsu.cs.yurov.graphics.fx;

import javafx.scene.paint.Color;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Tile;
import ru.vsu.cs.yurov.logics.actions.HomeState;

public class GraphicPieceDisposer {
    // long 97
    // short 42

    public void dispose(GraphicTile[] tiles, GraphicPiece graphicPiece) {
        if (graphicPiece.getPiece().getHomeState() == HomeState.IN) {
            graphicPiece.setCenterX(450);
            graphicPiece.setCenterY(450);
            return;
        }
        Tile tile = graphicPiece.getPiece().getCurrentTile();
        GraphicTile graphicTile = tiles[tile.getIndex()];
        //GraphicTile graphicTile = calculateTile(tiles, index);

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

        if (graphicPiece.getPiece().canMove()) {
            graphicPiece.setStrokeWidth(0F);
        } else {
            graphicPiece.setStrokeWidth(3F);
        }
    }

    private GraphicTile calculateTile(GraphicTile[] tiles, int index) {
        return tiles[index];
    }
}
