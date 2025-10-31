import greenfoot.*;

/**
 * PandaWorld - The main game world for PandaQuest.
 * 
 * In this world, a panda must collect bamboo while avoiding enemies.
 * This world initializes the game environment and populates it with actors.
 * 
 * @author AP-CSA PandaQuest Team
 * @version 1.0
 */
public class PandaWorld extends World
{
    private int score;
    private int bambooCollected;
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;
    private static final int CELL_SIZE = 50;
    
    /**
     * Constructor for PandaWorld.
     * Creates a world with 16x12 cells (800x600 pixels with 50px cells).
     */
    public PandaWorld()
    {    
        super(WORLD_WIDTH / CELL_SIZE, WORLD_HEIGHT / CELL_SIZE, CELL_SIZE);
        score = 0;
        bambooCollected = 0;
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * Creates and adds the initial actors to the world.
     */
    private void prepare()
    {
        // Add the player panda in the center-left area
        Panda panda = new Panda();
        addObject(panda, 3, 6);
        
        // Add bamboo items to collect (scattered around the world)
        addObject(new Bamboo(), 10, 3);
        addObject(new Bamboo(), 13, 9);
        addObject(new Bamboo(), 7, 10);
        addObject(new Bamboo(), 14, 5);
        addObject(new Bamboo(), 5, 2);
        
        // Add enemies (moving obstacles)
        addObject(new Enemy(), 8, 4);
        addObject(new Enemy(), 12, 7);
        addObject(new Enemy(), 6, 9);
        
        // Add walls to create boundaries and obstacles
        createWalls();
        
        // Display initial score
        showText("Score: " + score + " | Bamboo: " + bambooCollected, 
                 WORLD_WIDTH / CELL_SIZE / 2, 1);
    }
    
    /**
     * Create walls around the perimeter and some internal obstacles.
     */
    private void createWalls()
    {
        // Top and bottom walls
        for (int x = 0; x < WORLD_WIDTH / CELL_SIZE; x++) {
            addObject(new Wall(), x, 0);
            addObject(new Wall(), x, WORLD_HEIGHT / CELL_SIZE - 1);
        }
        
        // Left and right walls
        for (int y = 1; y < WORLD_HEIGHT / CELL_SIZE - 1; y++) {
            addObject(new Wall(), 0, y);
            addObject(new Wall(), WORLD_WIDTH / CELL_SIZE - 1, y);
        }
        
        // Add some internal wall obstacles
        for (int i = 3; i < 8; i++) {
            addObject(new Wall(), 9, i);
        }
    }
    
    /**
     * Update the score when bamboo is collected.
     */
    public void addScore(int points)
    {
        score += points;
        bambooCollected++;
        updateScoreDisplay();
        
        // Check for win condition
        if (bambooCollected >= 5) {
            showText("YOU WIN! Final Score: " + score, 
                     WORLD_WIDTH / CELL_SIZE / 2, WORLD_HEIGHT / CELL_SIZE / 2);
            Greenfoot.stop();
        }
    }
    
    /**
     * Handle game over condition.
     */
    public void gameOver()
    {
        showText("GAME OVER! Score: " + score, 
                 WORLD_WIDTH / CELL_SIZE / 2, WORLD_HEIGHT / CELL_SIZE / 2);
        Greenfoot.stop();
    }
    
    /**
     * Update the score display.
     */
    private void updateScoreDisplay()
    {
        showText("Score: " + score + " | Bamboo: " + bambooCollected, 
                 WORLD_WIDTH / CELL_SIZE / 2, 1);
    }
}
