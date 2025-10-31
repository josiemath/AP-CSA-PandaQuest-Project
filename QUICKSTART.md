# Quick Start Guide

## Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

## Build & Run (Quick)

```bash
# Clone the repository (if not already done)
git clone https://github.com/josiemath/AP-CSA-PandaQuest-Project.git
cd AP-CSA-PandaQuest-Project

# Build the project
mvn clean package

# Run the game
java -jar target/pandaquest-1.0.0.jar
```

## How to Play

1. **Starting the game**: Run the JAR file and you'll see a grid

2. **Reading the board**:
   - `■` = Unrevealed tile
   - `⭐` = Power-up (visible before selection)
   - `[space]` = Empty tile (0 adjacent bamboo)
   - `1-8` = Number indicating adjacent bamboo tiles
   - `🎋` = Bamboo (you'll see this if you hit one)

3. **Making moves**: Enter row and column coordinates separated by space
   ```
   Enter row and column (e.g., 2 3) or 'q' to quit: 0 0
   ```

4. **Power-ups**: When you reveal a tile with ⭐:
   - **Extra Life** (❤️): Adds one life
   - **Reveal Safe**: Automatically reveals a random safe tile
   - **Reveal Adjacent**: Reveals all adjacent safe tiles

5. **Winning a level**: Reveal all safe tiles (non-bamboo)

6. **Losing a life**: Hit bamboo (🎋) and the level restarts

7. **Game over**: Lose all 3 lives

## Strategy Tips

1. Start with corners or edges - they have fewer neighbors
2. Pay attention to numbers - they tell you where bamboo is
3. Use process of elimination to deduce safe tiles
4. Power-ups (⭐) are always on safe tiles
5. Empty tiles (0) auto-reveal adjacent tiles, creating safe zones

## Example Game Session

```
=== Welcome to PandaQuest! ===
Clear the board by revealing all safe tiles.
Avoid the bamboo or you'll lose a life!

    0  1  2  3  4  5 
  ------------------
 0| ■  ■  ■  ■  ■  ■ 
 1| ■  ■  ■  ■  ■  ⭐
 2| ■  ■  ■  ■  ■  ■ 
 3| ■  ■  ■  ■  ■  ■ 
 4| ■  ■  ■  ■  ■  ■ 
 5| ■  ■  ■  ■  ■  ■ 

Level: 1 | Lives: ❤️❤️❤️
Enter row and column (e.g., 2 3) or 'q' to quit: 0 0

    0  1  2  3  4  5 
  ------------------
 0| 1  ■  ■  ■  ■  ■    <- Revealed: 1 adjacent bamboo
 1| ■  ■  ■  ■  ■  ⭐
 2| ■  ■  ■  ■  ■  ■ 
 3| ■  ■  ■  ■  ■  ■ 
 4| ■  ■  ■  ■  ■  ■ 
 5| ■  ■  ■  ■  ■  ■ 

Level: 1 | Lives: ❤️❤️❤️
Enter row and column (e.g., 2 3) or 'q' to quit: 1 5

Power-up collected! [Extra Life gained]

    0  1  2  3  4  5 
  ------------------
 0| 1  ■  ■  ■  ■  ■    <- Got power-up
 1| ■  ■  ■  ■  ■  2     Lives increased!
 2| ■  ■  ■  ■  ■  ■ 
 3| ■  ■  ■  ■  ■  ■ 
 4| ■  ■  ■  ■  ■  ■ 
 5| ■  ■  ■  ■  ■  ■ 

Level: 1 | Lives: ❤️❤️❤️❤️
```

## Common Commands

- **Play a tile**: `row col` (e.g., `2 3`)
- **Quit game**: `q` or `quit`
- **Continue to next level**: `yes` or `y` (when prompted)
- **Stop after level**: `no` or `n` (when prompted)

## Troubleshooting

**Game won't start**:
- Ensure Java 11+ is installed: `java -version`
- Rebuild: `mvn clean package`

**Tests fail**:
- Check Java version
- Clean build: `mvn clean test`

**Can't find JAR**:
- Make sure you ran `mvn package`
- Check `target/` directory for `pandaquest-1.0.0.jar`

## Development Commands

```bash
# Run all tests
mvn test

# Clean build
mvn clean compile

# Run specific test class
mvn test -Dtest=GameTest

# Run game from classes (without JAR)
java -cp target/classes com.pandaquest.PandaQuest
```

## Need Help?

- See README.md for detailed documentation
- See IMPLEMENTATION_SUMMARY.md for technical details
- Check the source code in `src/main/java/com/pandaquest/`

Have fun playing PandaQuest! 🐼🎋
