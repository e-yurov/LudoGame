package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;

public class Piece {
    public static final int TILES_COUNT = 72;
    public static final int COLORED_TILES_COUNT = 8;
    private Tile currentTile;
    private boolean isOnColorTrack;
    private boolean canMove;
    private HomeState homeState = HomeState.OUT;
    private int tilesPassed = -1;
    private Player player;
    private boolean hasFinished = false;

    public Tile getNextTile(int number) {
        return this.getPlayer().getTiles()[tilesPassed + number];
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public HomeState getHomeState() {
        return homeState;
    }

    public void setHomeState(HomeState homeState) {
        this.homeState = homeState;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public int getTilesPassed() {
        return tilesPassed;
    }

    public void setTilesPassed(int tilesPassed) {
        this.tilesPassed = tilesPassed;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public void bust() {
        currentTile.removePiece(this);
        tilesPassed = -1;
        currentTile = null;
        homeState = HomeState.IN;
        player.setSixCounter(0);
    }
}
