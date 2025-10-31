package com.pandaquest;

/**
 * Represents a single tile on the game board.
 */
public class Tile {
    private boolean hasBamboo;
    private boolean isRevealed;
    private int adjacentBambooCount;
    private PowerUp powerUp;

    /**
     * Creates a new tile.
     */
    public Tile() {
        this.hasBamboo = false;
        this.isRevealed = false;
        this.adjacentBambooCount = 0;
        this.powerUp = null;
    }

    /**
     * Gets whether this tile contains bamboo.
     * @return true if tile has bamboo, false otherwise
     */
    public boolean hasBamboo() {
        return hasBamboo;
    }

    /**
     * Sets whether this tile contains bamboo.
     * @param hasBamboo true to place bamboo on this tile
     */
    public void setHasBamboo(boolean hasBamboo) {
        this.hasBamboo = hasBamboo;
    }

    /**
     * Gets whether this tile has been revealed.
     * @return true if revealed, false otherwise
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * Sets the revealed state of this tile.
     * @param revealed true to reveal the tile
     */
    public void setRevealed(boolean revealed) {
        this.isRevealed = revealed;
    }

    /**
     * Gets the count of adjacent tiles with bamboo.
     * @return number of adjacent bamboo tiles
     */
    public int getAdjacentBambooCount() {
        return adjacentBambooCount;
    }

    /**
     * Sets the count of adjacent tiles with bamboo.
     * @param count number of adjacent bamboo tiles
     */
    public void setAdjacentBambooCount(int count) {
        this.adjacentBambooCount = count;
    }

    /**
     * Gets the power-up on this tile, if any.
     * @return the power-up or null if none
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }

    /**
     * Sets a power-up on this tile.
     * @param powerUp the power-up to place
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * Checks if this tile has a power-up.
     * @return true if tile has power-up, false otherwise
     */
    public boolean hasPowerUp() {
        return powerUp != null;
    }
}
