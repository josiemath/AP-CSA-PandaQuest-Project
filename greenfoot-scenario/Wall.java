import greenfoot.*;

/**
 * Wall - Impassable obstacle in PandaQuest.
 * 
 * Walls create boundaries and obstacles in the game world.
 * Neither the panda nor enemies can pass through walls.
 * 
 * @author AP-CSA PandaQuest Team
 * @version 1.0
 */
public class Wall extends Actor
{
    /**
     * Constructor for Wall.
     * Sets up the initial appearance.
     */
    public Wall()
    {
        // Set the wall image - users should replace with actual wall.png
        setImage("wall.png");
    }
    
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * 
     * Walls are static, so this method does nothing.
     */
    public void act()
    {
        // Walls don't do anything
    }
}
