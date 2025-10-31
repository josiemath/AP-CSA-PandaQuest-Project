import greenfoot.*;

/**
 * Panda - The player character in PandaQuest.
 * 
 * The panda can be controlled using arrow keys to move around the world.
 * The goal is to collect all bamboo while avoiding enemies.
 * 
 * Controls:
 * - Arrow keys: Move in four directions
 * 
 * @author AP-CSA PandaQuest Team
 * @version 1.0
 */
public class Panda extends Actor
{
    private int speed = 3;
    private int health = 3;
    private int invincibilityTimer = 0;
    private static final int INVINCIBILITY_DURATION = 60; // 60 act cycles of invincibility
    
    /**
     * Constructor for Panda.
     * Sets up the initial appearance.
     */
    public Panda()
    {
        // Set the panda image - users should replace with actual panda.png
        setImage("panda.png");
    }
    
    /**
     * Act - do whatever the Panda wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        handleMovement();
        checkCollisions();
        
        // Reduce invincibility timer if active
        if (invincibilityTimer > 0) {
            invincibilityTimer--;
            // Flash effect during invincibility
            if (invincibilityTimer % 10 < 5) {
                getImage().setTransparency(128);
            } else {
                getImage().setTransparency(255);
            }
        } else {
            getImage().setTransparency(255);
        }
    }
    
    /**
     * Handle keyboard input for movement.
     */
    private void handleMovement()
    {
        int dx = 0;
        int dy = 0;
        
        if (Greenfoot.isKeyDown("up")) {
            dy = -1;
        }
        if (Greenfoot.isKeyDown("down")) {
            dy = 1;
        }
        if (Greenfoot.isKeyDown("left")) {
            dx = -1;
        }
        if (Greenfoot.isKeyDown("right")) {
            dx = 1;
        }
        
        // Attempt to move
        if (dx != 0 || dy != 0) {
            int newX = getX() + dx;
            int newY = getY() + dy;
            
            // Check if the new position would collide with a wall
            if (!wouldCollideWithWall(newX, newY)) {
                setLocation(newX, newY);
            }
        }
    }
    
    /**
     * Check if moving to the specified location would collide with a wall.
     */
    private boolean wouldCollideWithWall(int x, int y)
    {
        // Temporarily move to check collision
        int oldX = getX();
        int oldY = getY();
        setLocation(x, y);
        
        Actor wall = getOneIntersectingObject(Wall.class);
        
        // Move back
        setLocation(oldX, oldY);
        
        return wall != null;
    }
    
    /**
     * Check for collisions with other actors.
     */
    private void checkCollisions()
    {
        // Check for bamboo collection
        Actor bamboo = getOneIntersectingObject(Bamboo.class);
        if (bamboo != null) {
            // Remove the bamboo and increase score
            getWorld().removeObject(bamboo);
            PandaWorld world = (PandaWorld) getWorld();
            world.addScore(10);
            // Optional: Uncomment the line below and add coin.wav to sounds/ folder
            // Greenfoot.playSound("coin.wav");
        }
        
        // Check for enemy collision
        if (invincibilityTimer == 0) {
            Actor enemy = getOneIntersectingObject(Enemy.class);
            if (enemy != null) {
                takeDamage();
            }
        }
    }
    
    /**
     * Handle taking damage from an enemy.
     */
    private void takeDamage()
    {
        health--;
        invincibilityTimer = INVINCIBILITY_DURATION;
        
        if (health <= 0) {
            PandaWorld world = (PandaWorld) getWorld();
            world.gameOver();
        }
    }
    
    /**
     * Get the current health of the panda.
     */
    public int getHealth()
    {
        return health;
    }
}
