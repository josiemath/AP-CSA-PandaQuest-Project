package com.pandaquest;

import java.util.Scanner;

/**
 * Console-based user interface for PandaQuest.
 */
public class PandaQuest {
    private final Game game;
    private final Scanner scanner;

    /**
     * Creates a new PandaQuest game interface.
     */
    public PandaQuest() {
        this.game = new Game();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts and runs the game.
     */
    public void play() {
        System.out.println("=== Welcome to PandaQuest! ===");
        System.out.println("Clear the board by revealing all safe tiles.");
        System.out.println("Avoid the bamboo or you'll lose a life!");
        System.out.println();

        while (!game.isGameOver()) {
            playLevel();
            
            if (game.isLevelComplete() && !game.isGameOver()) {
                System.out.println("\n🎉 Level " + game.getCurrentLevel() + " Complete!");
                System.out.println("Ready for the next level? (yes/no)");
                String response = scanner.nextLine().trim().toLowerCase();
                
                if (response.equals("yes") || response.equals("y")) {
                    game.nextLevel();
                } else {
                    System.out.println("Thanks for playing! Final Level: " + game.getCurrentLevel());
                    break;
                }
            }
        }

        if (game.isGameOver()) {
            System.out.println("\n💀 Game Over! You ran out of lives.");
            System.out.println("Final Level: " + game.getCurrentLevel());
        }

        scanner.close();
    }

    /**
     * Plays the current level.
     */
    private void playLevel() {
        while (!game.isLevelComplete() && !game.isGameOver()) {
            displayBoard();
            System.out.println("\nLevel: " + game.getCurrentLevel() + " | Lives: " + "❤️".repeat(game.getLives()));
            System.out.print("Enter row and column (e.g., 2 3) or 'q' to quit: ");
            
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please enter row and column separated by space.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                
                if (!game.selectTile(row, col)) {
                    System.out.println("Invalid selection. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers.");
            }
        }
    }

    /**
     * Displays the current board state.
     */
    private void displayBoard() {
        Board board = game.getBoard();
        System.out.println();
        
        // Print column headers
        System.out.print("   ");
        for (int j = 0; j < board.getCols(); j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();

        // Print separator
        System.out.print("  ");
        for (int j = 0; j < board.getCols(); j++) {
            System.out.print("---");
        }
        System.out.println();

        // Print board rows
        for (int i = 0; i < board.getRows(); i++) {
            System.out.printf("%2d|", i);
            for (int j = 0; j < board.getCols(); j++) {
                Tile tile = board.getTile(i, j);
                
                if (tile.isRevealed()) {
                    if (tile.hasBamboo()) {
                        System.out.print(" 🎋");
                    } else if (tile.getAdjacentBambooCount() > 0) {
                        System.out.printf(" %d ", tile.getAdjacentBambooCount());
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    if (tile.hasPowerUp()) {
                        System.out.print(" ⭐");
                    } else {
                        System.out.print(" ■ ");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Main entry point for the game.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        PandaQuest game = new PandaQuest();
        game.play();
    }
}
