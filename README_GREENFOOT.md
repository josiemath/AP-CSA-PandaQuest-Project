# PandaQuest - Greenfoot Conversion Guide

This directory contains Greenfoot-compatible Java files for the PandaQuest game.

## Overview

PandaQuest is a puzzle game inspired by Minesweeper where players control a panda navigating through a grid-based world, revealing tiles while avoiding bamboo obstacles.

## Converted Files

The following Java classes have been converted to work with the Greenfoot API:

### Core Game Files

1. **PandaWorld.java** - Main game world (extends `greenfoot.World`)
   - Manages the game grid and state
   - Handles tile revealing logic
   - Tracks level, lives, and score
   - Implements auto-reveal for empty tiles

2. **Panda.java** - Player character (extends `greenfoot.Actor`)
   - Controlled with arrow keys for movement
   - Press SPACE to reveal tiles
   - Movement delay counter prevents too-fast movement

3. **Bamboo.java** - Obstacle actor (extends `greenfoot.Actor`)
   - Static obstacle that appears when revealed
   - Causes player to lose a life when hit

4. **TileMarker.java** - Visual indicator (extends `greenfoot.Actor`)
   - Displays number of adjacent bamboo shoots
   - Shows empty tiles in gray

### Stub Files (For Future Expansion)

5. **Enemy.java** - Placeholder for potential enemy characters
   - Currently not used in gameplay
   - Ready for future implementation

## Placeholder Images

The `images/` directory contains simple placeholder images:

- **panda.png** (50x50) - Panda face with black ears and eyes
- **bamboo.png** (50x50) - Green bamboo shoot with leaves
- **enemy.png** (50x50) - Red circle with angry face
- **background.png** (400x400) - Light blue grid background

**Important:** These are basic placeholder images. Replace them with better artwork for a polished game!

## How to Use in Greenfoot

### Setting Up a New Greenfoot Scenario

1. **Create New Scenario:**
   - Open Greenfoot
   - Click "Scenario" → "New Java Scenario..."
   - Choose a name and location for your scenario

2. **Copy Java Files:**
   - Copy all `.java` files from this directory to your new scenario folder
   - Files to copy: `PandaWorld.java`, `Panda.java`, `Bamboo.java`, `Enemy.java`, `TileMarker.java`

3. **Copy Images:**
   - Copy all `.png` files from the `images/` directory to your scenario's `images/` folder
   - Files to copy: `panda.png`, `bamboo.png`, `enemy.png`, `background.png`

4. **Compile in Greenfoot:**
   - Click the "Compile All" button
   - Fix any compilation errors if they appear

5. **Set World Class:**
   - Right-click on `PandaWorld` in the class diagram
   - Select "new PandaWorld()"
   - The game world will be created

6. **Assign Images to Actors:**
   - Right-click on `Panda` class → "Set Image..." → choose `panda.png`
   - Right-click on `Bamboo` class → "Set Image..." → choose `bamboo.png`
   - Right-click on `Enemy` class → "Set Image..." → choose `enemy.png`
   - Right-click on `PandaWorld` class → "Set Image..." → choose `background.png`

7. **Run the Game:**
   - Click the "Run" button to start
   - Use arrow keys to move the panda
   - Press SPACE to reveal tiles

## Key Differences from Original

The Greenfoot conversion differs from the original console-based version:

### What Was Converted

- ✅ Core game logic (tile revealing, bamboo detection)
- ✅ Level system with progressive difficulty
- ✅ Lives and scoring system
- ✅ Auto-reveal for empty tiles
- ✅ Movement-based interaction (instead of coordinate input)

### What Needs Manual Implementation

The following features from the original need additional work:

1. **Power-Ups** - The original had power-ups (Extra Life, Reveal Safe, Reveal Adjacent)
   - Need to create a `PowerUp` actor class
   - Implement random placement
   - Add visual indicators and effects

2. **Visual Tile Indicators** - Better visual feedback for revealed tiles
   - The `TileMarker` class is included but not fully integrated
   - Consider adding it to `PandaWorld.revealTile()` method

3. **Level Progression** - Currently stops after completing a level
   - Implement level reset and continuation
   - Add "Press SPACE" detection to start next level

4. **Better Input Handling** - Key repeat can be improved
   - Current implementation uses a simple delay counter
   - Consider using `Greenfoot.isKeyDown()` state tracking

5. **Sound Effects** - No audio in this conversion
   - Add sound for revealing tiles
   - Add sound for hitting bamboo
   - Add music for background

6. **Animations** - Static images only
   - Add sprite animations for panda movement
   - Add effects for revealing tiles
   - Add particle effects for bamboo hits

## Manual Steps Remaining

To complete the Greenfoot scenario, you should:

1. **Improve Graphics:**
   - Replace placeholder images with better artwork
   - Consider using sprite sheets for animations
   - Add visual effects for game events

2. **Enhance TileMarker Integration:**
   - Modify `PandaWorld.revealTile()` to add `TileMarker` actors for revealed tiles
   - Example: `addObject(new TileMarker(adjacentBamboo), x, y);`

3. **Add Power-Ups:**
   - Create a `PowerUp` actor class
   - Add power-up placement in `PandaWorld.initializeGame()`
   - Implement collection and effects in `Panda.act()`

4. **Implement Level Continuation:**
   - Add key detection in `PandaWorld.act()` after level complete
   - Reset grid and increase difficulty
   - Clear old actors and prepare new level

5. **Add Sound and Music:**
   - Import sound files to the sounds folder
   - Use `Greenfoot.playSound()` for effects
   - Add background music with looping

6. **Polish UI:**
   - Improve the text display for score/lives/level
   - Add a proper title screen
   - Create game over and victory screens

## Compiling Outside Greenfoot

While these files are designed for Greenfoot, you can compile them for testing if you have the Greenfoot JAR:

```bash
javac -cp /path/to/greenfoot.jar *.java
```

However, running them requires the full Greenfoot environment.

## Original Source Code

The original PandaQuest project uses Maven and standard Java:
- Located in the `src/` directory of the repository
- Uses console-based input/output
- Package structure: `com.pandaquest`

The original classes that were adapted:
- `com.pandaquest.Game` → `PandaWorld`
- `com.pandaquest.Board` → Integrated into `PandaWorld`
- `com.pandaquest.Tile` → Replaced with grid arrays in `PandaWorld`
- `com.pandaquest.PowerUp` → Not yet implemented (stub needed)

## Known Limitations

1. **No Package Declarations** - Required by Greenfoot, but makes code organization harder
2. **Blocking Code Removed** - Original used `Scanner` and blocking input; replaced with act-based input
3. **Simplified Architecture** - Original had separate Model classes; converted version combines logic in World
4. **Limited Testing** - These files have not been compiled with actual Greenfoot due to conversion process

## Getting Help

If you encounter issues:
1. Check that all images are in the `images/` folder
2. Verify all classes are compiled (no red stripes in Greenfoot)
3. Make sure `PandaWorld` is set as the world class
4. Check the Greenfoot output console for error messages

## License

This project is part of the AP Computer Science A curriculum.

---

**Note:** This conversion was created automatically from the original console-based PandaQuest game. The original source code should be referenced for any clarifications on game logic or behavior.
