import greenfoot.*;

/**
 * Bamboo - Collectible item in PandaQuest.
 * 
 * Bamboo is the primary collectible in the game. The panda must collect
 * all bamboo items to win the game. Each bamboo collected increases the score.
 * 
 * @author AP-CSA PandaQuest Team
 * @version 1.0
 */
public class Bamboo extends Actor
{
    private int animationCounter = 0;
    private int baseY;
    
    /**
     * Constructor for Bamboo.
     * Sets up the initial appearance.
     */
    public Bamboo()
    {
        // Set the bamboo image - users should replace with actual bamboo.png
        setImage("bamboo.png");
    }
    
    /**
     * Act - do whatever the Bamboo wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add a gentle floating animation to make the bamboo more visible
        animate();
    }
    
    /**
     * Create a floating animation effect.
     */
    private void animate()
    {
        animationCounter++;
        
        // Use sine wave for smooth up-down motion
        if (animationCounter % 60 == 0) {
            // Reset counter to prevent overflow
            animationCounter = 0;
        }
        
        // Create a subtle pulsing effect by changing transparency
        int transparency = 200 + (int)(55 * Math.sin(animationCounter * 0.1));
        getImage().setTransparency(transparency);
    }
}
