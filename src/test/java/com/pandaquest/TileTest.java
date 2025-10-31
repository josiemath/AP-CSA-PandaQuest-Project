package com.pandaquest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tile class.
 */
public class TileTest {
    private Tile tile;

    @BeforeEach
    public void setUp() {
        tile = new Tile();
    }

    @Test
    public void testInitialState() {
        assertFalse(tile.hasBamboo(), "New tile should not have bamboo");
        assertFalse(tile.isRevealed(), "New tile should not be revealed");
        assertEquals(0, tile.getAdjacentBambooCount(), "New tile should have 0 adjacent bamboo");
        assertNull(tile.getPowerUp(), "New tile should not have power-up");
        assertFalse(tile.hasPowerUp(), "New tile should not have power-up");
    }

    @Test
    public void testSetHasBamboo() {
        tile.setHasBamboo(true);
        assertTrue(tile.hasBamboo(), "Tile should have bamboo after setting");
        
        tile.setHasBamboo(false);
        assertFalse(tile.hasBamboo(), "Tile should not have bamboo after unsetting");
    }

    @Test
    public void testSetRevealed() {
        tile.setRevealed(true);
        assertTrue(tile.isRevealed(), "Tile should be revealed after setting");
        
        tile.setRevealed(false);
        assertFalse(tile.isRevealed(), "Tile should not be revealed after unsetting");
    }

    @Test
    public void testSetAdjacentBambooCount() {
        tile.setAdjacentBambooCount(5);
        assertEquals(5, tile.getAdjacentBambooCount(), "Adjacent bamboo count should be 5");
        
        tile.setAdjacentBambooCount(0);
        assertEquals(0, tile.getAdjacentBambooCount(), "Adjacent bamboo count should be 0");
    }

    @Test
    public void testSetPowerUp() {
        tile.setPowerUp(PowerUp.EXTRA_LIFE);
        assertEquals(PowerUp.EXTRA_LIFE, tile.getPowerUp(), "Tile should have EXTRA_LIFE power-up");
        assertTrue(tile.hasPowerUp(), "Tile should have a power-up");
        
        tile.setPowerUp(null);
        assertNull(tile.getPowerUp(), "Tile should not have power-up after setting to null");
        assertFalse(tile.hasPowerUp(), "Tile should not have power-up after setting to null");
    }
}
