package com.pandaquest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Game class.
 */
public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialGameState() {
        assertEquals(1, game.getCurrentLevel(), "Game should start at level 1");
        assertEquals(3, game.getLives(), "Game should start with 3 lives");
        assertFalse(game.isGameOver(), "Game should not be over initially");
        assertFalse(game.isLevelComplete(), "Level should not be complete initially");
        assertNotNull(game.getBoard(), "Game should have a board");
    }

    @Test
    public void testSelectValidTile() {
        Board board = game.getBoard();
        // Find a safe tile to select
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (!board.getTile(i, j).hasBamboo()) {
                    boolean selected = game.selectTile(i, j);
                    assertTrue(selected, "Selecting valid tile should return true");
                    return;
                }
            }
        }
    }

    @Test
    public void testSelectInvalidPosition() {
        boolean selected = game.selectTile(-1, -1);
        assertFalse(selected, "Selecting invalid position should return false");
    }

    @Test
    public void testSelectAlreadyRevealedTile() {
        Board board = game.getBoard();
        // Find a safe tile and reveal it
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (!board.getTile(i, j).hasBamboo()) {
                    game.selectTile(i, j);
                    boolean selected = game.selectTile(i, j);
                    assertFalse(selected, "Selecting already revealed tile should return false");
                    return;
                }
            }
        }
    }

    @Test
    public void testSelectBambooTileLosesLife() {
        int initialLives = game.getLives();
        Board board = game.getBoard();
        
        // Find and select a bamboo tile
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (tile.hasBamboo()) {
                    game.selectTile(i, j);
                    assertEquals(initialLives - 1, game.getLives(), 
                        "Lives should decrease after hitting bamboo");
                    return;
                }
            }
        }
    }

    @Test
    public void testReset() {
        game.selectTile(0, 0);
        game.reset();
        
        assertEquals(1, game.getCurrentLevel(), "Level should reset to 1");
        assertEquals(3, game.getLives(), "Lives should reset to 3");
        assertFalse(game.isGameOver(), "Game should not be over after reset");
        assertFalse(game.isLevelComplete(), "Level should not be complete after reset");
    }

    @Test
    public void testNextLevelIncreasesLevel() {
        // Manually set level complete
        Board board = game.getBoard();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                if (!tile.hasBamboo()) {
                    board.revealTile(i, j);
                }
            }
        }
        
        if (game.isLevelComplete()) {
            game.nextLevel();
            assertEquals(2, game.getCurrentLevel(), "Level should increase after nextLevel()");
        }
    }

    @Test
    public void testBoardSizeIncreasesWithLevel() {
        Board level1Board = game.getBoard();
        int level1Size = level1Board.getRows() * level1Board.getCols();
        
        // Complete level and move to next
        for (int i = 0; i < level1Board.getRows(); i++) {
            for (int j = 0; j < level1Board.getCols(); j++) {
                Tile tile = level1Board.getTile(i, j);
                if (!tile.hasBamboo()) {
                    level1Board.revealTile(i, j);
                }
            }
        }
        
        if (game.isLevelComplete()) {
            game.nextLevel();
            Board level2Board = game.getBoard();
            int level2Size = level2Board.getRows() * level2Board.getCols();
            assertTrue(level2Size > level1Size, 
                "Board size should increase in next level");
        }
    }
}
