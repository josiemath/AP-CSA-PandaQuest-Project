# PandaQuest - Greenfoot Conversion

This directory contains a Greenfoot-compatible version of the PandaQuest game.

## Opening the Scenario in Greenfoot

1. **Install Greenfoot**: Download and install Greenfoot from https://www.greenfoot.org/
2. **Open the Scenario**: 
   - Launch Greenfoot
   - Click "Scenario" → "Open..."
   - Navigate to the `greenfoot-scenario/` directory in this repository
   - Select and open the directory

3. **Compile the Classes**:
   - Click "Compile All" button in Greenfoot
   - The classes should compile without errors

4. **Run the Game**:
   - Right-click on `PandaQuestWorld` in the class diagram
   - Select "new PandaQuestWorld()"
   - Click the "Run" button to start the game

## Game Controls

- **Mouse Click**: Click on any tile to reveal it
- **Space Bar**: Advance to the next level after completing a level
- **R Key**: Restart the game after game over

## Image Files

The `greenfoot-scenario/images/` directory contains placeholder image files:
- `panda.png` - Placeholder for panda/player character
- `bamboo.png` - Placeholder for bamboo (the hazard)
- `enemy.png` - Placeholder for enemies (if needed)
- `background.png` - Placeholder for background
- `tile.png` - Placeholder for unrevealed tiles
- `powerup.png` - Placeholder for power-up items

**Note**: These are minimal 1x1 pixel placeholder images. You should replace them with proper game graphics:
1. Create or find suitable 40x40 pixel (or larger) PNG images
2. Name them to match the filenames above
3. Copy them to the `greenfoot-scenario/images/` directory
4. Greenfoot will automatically use these images if referenced in the code

## Project Structure

```
greenfoot-scenario/
├── PandaQuestWorld.java    # Main World class (combines Game and Board logic)
├── TileActor.java          # Tile Actor class (represents individual tiles)
├── PowerUp.java            # Power-up types enum
└── images/                 # Image assets directory
    ├── panda.png
    ├── bamboo.png
    ├── enemy.png
    ├── background.png
    ├── tile.png
    └── powerup.png
```

## Converted Files

The following classes have been converted from the original source code to Greenfoot-compatible format:

### Successfully Converted:
- **PandaQuestWorld.java** (from `Game.java` + `Board.java`)
  - Extends `greenfoot.World`
  - Manages game state, level progression, and tile grid
  - Handles tile selection via mouse clicks
  - Implements power-up system and level advancement
  
- **TileActor.java** (from `Tile.java`)
  - Extends `greenfoot.Actor`
  - Represents individual tiles on the board
  - Handles click events to select tiles
  - Dynamically generates tile appearance based on state
  
- **PowerUp.java** (from `PowerUp.java`)
  - Enum type (no inheritance needed)
  - Defines three power-up types: EXTRA_LIFE, REVEAL_SAFE, REVEAL_ADJACENT

### Changes Made for Greenfoot Compatibility:
1. **Removed package declarations** - All classes are now in the default package
2. **Replaced console I/O with Greenfoot UI** - No more Scanner; uses mouse clicks and keyboard
3. **Converted to Actor-based model** - Tiles are now Actors that can be clicked
4. **Added act() methods** - Greenfoot's execution model for World and Actor classes
5. **Visual rendering** - Tiles now draw themselves using GreenfootImage
6. **Keyboard controls** - Space to advance levels, R to restart

## Original Source Files (Unchanged)

The original Java source files remain in the `src/main/java/com/pandaquest/` directory:
- `PandaQuest.java` - Console-based UI (not converted - uses Scanner)
- `Game.java` - Game logic (merged into PandaQuestWorld)
- `Board.java` - Board management (merged into PandaQuestWorld)
- `Tile.java` - Tile data (converted to TileActor)
- `PowerUp.java` - Power-up enum (converted)

## Features Ported to Greenfoot

✅ **Core Gameplay**:
- Grid-based puzzle mechanics
- Life system (start with 3 lives)
- Progressive difficulty (larger grids, more bamboo per level)
- Tile revealing with adjacent bamboo counts
- Auto-reveal of empty tiles

✅ **Power-ups**:
- Extra Life
- Reveal Safe (reveals a random safe tile)
- Reveal Adjacent (reveals adjacent tiles)

✅ **Game Mechanics**:
- Level completion detection
- Level restart on bamboo hit
- Game over when out of lives
- Level progression

## Features NOT Ported / Manual Steps Required

The following features from the original console version were adapted or simplified:

1. **Console UI** - The original `PandaQuest.java` used Scanner for console input
   - **Greenfoot equivalent**: Mouse clicks on tiles + keyboard controls
   
2. **Visual symbols** - Console version used Unicode emojis (■, ⭐, 🎋, ❤️)
   - **Greenfoot equivalent**: Colored rectangles and circles drawn programmatically
   - **Action needed**: Replace with proper image files in the `images/` directory

3. **Sound effects** - Code references "buzzer.wav" but file not included
   - **Action needed**: Add sound files to the Greenfoot scenario directory:
     - `buzzer.wav` - Played when hitting bamboo
     - Add other sound effects as desired (level complete, power-up collected, etc.)

4. **Maven build system** - Original uses Maven for building/testing
   - **Greenfoot equivalent**: Uses Greenfoot's built-in compiler
   - No external dependencies required

5. **Unit tests** - Original has JUnit tests in `src/test/`
   - **Greenfoot note**: Greenfoot scenarios don't typically include automated tests
   - Tests remain in original source for reference

## Customization Ideas

To enhance the Greenfoot version, consider:

1. **Better Graphics**: Replace placeholder images with custom artwork
2. **Animation**: Add animations for tile reveals, power-up collection
3. **Sound Effects**: Add sounds for clicks, reveals, level complete, game over
4. **Background Music**: Add looping background music
5. **Particle Effects**: Add visual effects for power-ups
6. **High Score System**: Track and display high scores
7. **Difficulty Settings**: Add easy/medium/hard modes
8. **Custom Levels**: Create specific level layouts instead of random generation

## Technical Notes

- All converted files use **only Greenfoot API and standard Java**
- No external libraries or dependencies required
- Tile images are generated programmatically by default (no image files required)
- World size adjusts automatically based on grid size
- Compatible with Greenfoot 3.x and later

## Troubleshooting

**Problem**: Classes won't compile  
**Solution**: Make sure all three `.java` files are in the same directory and Greenfoot is version 3.0 or later

**Problem**: Tiles don't appear  
**Solution**: Check that `PandaQuestWorld` constructor is being called. Try right-clicking the class and selecting "new PandaQuestWorld()"

**Problem**: Clicks don't register  
**Solution**: Make sure the scenario is running (not paused) and you're clicking on the tile actors themselves

**Problem**: Missing images warning  
**Solution**: This is expected - tiles draw themselves programmatically. You can add custom images if desired.

## Credits

Original PandaQuest game by josiemath  
Greenfoot conversion for AP Computer Science A curriculum  
Greenfoot by Michael Kölling and the Greenfoot Team

## License

This project is part of the AP Computer Science A curriculum.
