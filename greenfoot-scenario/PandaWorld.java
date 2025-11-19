import greenfoot.*;
import java.awt.Color;

/**
 * PandaWorld - The main game world for PandaQuest.
 *
 * A Greenfoot adaptation of the PandaQuest puzzle game.
 * Players control a panda navigating through a grid-based world,
 * collecting items and avoiding bamboo obstacles.
 *
 * Updated to support multiple grid-size difficulty levels:
 * Level grid sizes available: 6x6, 10x10, 12x12, 14x14, 16x16.
 *
 * Approach:
 * - The World is sized to the largest grid (16x16 plus frame). For smaller
 *   difficulty grids the playable area is centered inside the larger world.
 * - Playable grid size (playWidth/playHeight) is selected from LEVEL_SIZES.
 * - All coordinate conversions (playable <-> world) use playOffsetX/Y so
 *   existing world coordinates passed around in the game keep working.
 * - Bamboo count scales with playable area (approx ~15% of cells) but at least 1.
 *
 * Note: Level progression and how the next difficulty is selected are left
 * to the existing flow (levelComplete stops the game and prompts SPACE).
 *
 * @author PandaQuest Team (modified)
 * @version 1.1
 */
public class PandaWorld extends World
{
    private static final int CELL_SIZE = 50;

    // Available difficulty grid sizes (square grids).
    private static final int[] LEVEL_SIZES = {6, 10, 12, 14, 16};

    // If we want the world to be a fixed size, make it large enough for the largest grid.
    private static final int MAX_PLAY_SIZE = 16;

    // Frame width (in cells) around the playable board where status is shown
    private static final int FRAME_SIZE = 1;

    // World size in cells (based on largest playable grid)
    private static final int WORLD_WIDTH = MAX_PLAY_SIZE + FRAME_SIZE * 2;
    private static final int WORLD_HEIGHT = WORLD_WIDTH;

    // Index into LEVEL_SIZES for current difficulty (0 => 6x6 by default)
    private static final int DEFAULT_LEVEL_INDEX = 0;

    private int level;         // display-level number (keeps original behaviour)
    private int levelIndex;    // index into LEVEL_SIZES for current grid size
    private int lives;
    private int score;

    // Current playable grid dimensions (in playable cells)
    private int playWidth;
    private int playHeight;

    // Offset of playable grid within the world (in world cells), used for mapping
    private int playOffsetX;
    private int playOffsetY;

    // Game state arrays use current playable sizes (indices 0..playWidth-1)
    private boolean[][] bambooGrid;
    private boolean[][] revealedGrid;

    /**
     * Constructor for PandaWorld.
     * Creates a new world sized to the maximum supported grid and centers the chosen playable grid.
     */
    public PandaWorld()
    {
        // World is created based on largest grid; playable area will be centered for smaller sizes
        super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE);

        // Default game state
        level = 1;
        levelIndex = DEFAULT_LEVEL_INDEX;
        lives = 3;
        score = 0;

        // Set playable grid dimensions and offset based on selected levelIndex
        setPlaySizeFromIndex();

