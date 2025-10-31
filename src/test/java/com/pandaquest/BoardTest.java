package com.pandaquest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Board class.
 */
public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(5, 5, 5);
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(5, board.getRows(), "Board should have 5 rows");
        assertEquals(5, board.getCols(), "Board should have 5 columns");
        assertEquals(5, board.getBambooCount(), "Board should have 5 bamboo");
    }

    @Test
    public void testBoardHasCorrectNumberOfBamboo() {
        int bambooCount = 0;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getTile(i, j).hasBamboo()) {
                    bambooCount++;
                }
            }
        }
        assertEquals(5, bambooCount, "Board should have exactly 5 bamboo tiles");
    }

    @Test
    public void testAllTilesInitialized() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                assertNotNull(board.getTile(i, j), "All tiles should be initialized");
            }
        }
    }

    @Test
    public void testRevealSafeTile() {
        // Find a safe tile
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (!tile.hasBamboo()) {
                    boolean hitBamboo = board.revealTile(i, j);
                    assertFalse(hitBamboo, "Revealing safe tile should not hit bamboo");
                    assertTrue(tile.isRevealed(), "Safe tile should be revealed");
                    return;
                }
            }
        }
    }

    @Test
    public void testRevealBambooTile() {
        // Find a bamboo tile
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (tile.hasBamboo()) {
                    boolean hitBamboo = board.revealTile(i, j);
                    assertTrue(hitBamboo, "Revealing bamboo tile should return true");
                    assertTrue(tile.isRevealed(), "Bamboo tile should be revealed");
                    return;
                }
            }
        }
    }

    @Test
    public void testGetTileInvalidPosition() {
        assertNull(board.getTile(-1, 0), "Invalid negative row should return null");
        assertNull(board.getTile(0, -1), "Invalid negative col should return null");
        assertNull(board.getTile(100, 0), "Invalid large row should return null");
        assertNull(board.getTile(0, 100), "Invalid large col should return null");
    }

    @Test
    public void testIsNotClearedInitially() {
        assertFalse(board.isCleared(), "Board should not be cleared initially");
    }

    @Test
    public void testPlacePowerUps() {
        board.placePowerUps(3);
        int powerUpCount = 0;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getTile(i, j).hasPowerUp()) {
                    powerUpCount++;
                }
            }
        }
        assertEquals(3, powerUpCount, "Board should have exactly 3 power-ups");
    }

    @Test
    public void testAdjacentBambooCount() {
        // Test that non-bamboo tiles have correct adjacent counts
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (!tile.hasBamboo()) {
                    int count = tile.getAdjacentBambooCount();
                    assertTrue(count >= 0 && count <= 8, 
                        "Adjacent bamboo count should be between 0 and 8");
                }
            }
        }
    }
}
