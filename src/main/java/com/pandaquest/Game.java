package com.pandaquest;

/**
 * Manages the game state and logic.
 */
public class Game {
    private Board board;
    private int currentLevel;
    private int lives;
    private boolean gameOver;
    private boolean levelComplete;
    private static final int STARTING_LIVES = 3;

    /**
     * Creates a new game starting at level 1.
     */
    public Game() {
        this.currentLevel = 1;
        this.lives = STARTING_LIVES;
        this.gameOver = false;
        this.levelComplete = false;
        initializeLevel();
    }

    /**
     * Initializes the current level with appropriate difficulty.
     */
    private void initializeLevel() {
        // Progressive difficulty: larger boards and more bamboo per level
        int rows = 5 + currentLevel;
        int cols = 5 + currentLevel;
        int bambooCount = 3 + (currentLevel * 2);
        
        board = new Board(rows, cols, bambooCount);
        
        // Add power-ups (1-2 per level)
        int powerUpCount = Math.min(2, 1 + (currentLevel / 3));
        board.placePowerUps(powerUpCount);
        
        levelComplete = false;
    }

    /**
     * Handles a player's tile selection.
     * @param row tile row
     * @param col tile column
     * @return true if selection was valid, false otherwise
     */
    public boolean selectTile(int row, int col) {
        if (gameOver || levelComplete) {
            return false;
        }

        Tile tile = board.getTile(row, col);
        if (tile == null || tile.isRevealed()) {
            return false;
        }

        // Check for power-up
        if (tile.hasPowerUp()) {
            applyPowerUp(tile.getPowerUp());
            tile.setPowerUp(null); // Remove power-up after use
        }

        // Reveal the tile
        boolean hitBamboo = board.revealTile(row, col);

        if (hitBamboo) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                // Restart level
                initializeLevel();
            }
        } else {
            // Check if level is complete
            if (board.isCleared()) {
                levelComplete = true;
            }
        }

        return true;
    }

    /**
     * Applies a power-up effect.
     * @param powerUp the power-up to apply
     */
    private void applyPowerUp(PowerUp powerUp) {
        switch (powerUp) {
            case EXTRA_LIFE:
                lives++;
                break;
            case REVEAL_SAFE:
                revealRandomSafeTile();
                break;
            case REVEAL_ADJACENT:
                // This is handled during tile reveal
                break;
        }
    }

    /**
     * Reveals a random safe (non-bamboo) tile.
     */
    private void revealRandomSafeTile() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (!tile.hasBamboo() && !tile.isRevealed()) {
                    board.revealTile(i, j);
                    return;
                }
            }
        }
    }

    /**
     * Advances to the next level.
     */
    public void nextLevel() {
        if (levelComplete) {
            currentLevel++;
            initializeLevel();
        }
    }

    /**
     * Resets the game to level 1.
     */
    public void reset() {
        currentLevel = 1;
        lives = STARTING_LIVES;
        gameOver = false;
        levelComplete = false;
        initializeLevel();
    }

    /**
     * Gets the current game board.
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current level number.
     * @return current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Gets the remaining lives.
     * @return number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Checks if the game is over.
     * @return true if game over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if the current level is complete.
     * @return true if level complete, false otherwise
     */
    public boolean isLevelComplete() {
        return levelComplete;
    }
}
