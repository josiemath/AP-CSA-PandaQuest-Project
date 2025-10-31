package com.pandaquest;

import java.util.Random;

/**
 * Represents the game board with tiles.
 */
public class Board {
    private final int rows;
    private final int cols;
    private final Tile[][] tiles;
    private final int bambooCount;
    private final Random random;

    /**
     * Creates a new game board.
     * @param rows number of rows
     * @param cols number of columns
     * @param bambooCount number of bamboo shoots to place
     */
    public Board(int rows, int cols, int bambooCount) {
        this.rows = rows;
        this.cols = cols;
        this.bambooCount = bambooCount;
        this.tiles = new Tile[rows][cols];
        this.random = new Random();
        initializeBoard();
    }

    /**
     * Initializes the board with tiles and places bamboo.
     */
    private void initializeBoard() {
        // Create all tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tiles[i][j] = new Tile();
            }
        }

        // Place bamboo randomly
        int placed = 0;
        while (placed < bambooCount) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            
            if (!tiles[row][col].hasBamboo()) {
                tiles[row][col].setHasBamboo(true);
                placed++;
            }
        }

        // Calculate adjacent bamboo counts
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!tiles[i][j].hasBamboo()) {
                    tiles[i][j].setAdjacentBambooCount(countAdjacentBamboo(i, j));
                }
            }
        }
    }

    /**
     * Counts bamboo in adjacent tiles.
     * @param row tile row
     * @param col tile column
     * @return count of adjacent bamboo
     */
    private int countAdjacentBamboo(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (isValidPosition(newRow, newCol) && tiles[newRow][newCol].hasBamboo()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if a position is valid on the board.
     * @param row row position
     * @param col column position
     * @return true if position is valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Reveals a tile at the specified position.
     * @param row tile row
     * @param col tile column
     * @return true if bamboo was revealed (player loses life), false otherwise
     */
    public boolean revealTile(int row, int col) {
        if (!isValidPosition(row, col) || tiles[row][col].isRevealed()) {
            return false;
        }

        Tile tile = tiles[row][col];
        tile.setRevealed(true);

        if (tile.hasBamboo()) {
            return true; // Hit bamboo - lose life
        }

        // Auto-reveal adjacent empty tiles
        if (tile.getAdjacentBambooCount() == 0) {
            revealAdjacentTiles(row, col);
        }

        return false;
    }

    /**
     * Recursively reveals adjacent tiles when an empty tile is revealed.
     * @param row tile row
     * @param col tile column
     */
    private void revealAdjacentTiles(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (isValidPosition(newRow, newCol)) {
                    Tile adjacentTile = tiles[newRow][newCol];
                    if (!adjacentTile.isRevealed() && !adjacentTile.hasBamboo()) {
                        adjacentTile.setRevealed(true);
                        if (adjacentTile.getAdjacentBambooCount() == 0) {
                            revealAdjacentTiles(newRow, newCol);
                        }
                    }
                }
            }
        }
    }

    /**
     * Places power-ups randomly on the board.
     * @param count number of power-ups to place
     */
    public void placePowerUps(int count) {
        int placed = 0;
        while (placed < count) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            
            Tile tile = tiles[row][col];
            if (!tile.hasBamboo() && !tile.hasPowerUp()) {
                PowerUp[] powerUps = PowerUp.values();
                PowerUp randomPowerUp = powerUps[random.nextInt(powerUps.length)];
                tile.setPowerUp(randomPowerUp);
                placed++;
            }
        }
    }

    /**
     * Gets the tile at the specified position.
     * @param row tile row
     * @param col tile column
     * @return the tile at that position
     */
    public Tile getTile(int row, int col) {
        if (isValidPosition(row, col)) {
            return tiles[row][col];
        }
        return null;
    }

    /**
     * Gets the number of rows on the board.
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns on the board.
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of bamboo shoots on the board.
     * @return bamboo count
     */
    public int getBambooCount() {
        return bambooCount;
    }

    /**
     * Checks if all safe tiles have been revealed.
     * @return true if all safe tiles are revealed, false otherwise
     */
    public boolean isCleared() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = tiles[i][j];
                if (!tile.hasBamboo() && !tile.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
}