        // Initialize game state and visuals
        initializeGame();
        prepare();
        drawFrame();
        setPaintOrder(Panda.class, Bamboo.class, TileMarker.class, FlagMarker.class);
    }

    /**
     * Configure playWidth/playHeight and playOffsetX/playOffsetY from levelIndex.
     */
    private void setPlaySizeFromIndex()
    {
        playWidth = LEVEL_SIZES[levelIndex];
        playHeight = playWidth; // square grids
        // Center the playable grid inside the world (after the outer FRAME)
        int freeSpaceX = MAX_PLAY_SIZE - playWidth;
        int freeSpaceY = MAX_PLAY_SIZE - playHeight;
        playOffsetX = FRAME_SIZE + (freeSpaceX / 2);
        playOffsetY = FRAME_SIZE + (freeSpaceY / 2);
    }

    /**
     * Initialize the game state.
     */
    private void initializeGame()
    {
        bambooGrid = new boolean[playWidth][playHeight];
        revealedGrid = new boolean[playWidth][playHeight];

        // Place bamboo: approximately 15% of playable cells (minimum 1)
        int area = playWidth * playHeight;
        int bambooCount = Math.max(1, (int)Math.round(area * 0.15));
        placeBambooRandomly(bambooCount);
    }

    /**
     * Place bamboo randomly on the playable grid.
     */
    private void placeBambooRandomly(int count)
    {
        int placed = 0;
        while (placed < count) {
            int x = Greenfoot.getRandomNumber(playWidth);
            int y = Greenfoot.getRandomNumber(playHeight);

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
        // Add panda at center of playable area (converted to world coordinates)
        Panda panda = new Panda();
        int startWX = playToWorldX(playWidth / 2);
        int startWY = playToWorldY(playHeight / 2);
        addObject(panda, startWX, startWY);

        // Display game info inside the top frame row (centered across the whole world)
        showText("Level: " + level + " Lives: " + lives + " Score: " + score,
                 WORLD_WIDTH / 2, 0);
    }

    /**
     * Draw a visible frame around the current playable board (for status text).
     */
    private void drawFrame()
    {
        GreenfootImage bg = getBackground();
        if (bg == null) return;

        bg.setColor(Color.BLACK);
        int left = playOffsetX * CELL_SIZE;
        int top = playOffsetY * CELL_SIZE;
        int width = playWidth * CELL_SIZE;
        int height = playHeight * CELL_SIZE;

        // Draw outer rectangle around playable area (pixel coords)
        bg.drawRect(left, top, width - 1, height - 1);

        // Optionally draw a thin inner rectangle for a clearer frame (subtle)
        bg.setColor(Color.BLACK);
        bg.drawRect(left + 2, top + 2, width - 5, height - 5);
    }

    /**
     * World act method - ensure Panda cannot move outside the playable area.
     */
    public void act()
    {
        // Clamp any Panda instances to stay within current playable area
        for (Object obj : getObjects(Panda.class)) {
            Panda p = (Panda)obj;
            int px = p.getX();
            int py = p.getY();

            int minX = playOffsetX;
            int minY = playOffsetY;
            int maxX = playOffsetX + playWidth - 1;
            int maxY = playOffsetY + playHeight - 1;

            boolean moved = false;
            int newX = px;
            int newY = py;
            if (px < minX) { newX = minX; moved = true; }
            if (py < minY) { newY = minY; moved = true; }
            if (px > maxX) { newX = maxX; moved = true; }
            if (py > maxY) { newY = maxY; moved = true; }

            if (moved) {
                p.setLocation(newX, newY);
            }
        }
    }

    /**
     * Reveal a tile at the specified world position.
     * Note: x,y parameters are expected to be world coordinates. They are mapped to playable coordinates internally.
     */
    public boolean revealTile(int worldX, int worldY)
    {
        int x = worldToPlayX(worldX);
        int y = worldToPlayY(worldY);

        if (x < 0 || x >= playWidth || y < 0 || y >= playHeight) {
            return false;
        }

        if (revealedGrid[x][y]) {
            return false;
        }

        revealedGrid[x][y] = true;

        if (bambooGrid[x][y]) {
            // Hit bamboo - lose a life
            lives--;
            addObject(new Bamboo(), worldX, worldY); // show bamboo visually at world coords
            updateDisplay();

            if (lives <= 0) {
                gameOver();
            }
            return true;
        }

        // Count adjacent bamboo (playable coords)
        int adjacentBamboo = countAdjacentBamboo(x, y);

        // Add visual marker for this tile at world coords
        TileMarker marker = new TileMarker(adjacentBamboo);
        addObject(marker, worldX, worldY);

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

    public void addFlag(int worldX, int worldY)
    {
        FlagMarker flag = new FlagMarker();
        addObject(flag, worldX, worldY);
        updateDisplay();
    }

    public void removeFlag(int worldX, int worldY)
    {
        removeObjects(getObjectsAt(worldX, worldY, FlagMarker.class));
        updateDisplay();
    }

    /**
     * Count bamboo in adjacent playable tiles (x,y are playable coords).
     */
    private int countAdjacentBamboo(int x, int y)
    {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < playWidth &&
                    newY >= 0 && newY < playHeight &&
                    bambooGrid[newX][newY]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Auto-reveal adjacent tiles recursively. Input coordinates are playable coords.
     */
    private void autoRevealAdjacent(int x, int y)
    {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < playWidth &&
                    newY >= 0 && newY < playHeight &&
                    !revealedGrid[newX][newY] && !bambooGrid[newX][newY]) {

                    revealedGrid[newX][newY] = true;

                    // Add visual marker at corresponding world coords
                    int adjacentBamboo = countAdjacentBamboo(newX, newY);
                    TileMarker marker = new TileMarker(adjacentBamboo);
                    addObject(marker, playToWorldX(newX), playToWorldY(newY));

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
        for (int x = 0; x < playWidth; x++) {
            for (int y = 0; y < playHeight; y++) {
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
        // Advance display-level number
        level++;

        // If there's a higher available difficulty, advance to it for the next start
        if (levelIndex < LEVEL_SIZES.length - 1) {
            levelIndex++;
            // Recompute playable sizes for the next level (when user restarts)
            setPlaySizeFromIndex();
        }

        showText("Level " + (level - 1) + " Complete! Press SPACE for next level",
                 WORLD_WIDTH / 2, 0);
        Greenfoot.stop();
    }

    /**
     * Handle game over.
     */
    private void gameOver()
    {
        showText("Game Over! Final Score: " + score,
                 WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        Greenfoot.stop();
    }

    /**
     * Update the display with current game state (placed in top frame row).
     */
    private void updateDisplay()
    {
        showText("Level: " + level + " Lives: " + lives + " Score: " + score,
                 WORLD_WIDTH / 2, 0);
    }

    /**
     * Check if a world position (world coords) is revealed.
     */
    public boolean isRevealed(int worldX, int worldY)
    {
        int x = worldToPlayX(worldX);
        int y = worldToPlayY(worldY);

        if (x < 0 || x >= playWidth || y < 0 || y >= playHeight) {
            return false;
        }
        return revealedGrid[x][y];
    }

    /**
     * Get the number of adjacent bamboo for a world-position.
     */
    public int getAdjacentBambooCount(int worldX, int worldY)
    {
        int x = worldToPlayX(worldX);
        int y = worldToPlayY(worldY);
        if (x < 0 || x >= playWidth || y < 0 || y >= playHeight) {
            return 0;
        }
        return countAdjacentBamboo(x, y);
    }

    /**
     * Helper: convert world X coordinate to playable grid X coordinate.
     */
    private int worldToPlayX(int worldX)
    {
        return worldX - playOffsetX;
    }

    /**
     * Helper: convert world Y coordinate to playable grid Y coordinate.
     */
    private int worldToPlayY(int worldY)
    {
        return worldY - playOffsetY;
    }

    /**
     * Helper: convert playable X to world X.
     */
    private int playToWorldX(int playX)
    {
        return playX + playOffsetX;
    }

    /**
     * Helper: convert playable Y to world Y.
     */
    private int playToWorldY(int playY)
    {
        return playY + playOffsetY;
    }
}
