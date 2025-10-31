# PandaQuest - Greenfoot Scenario Guide

Welcome to the PandaQuest Greenfoot scenario! This guide will help you set up and run the game in Greenfoot.

## What is PandaQuest?

PandaQuest is an educational game where you control a panda character that must collect bamboo while avoiding enemies. The game demonstrates core object-oriented programming concepts including:
- Inheritance (all game objects extend Actor or World)
- Encapsulation (private fields and methods)
- Polymorphism (different actors with different behaviors)
- Event handling (keyboard input and collision detection)

## Prerequisites

1. **Greenfoot** - Download and install Greenfoot from [greenfoot.org](https://www.greenfoot.org/)
   - Greenfoot is a free Java IDE designed for learning programming
   - Compatible with Windows, macOS, and Linux

## Setup Instructions

### Option 1: Open the Scenario Directly

1. Download or clone this repository
2. Open Greenfoot
3. Go to `Scenario` → `Open...`
4. Navigate to the `greenfoot-scenario` folder in this repository
5. Select the folder and click "Open"

### Option 2: Import into a New Scenario

1. Create a new scenario in Greenfoot (`Scenario` → `New Java Scenario...`)
2. Copy all `.java` files from `greenfoot-scenario/` into your new scenario folder
3. Copy the `project.greenfoot` file to your scenario folder (optional but recommended)
4. In Greenfoot, click `Scenario` → `Close and Open Recent` and select your scenario

## Adding Images

The scenario requires image files for the game actors. You have two options:

### Option A: Use Placeholder Images (Quick Start)

Greenfoot comes with a library of images. To use them temporarily:

1. Open each `.java` file (Panda.java, Bamboo.java, Enemy.java, Wall.java)
2. In the constructor, change the `setImage()` calls to use Greenfoot's built-in images:
   - **Panda.java**: Change to `setImage("elephant.png");` or `"bee.png"`
   - **Bamboo.java**: Change to `setImage("wombat.png");` or `"leaf.png"`
   - **Enemy.java**: Change to `setImage("ant.png");` or `"ladybug.png"`
   - **Wall.java**: Change to `setImage("brick.jpg");` or `"steel.jpg"`

### Option B: Add Custom Images

1. Create or download PNG images for your game characters
2. Place them in the `greenfoot-scenario/images/` folder
3. In Greenfoot, right-click on each class and select "Set image..."
4. Choose your custom image files

**Recommended Image Specifications:**
- **panda.png**: 40-50px square, transparent background
- **bamboo.png**: 30-40px square, transparent background
- **enemy.png**: 40-50px square, transparent background
- **wall.png**: 50px square, can be a tiled texture
- **cell.jpg**: 50px square background texture (optional)

See `greenfoot-scenario/images/README.md` for more details on finding or creating images.

## Running the Game

1. Once the scenario is open in Greenfoot, click the **Compile** button to compile all classes
2. Right-click on `PandaWorld` in the class diagram
3. Select `new PandaWorld()` to create an instance of the world
4. Click the **Run** button to start the game
5. Use the **Reset** button to restart the game at any time

## How to Play

### Controls
- **Arrow Keys**: Move the panda
  - Up Arrow: Move up
  - Down Arrow: Move down
  - Left Arrow: Move left
  - Right Arrow: Move right

### Objective
- Collect all 5 bamboo items scattered around the world
- Avoid touching the red enemies
- Navigate around walls and obstacles

### Scoring
- Each bamboo collected: **+10 points**
- Hit by an enemy: Lose 1 health (you have 3 health total)
- Collect all 5 bamboo to win!

### Game Mechanics
- **Invincibility**: After being hit by an enemy, you become temporarily invincible (flashing effect)
- **Enemy Movement**: Enemies patrol the world and change direction when hitting walls
- **Walls**: Cannot be passed by the panda or enemies

## Modifying the Game

The scenario is designed to be easily modified for learning purposes. Here are some ideas:

### Easy Modifications
1. **Change Movement Speed**: In `Panda.java`, modify the `speed` variable
2. **Add More Bamboo**: In `PandaWorld.java`, add more `addObject(new Bamboo(), x, y);` calls
3. **Change Win Condition**: In `PandaWorld.addScore()`, modify the bamboo count required to win
4. **Adjust Enemy Speed**: In `Enemy.java`, change the `moveInterval` variable

### Intermediate Modifications
1. **Add Power-ups**: Create a new Actor class for special items
2. **Multiple Enemy Types**: Extend Enemy class with different behaviors
3. **Add Levels**: Create multiple world configurations
4. **Add Sound Effects**: Uncomment sound lines and add .wav files to the `sounds/` folder

### Advanced Modifications
1. **Add Animation**: Implement sprite sheets for character animation
2. **Create AI Behaviors**: Make enemies chase the player
3. **Add a Menu System**: Create title screen and game over screens
4. **Implement Saving**: Save high scores to a file

## Troubleshooting

### "Cannot find symbol" errors
- Make sure all `.java` files are in the same directory
- Click `Tools` → `Clean Up` in Greenfoot
- Click the `Compile` button again

### Images not displaying
- Check that image file names match exactly (case-sensitive)
- Verify images are in PNG or JPG format
- Try using Greenfoot's built-in images first (see Option A above)
- Right-click on a class and select "Set image..." to manually assign images

### Game runs too fast/slow
- Use the speed slider at the bottom of the Greenfoot window
- Adjust the `moveInterval` in Enemy.java for enemy speed
- Modify movement logic in Panda.java for player speed

### Actors not appearing in the world
- Check the `prepare()` method in `PandaWorld.java`
- Verify X,Y coordinates are within the world bounds (0-15 for X, 0-11 for Y)
- Make sure classes are compiled before running

## Project Structure

```
greenfoot-scenario/
├── PandaWorld.java      # Main world class (extends World)
├── Panda.java           # Player character (extends Actor)
├── Bamboo.java          # Collectible item (extends Actor)
├── Enemy.java           # Enemy character (extends Actor)
├── Wall.java            # Obstacle (extends Actor)
├── project.greenfoot    # Greenfoot project configuration
└── images/              # Directory for image files
    └── README.md        # Guide for adding images
```

## Learning Resources

- **Greenfoot Documentation**: [greenfoot.org/doc](https://www.greenfoot.org/doc)
- **Greenfoot Tutorials**: Built into the Greenfoot IDE (`Help` → `Greenfoot Tutorial`)
- **Java Documentation**: Understanding basic Java is helpful for modifying the code

## Educational Objectives

This project helps students learn:

1. **Object-Oriented Programming**
   - Classes and objects
   - Inheritance hierarchies
   - Method overriding (act() method)

2. **Game Development Concepts**
   - Game loop (act-method-based)
   - Collision detection
   - User input handling
   - Score tracking

3. **Problem Solving**
   - Debugging techniques
   - Algorithm design
   - Code organization

## Support and Contributions

If you encounter issues or have suggestions for improving this scenario:
1. Check the troubleshooting section above
2. Review the code comments in each `.java` file
3. Consult Greenfoot documentation
4. Create an issue in the repository

## License

This project is created for educational purposes. Feel free to modify and extend it for learning!

---

**Happy Coding and Have Fun Playing PandaQuest!** 🐼🎋
