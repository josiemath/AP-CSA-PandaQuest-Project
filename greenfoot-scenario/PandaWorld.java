import greenfoot.*;

/**
 * PandaWorld - The main game world for PandaQuest.
 * 
 * A Greenfoot adaptation of the PandaQuest puzzle game.
 * Players control a panda navigating through a grid-based world,
 * collecting items and avoiding bamboo obstacles.
 * 
 * @author PandaQuest Team
 * @version 1.0
 */
public class PandaWorld extends World
{
    private static final int CELL_SIZE = 50;
    private static final int GRID_WIDTH = 8;
    private static final int GRID_HEIGHT = 8;
    
    private int level;
    private int lives;
    private int score;
    private boolean[][] bambooGrid;
    private boolean[][] revealedGrid;
    
    /**
     * Constructor for PandaWorld.
     * Creates a new world with the specified grid dimensions.
     */
    public PandaWorld()
    {    
        super(GRID_WIDTH, GRID_HEIGHT, CELL_SIZE);
        level = 1;
        lives = 3;
        score = 0;
        initializeGame();
        prepare();
    }
    
    /**
     * Initialize the game state.
     */
    private void initializeGame()
    {
        bambooGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];
        revealedGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];
        
        // Place bamboo randomly
        int bambooCount = 5 + (level * 2);
        placeBambooRandomly(bambooCount);
    }
    
    /**
     * Place bamboo randomly on the grid.
     */
    private void placeBambooRandomly(int count)
    {
        int placed = 0;
        while (placed < count) {
            int x = Greenfoot.getRandomNumber(GRID_WIDTH);
            int y = Greenfoot.getRandomNumber(GRID_HEIGHT);
            
            if (!bambooGrid[x][y]) {
                bambooGrid[x][y] = true;
                placed++;
            }
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * Add initial actors to the world.
     */
    private void prepare()
    {
        // Add panda at starting position
        Panda panda = new Panda();
        addObject(panda, 0, 0);
        
        // Display game info
        showText("Level: " + level + " Lives: " + lives + " Score: " + score, 
                 GRID_WIDTH / 2, 0);
    }
    
    /**
     * Reveal a tile at the specified position.
     */
    public boolean revealTile(int x, int y)
    {
        if (x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT) {
            return false;
        }
        
        if (revealedGrid[x][y]) {
            return false;
        }
        
        revealedGrid[x][y] = true;
        
        if (bambooGrid[x][y]) {
            // Hit bamboo - lose a life
            lives--;
            addObject(new Bamboo(), x, y);
            updateDisplay();
            
            if (lives <= 0) {
                gameOver();
            }
            return true;
        }
        
        // Count adjacent bamboo
        int adjacentBamboo = countAdjacentBamboo(x, y);
        
        // Auto-reveal if no adjacent bamboo
        if (adjacentBamboo == 0) {
            autoRevealAdjacent(x, y);
        }
        
        score += 10;
        updateDisplay();
        
        // Check if level is complete
        if (isLevelComplete()) {
            levelComplete();
        }
        
        return false;
    }
    
    /**
     * Count bamboo in adjacent tiles.
     */
    private int countAdjacentBamboo(int x, int y)
    {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < GRID_WIDTH && 
                    newY >= 0 && newY < GRID_HEIGHT &&
                    bambooGrid[newX][newY]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Auto-reveal adjacent tiles recursively.
     */
    private void autoRevealAdjacent(int x, int y)
    {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < GRID_WIDTH && 
                    newY >= 0 && newY < GRID_HEIGHT &&
                    !revealedGrid[newX][newY] && !bambooGrid[newX][newY]) {
                    revealedGrid[newX][newY] = true;
                    if (countAdjacentBamboo(newX, newY) == 0) {
                        autoRevealAdjacent(newX, newY);
                    }
                }
            }
        }
    }
    
    /**
     * Check if the current level is complete.
     */
    private boolean isLevelComplete()
    {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                if (!bambooGrid[x][y] && !revealedGrid[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Handle level completion.
     */
    private void levelComplete()
    {
        level++;
        showText("Level " + (level - 1) + " Complete! Press SPACE for next level", 
                 GRID_WIDTH / 2, GRID_HEIGHT / 2);
        Greenfoot.stop();
    }
    
    /**
     * Handle game over.
     */
    private void gameOver()
    {
        showText("Game Over! Final Score: " + score, 
                 GRID_WIDTH / 2, GRID_HEIGHT / 2);
        Greenfoot.stop();
    }
    
    /**
     * Update the display with current game state.
     */
    private void updateDisplay()
    {
        showText("Level: " + level + " Lives: " + lives + " Score: " + score, 
                 GRID_WIDTH / 2, 0);
    }
    
    /**
     * Check if a position is revealed.
     */
    public boolean isRevealed(int x, int y)
    {
        if (x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT) {
            return false;
        }
        return revealedGrid[x][y];
    }
    
    /**
     * Get the number of adjacent bamboo for a position.
     */
    public int getAdjacentBambooCount(int x, int y)
    {
        return countAdjacentBamboo(x, y);
    }
}
