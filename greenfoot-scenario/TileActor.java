import greenfoot.*;

/**
 * Represents a single tile on the game board as a Greenfoot Actor.
 * Tiles can contain bamboo, be revealed, show adjacent bamboo counts, or have power-ups.
 */
public class TileActor extends Actor {
    private boolean hasBamboo;
    private boolean isRevealed;
    private int adjacentBambooCount;
    private PowerUp powerUp;
    private int row;
    private int col;
    
    private static final int TILE_SIZE = 40;

    /**
     * Creates a new tile at specified grid position.
     * @param row grid row
     * @param col grid column
     */
    public TileActor(int row, int col) {
        this.row = row;
        this.col = col;
        this.hasBamboo = false;
        this.isRevealed = false;
        this.adjacentBambooCount = 0;
        this.powerUp = null;
        updateImage();
    }

    /**
     * Act method called by Greenfoot each frame.
     */
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            PandaQuestWorld world = (PandaQuestWorld) getWorld();
            world.selectTile(row, col);
        }
    }
    
    /**
     * Updates the tile's visual appearance based on its state.
     */
    public void updateImage() {
        GreenfootImage img = new GreenfootImage(TILE_SIZE, TILE_SIZE);
        
        if (isRevealed) {
            img.setColor(Color.LIGHT_GRAY);
            img.fill();
            img.setColor(Color.DARK_GRAY);
            img.drawRect(0, 0, TILE_SIZE - 1, TILE_SIZE - 1);
            
            if (hasBamboo) {
                img.setColor(Color.RED);
                img.fillOval(10, 10, 20, 20);
            } else if (adjacentBambooCount > 0) {
                img.setColor(Color.BLACK);
                img.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
                img.drawString(String.valueOf(adjacentBambooCount), TILE_SIZE/2 - 8, TILE_SIZE/2 + 8);
            }
        } else {
            img.setColor(Color.GRAY);
            img.fill();
            img.setColor(Color.BLACK);
            img.drawRect(0, 0, TILE_SIZE - 1, TILE_SIZE - 1);
            
            if (powerUp != null) {
                img.setColor(Color.YELLOW);
                img.fillOval(10, 10, 20, 20);
            }
        }
        
        setImage(img);
    }

    /**
     * Gets whether this tile contains bamboo.
     * @return true if tile has bamboo, false otherwise
     */
    public boolean hasBamboo() {
        return hasBamboo;
    }

    /**
     * Sets whether this tile contains bamboo.
     * @param hasBamboo true to place bamboo on this tile
     */
    public void setHasBamboo(boolean hasBamboo) {
        this.hasBamboo = hasBamboo;
        updateImage();
    }

    /**
     * Gets whether this tile has been revealed.
     * @return true if revealed, false otherwise
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * Sets the revealed state of this tile.
     * @param revealed true to reveal the tile
     */
    public void setRevealed(boolean revealed) {
        this.isRevealed = revealed;
        updateImage();
    }

    /**
     * Gets the count of adjacent tiles with bamboo.
     * @return number of adjacent bamboo tiles
     */
    public int getAdjacentBambooCount() {
        return adjacentBambooCount;
    }

    /**
     * Sets the count of adjacent tiles with bamboo.
     * @param count number of adjacent bamboo tiles
     */
    public void setAdjacentBambooCount(int count) {
        this.adjacentBambooCount = count;
        updateImage();
    }

    /**
     * Gets the power-up on this tile, if any.
     * @return the power-up or null if none
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }

    /**
     * Sets a power-up on this tile.
     * @param powerUp the power-up to place
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
        updateImage();
    }

    /**
     * Checks if this tile has a power-up.
     * @return true if tile has power-up, false otherwise
     */
    public boolean hasPowerUp() {
        return powerUp != null;
    }
    
    /**
     * Gets the row position of this tile.
     * @return row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Gets the column position of this tile.
     * @return column position
     */
    public int getCol() {
        return col;
    }
}
