# PandaQuest

A Java-based puzzle game inspired by Minesweeper where players must clear the board by identifying all safe tiles while avoiding bamboo!

## Game Description

PandaQuest is a progressive puzzle game where:
- The player's goal is to clear the board by revealing all safe tiles
- Selecting a tile without bamboo reveals either a number (indicating adjacent bamboo) or an empty space
- If the player selects a tile containing bamboo, they lose a life and must restart the current level
- Each level progressively increases in difficulty by expanding the grid size and the number of bamboo shoots
- Certain tiles may contain power-ups that help the player

## Features

### Core Gameplay
- **Grid-based puzzle mechanics**: Similar to Minesweeper with tile revealing
- **Life system**: Start with 3 lives; lose a life when hitting bamboo
- **Progressive difficulty**: Each level increases grid size and bamboo count
  - Level 1: 6×6 grid with 5 bamboo
  - Level 2: 7×7 grid with 7 bamboo
  - Level 3: 8×8 grid with 9 bamboo
  - And so on...

### Power-ups
Three types of power-ups may appear on tiles:
- **Extra Life**: Grants an additional life
- **Reveal Safe**: Automatically reveals a random safe tile
- **Reveal Adjacent**: Reveals all adjacent tiles (implemented via auto-reveal)

### Game Mechanics
- **Auto-reveal**: When you reveal an empty tile (0 adjacent bamboo), all adjacent safe tiles are automatically revealed
- **Level completion**: Complete a level by revealing all safe tiles
- **Level restart**: Hit bamboo and you lose a life, restarting the current level
- **Game over**: Run out of lives and the game ends

## Building and Running

### Requirements
- Java 11 or higher
- Maven 3.6 or higher

### Build the Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package the Application
```bash
mvn package
```

### Run the Game
```bash
java -jar target/pandaquest-1.0.0.jar
```

Or run directly from compiled classes:
```bash
java -cp target/classes com.pandaquest.PandaQuest
```

## How to Play

1. Start the game and you'll see a grid of tiles
   - `■` represents unrevealed tiles
   - `⭐` represents tiles with power-ups
   - Numbers show how many bamboo shoots are in adjacent tiles
   - `🎋` reveals bamboo when you hit it

2. Enter coordinates to reveal a tile:
   ```
   Enter row and column (e.g., 2 3) or 'q' to quit: 
   ```

3. Type two numbers separated by a space (e.g., `0 0` for top-left corner)

4. Strategy tips:
   - Start with corners or edges for better odds
   - Use the numbers to deduce where bamboo is located
   - Avoid suspicious areas with high numbers
   - Power-ups (⭐) are visible before you select them

5. Complete the level by revealing all safe tiles, then proceed to the next level

## Project Structure

```
src/
├── main/java/com/pandaquest/
│   ├── Board.java          # Game board management
│   ├── Game.java           # Game state and logic
│   ├── PandaQuest.java     # Main game interface
│   ├── PowerUp.java        # Power-up types enum
│   └── Tile.java           # Individual tile class
└── test/java/com/pandaquest/
    ├── BoardTest.java      # Board class tests
    ├── GameTest.java       # Game class tests
    └── TileTest.java       # Tile class tests
```

## Development

### Running Tests
The project includes comprehensive unit tests:
- `TileTest`: Tests tile state and properties
- `BoardTest`: Tests board initialization and gameplay
- `GameTest`: Tests game logic and progression

Run all tests with:
```bash
mvn test
```

### Code Style
The project follows standard Java conventions:
- JavaDoc comments for all public methods
- Clear naming conventions
- Separation of concerns (Model-View-Controller pattern)

## License

This project is part of the AP Computer Science A curriculum.