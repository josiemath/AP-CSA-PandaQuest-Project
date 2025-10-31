import greenfoot.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main Greenfoot World for PandaQuest.
 * Manages the game state, board, and level progression.
 */
public class PandaQuestWorld extends World {
    private TileActor[][] tiles;
    private int rows;
    private int cols;
    private int bambooCount;
    private int currentLevel;
    private int lives;
    private boolean gameOver;
    private boolean levelComplete;
    private Random random;
    private static final int STARTING_LIVES = 3;
    private static final int TILE_SIZE = 40;
    private static final int INFO_HEIGHT = 60;
    
    /**
     * Creates a new PandaQuest world starting at level 1.
     */
    public PandaQuestWorld() {
        super(600, 600, 1);
        this.currentLevel = 1;
        this.lives = STARTING_LIVES;
        this.gameOver = false;
        this.levelComplete = false;
        this.random = new Random();
        initializeLevel();
        displayInfo();
    }

    /**
     * Initializes the current level with appropriate difficulty.
     */
    private void initializeLevel() {
        // Progressive difficulty: larger boards and more bamboo per level
        rows = 5 + currentLevel;
        cols = 5 + currentLevel;
        bambooCount = 3 + (currentLevel * 2);
        
        // Adjust world size to fit grid
        int worldWidth = Math.max(600, cols * TILE_SIZE + 40);
        int worldHeight = Math.max(600, rows * TILE_SIZE + INFO_HEIGHT + 40);
        
        // Clear existing tiles
        removeObjects(getObjects(TileActor.class));
        
        // Initialize board
        initializeBoard();
        
        // Add power-ups (1-2 per level)
        int powerUpCount = Math.min(2, 1 + (currentLevel / 3));
        placePowerUps(powerUpCount);
        
        levelComplete = false;
    }
    
    /**
     * Initializes the board with tiles and places bamboo.
     */
    private void initializeBoard() {
        tiles = new TileActor[rows][cols];
        
        // Create all tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileActor tile = new TileActor(i, j);
                tiles[i][j] = tile;
                int x = 20 + j * TILE_SIZE + TILE_SIZE / 2;
                int y = INFO_HEIGHT + 20 + i * TILE_SIZE + TILE_SIZE / 2;
                addObject(tile, x, y);
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
     * Handles a player's tile selection.
     * @param row tile row
     * @param col tile column
     * @return true if selection was valid, false otherwise
     */
    public boolean selectTile(int row, int col) {
        if (gameOver || levelComplete) {
            return false;
        }

        TileActor tile = tiles[row][col];
        if (tile == null || tile.isRevealed()) {
            return false;
        }

        // Check for power-up
        PowerUp powerUp = null;
        if (tile.hasPowerUp()) {
            powerUp = tile.getPowerUp();
            tile.setPowerUp(null); // Remove power-up after use
        }

        // Reveal the tile
        boolean hitBamboo = revealTile(row, col);

        // Apply power-up effect after revealing the tile
        if (powerUp != null) {
            applyPowerUp(powerUp, row, col);
        }

        if (hitBamboo) {
            lives--;
            Greenfoot.playSound("buzzer.wav");
            if (lives <= 0) {
                gameOver = true;
                showGameOver();
            } else {
                // Restart level
                Greenfoot.delay(30);
                initializeLevel();
            }
        } else {
            // Check if level is complete
            if (isCleared()) {
                levelComplete = true;
                showLevelComplete();
            }
        }
        
        displayInfo();
        return true;
    }
    
    /**
     * Reveals a tile at the specified position.
     * @param row tile row
     * @param col tile column
     * @return true if bamboo was revealed (player loses life), false otherwise
     */
    private boolean revealTile(int row, int col) {
        if (!isValidPosition(row, col) || tiles[row][col].isRevealed()) {
            return false;
        }

        TileActor tile = tiles[row][col];
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
                    TileActor adjacentTile = tiles[newRow][newCol];
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
    private void placePowerUps(int count) {
        // Calculate maximum eligible positions (non-bamboo tiles)
        int eligiblePositions = (rows * cols) - bambooCount;
        int actualCount = Math.min(count, eligiblePositions);
        
        int placed = 0;
        int attempts = 0;
        int maxAttempts = eligiblePositions * 3;
        
        while (placed < actualCount && attempts < maxAttempts) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            attempts++;
            
            TileActor tile = tiles[row][col];
            if (!tile.hasBamboo() && !tile.hasPowerUp()) {
                PowerUp[] powerUps = PowerUp.values();
                PowerUp randomPowerUp = powerUps[random.nextInt(powerUps.length)];
                tile.setPowerUp(randomPowerUp);
                placed++;
            }
        }
    }
    
    /**
     * Applies a power-up effect.
     * @param powerUp the power-up to apply
     * @param row the row where the power-up was collected
     * @param col the column where the power-up was collected
     */
    private void applyPowerUp(PowerUp powerUp, int row, int col) {
        switch (powerUp) {
            case EXTRA_LIFE:
                lives++;
                break;
            case REVEAL_SAFE:
                revealRandomSafeTile();
                break;
            case REVEAL_ADJACENT:
                revealAdjacentSafeTiles(row, col);
                break;
        }
    }
    
    /**
     * Reveals a random safe (non-bamboo) tile.
     */
    private void revealRandomSafeTile() {
        List<int[]> safeTiles = new ArrayList<int[]>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileActor tile = tiles[i][j];
                if (!tile.hasBamboo() && !tile.isRevealed()) {
                    safeTiles.add(new int[]{i, j});
                }
            }
        }
        
        if (!safeTiles.isEmpty()) {
            int[] position = safeTiles.get(random.nextInt(safeTiles.size()));
            revealTile(position[0], position[1]);
        }
    }
    
    /**
     * Reveals all adjacent tiles around a position (safe tiles only).
     * @param row the center row
     * @param col the center column
     */
    private void revealAdjacentSafeTiles(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (isValidPosition(newRow, newCol)) {
                    TileActor tile = tiles[newRow][newCol];
                    if (!tile.isRevealed() && !tile.hasBamboo()) {
                        revealTile(newRow, newCol);
                    }
                }
            }
        }
    }
    
    /**
     * Checks if all safe tiles have been revealed.
     * @return true if all safe tiles are revealed, false otherwise
     */
    private boolean isCleared() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileActor tile = tiles[i][j];
                if (!tile.hasBamboo() && !tile.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Advances to the next level.
     */
    public void nextLevel() {
        if (levelComplete) {
            currentLevel++;
            initializeLevel();
            displayInfo();
        }
    }
    
    /**
     * Displays game information (level, lives).
     */
    private void displayInfo() {
        showText("Level: " + currentLevel + " | Lives: " + lives + " | Click tiles to reveal", 
                 getWidth() / 2, 20);
    }
    
    /**
     * Shows level complete message.
     */
    private void showLevelComplete() {
        showText("Level " + currentLevel + " Complete! Press SPACE for next level", 
                 getWidth() / 2, getHeight() / 2);
    }
    
    /**
     * Shows game over message.
     */
    private void showGameOver() {
        showText("Game Over! Final Level: " + currentLevel + " | Press R to restart", 
                 getWidth() / 2, getHeight() / 2);
    }
    
    /**
     * Act method called by Greenfoot each frame.
     */
    public void act() {
        if (levelComplete && Greenfoot.isKeyDown("space")) {
            nextLevel();
        }
        
        if (gameOver && Greenfoot.isKeyDown("r")) {
            currentLevel = 1;
            lives = STARTING_LIVES;
            gameOver = false;
            levelComplete = false;
            initializeLevel();
            displayInfo();
        }
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
