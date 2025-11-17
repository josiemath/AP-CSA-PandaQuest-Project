import greenfoot.*;
/**
 * PandaWorld - The main game world for PandaQuest.
 *
 * A Greenfoot adaptation of the PandaQuest puzzle game.
 * Players control a panda navigating through a grid-based world,
 * collecting items and avoiding bamboo obstacles.
 *
 * Added: a one-tile padding/frame around the game board so that
 * the status text (level, lives, score) can be shown on the padding
 * without overlapping the board tiles. The frame is drawn on the
 * world's background image.
 *
 * @author PandaQuest Team
 * @version 1.1
 */
public class PandaWorld extends World
{
    private static final int CELL_SIZE = 50;
    private static final int GRID_WIDTH = 8;
    private static final int GRID_HEIGHT = 8;
    private static final int PADDING = 1; // padding in tiles around the board

    private int level;
    private int lives;
    private int score;
    private boolean[][] bambooGrid;
    private boolean[][] revealedGrid;

    /**
     * Constructor for PandaWorld.
     * Creates a new world with an extra padding/frame of ~one tile on each side.
     */
    public PandaWorld()
    {
        // world width/height include padding on all sides
        super(GRID_WIDTH + 2 * PADDING, GRID_HEIGHT + 2 * PADDING, CELL_SIZE);
        level = 1;
        lives = 3;
        score = 0;
        initializeGame();

        // Draw frame/padding background so the board is visually inset.
        drawBoardFrame();

        prepare();
    }

    /**
     * Draw a frame / padding on the world's background so the grid is inset.
     */
    private void drawBoardFrame()
    {
        GreenfootImage bg = getBackground();
        if (bg == null) {
            // If background not initialized, create a new one sized to world in pixels.
            bg = new GreenfootImage(getWidth() * CELL_SIZE, getHeight() * CELL_SIZE);
            setBackground(bg);
        }

        // Fill entire background with frame color
        bg.setColor(new java.awt.Color(100, 140, 100)); // darker green frame
        bg.fill();

        // Draw inner board area as a lighter rectangle
        int leftPx = PADDING * CELL_SIZE;
        int topPx = PADDING * CELL_SIZE;
        int boardWidthPx = GRID_WIDTH * CELL_SIZE;
        int boardHeightPx = GRID_HEIGHT * CELL_SIZE;

        bg.setColor(new java.awt.Color(220, 250, 220)); // light green board area
        bg.fillRect(leftPx, topPx, boardWidthPx, boardHeightPx);

        // Draw border around the inner board
        bg.setColor(new java.awt.Color(50, 90, 50));
        bg.drawRect(leftPx, topPx, boardWidthPx - 1, boardHeightPx - 1);
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
        // Add panda at starting grid position (0,0) but mapped to world coords
        Panda panda = new Panda();
        addObject(panda, worldX(0), worldY(0));

        // Display game info on the top padding row so it doesn't overlap tiles
        updateDisplay();
    }

    /**
     * Reveal a tile at the specified position (grid coordinates).
     * Returns true if bamboo was revealed (hit) otherwise false.
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
            addObject(new Bamboo(), worldX(x), worldY(y)); // show bamboo visually
            updateDisplay();

            if (lives <= 0) {
                gameOver();
            }
            return true;
        }

        // Count adjacent bamboo
        int adjacentBamboo = countAdjacentBamboo(x, y);

        // Add visual marker for this tile
        TileMarker marker = new TileMarker(adjacentBamboo);
        addObject(marker, worldX(x), worldY(y));

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

                    // Add visual marker
                    int adjacentBamboo = countAdjacentBamboo(newX, newY);
                    TileMarker marker = new TileMarker(adjacentBamboo);
                    addObject(marker, worldX(newX), worldY(newY));

                    // Recurse if still zero
                    if (adjacentBamboo == 0) {
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
        // Show level complete message centered over the board area
        showText("Level " + (level - 1) + " Complete! Press SPACE for next level",
                 worldX(GRID_WIDTH / 2), worldY(GRID_HEIGHT / 2));
        Greenfoot.stop();
    }

    /**
     * Handle game over.
     */
    private void gameOver()
    {
        // Show game over message centered over the board area
        showText("Game Over! Final Score: " + score,
                 worldX(GRID_WIDTH / 2), worldY(GRID_HEIGHT / 2));
        Greenfoot.stop();
    }

    /**
     * Update the display with current game state.
     * Status text is placed in the top padding row to avoid overlapping tiles.
     */
    private void updateDisplay()
    {
        // Put status centered horizontally on the top padding row (y = 0)
        int displayX = worldX(GRID_WIDTH / 2);
        int displayY = 0; // top padding row
        showText("Level: " + level + "   Lives: " + lives + "   Score: " + score,
                 displayX, displayY);
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

    /**
     * Convert grid X coordinate (0..GRID_WIDTH-1) into world column (including padding).
     */
    private int worldX(int gridX)
    {
        return gridX + PADDING;
    }

    /**
     * Convert grid Y coordinate (0..GRID_HEIGHT-1) into world row (including padding).
     */
    private int worldY(int gridY)
    {
        return gridY + PADDING;
    }
}
