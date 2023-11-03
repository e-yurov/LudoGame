package ru.vsu.cs.yurov.logics;

import ru.vsu.cs.yurov.logics.actions.HomeState;

public class Piece {
    public static final int TILES_COUNT = 72;

    private Tile currentTile;
    private HomeState homeState;
    private Player player;

    private boolean canMove;
    private boolean hasFinished;

    private int tilesPassed;
    private int index;


    public Piece(Tile currentTile, HomeState homeState, Player player, int tilesPassed, int index) {
        this.currentTile = currentTile;
        this.homeState = homeState;
        this.player = player;
        this.hasFinished = false;
        this.tilesPassed = tilesPassed;
        this.index = index;
    }

    public Piece() {}

    public void kill() {
        currentTile.removePiece(this);

        if (isOnColorTile()) {
            tilesPassed = Game.PIECE_NORMAL_TILES_COUNT;

            Tile colorBeginningTile = player.getTiles()[Game.PIECE_NORMAL_TILES_COUNT];
            currentTile = colorBeginningTile;
            colorBeginningTile.setPiece(this);
            return;
        }

        tilesPassed = -1;
        currentTile = null;
        homeState = HomeState.IN;
    }

    public boolean isOnColorTile() {
        return currentTile.getIndex() >= Game.PIECE_NORMAL_TILES_COUNT;
    }

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

    public int getIndex() {
        return index;
    }
}
