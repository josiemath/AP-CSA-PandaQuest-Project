import greenfoot.*;

/**
 * Enemy - An obstacle/antagonist in PandaQuest.
 * 
 * Enemies patrol the game world and cause damage to the panda on contact.
 * They move in simple patterns and reverse direction when hitting walls.
 * 
 * @author AP-CSA PandaQuest Team
 * @version 1.0
 */
public class Enemy extends Actor
{
    private int direction = 0; // 0=right, 1=down, 2=left, 3=up
    private int moveCounter = 0;
    private int moveInterval = 20; // Move every 20 act cycles
    private static final int DIRECTION_CHANGE_PROBABILITY = 5; // 5% chance per move
    private static final int RANDOM_RANGE = 100;
    
    /**
     * Constructor for Enemy.
     * Sets up the initial appearance and random direction.
     */
    public Enemy()
    {
        // Set the enemy image - users should replace with actual enemy.png
        setImage("enemy.png");
        
        // Start with a random direction
        direction = Greenfoot.getRandomNumber(4);
    }
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        moveCounter++;
        
        if (moveCounter >= moveInterval) {
            moveCounter = 0;
            move();
        }
        
        // Add a simple rotation animation
        setRotation(direction * 90);
    }
    
    /**
     * Move the enemy in its current direction.
     */
    private void move()
    {
        int dx = 0;
        int dy = 0;
        
        // Determine movement based on direction
        switch (direction) {
            case 0: // Right
                dx = 1;
                break;
            case 1: // Down
                dy = 1;
                break;
            case 2: // Left
                dx = -1;
                break;
            case 3: // Up
                dy = -1;
                break;
        }
        
        int newX = getX() + dx;
        int newY = getY() + dy;
        
        // Check if the new position would collide with a wall or boundary
        if (isAtEdge() || wouldCollideWithWall(newX, newY)) {
            // Reverse direction
            direction = (direction + 2) % 4;
        } else {
            // Move to new position
            setLocation(newX, newY);
        }
        
        // Occasionally change direction randomly
        if (Greenfoot.getRandomNumber(RANDOM_RANGE) < DIRECTION_CHANGE_PROBABILITY) {
            direction = Greenfoot.getRandomNumber(4);
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
}
