# PandaQuest - Greenfoot Scenario

🐼 **A fun educational game for learning Java and Object-Oriented Programming!**

## Quick Start

1. **Install Greenfoot**: Download from [greenfoot.org](https://www.greenfoot.org/)
2. **Open Scenario**: In Greenfoot, go to `Scenario` → `Open...` and select this folder
3. **Add Images**: See instructions below or use built-in Greenfoot images
4. **Compile**: Click the "Compile All" button
5. **Run**: Right-click on `PandaWorld` → `new PandaWorld()` → Click "Run"

## What's Included

- ✅ **5 Java Classes**: PandaWorld, Panda, Bamboo, Enemy, Wall
- ✅ **Complete Documentation**: README_GREENFOOT.md with detailed instructions
- ✅ **Migration Notes**: MIGRATION_NOTES.md explaining the conversion
- ✅ **Project Configuration**: project.greenfoot for Greenfoot IDE
- ✅ **Image Guidelines**: images/README.md for adding graphics

## Game Overview

- **Objective**: Control a panda to collect all bamboo while avoiding enemies
- **Controls**: Arrow keys to move
- **Win**: Collect all 5 bamboo items
- **Lose**: Get hit by enemies 3 times

## Image Setup (Required)

### Option A: Quick Start with Built-in Images

Edit the `setImage()` calls in each .java file:
- Panda.java: `setImage("elephant.png");`
- Bamboo.java: `setImage("wombat.png");`
- Enemy.java: `setImage("ant.png");`
- Wall.java: `setImage("brick.jpg");`

### Option B: Custom Images

Place these files in the scenario folder or `images/` directory:
- panda.png (40-50px)
- bamboo.png (30-40px)
- enemy.png (40-50px)
- wall.png (50px)

## Documentation

📖 **For complete setup and usage instructions, see:**
- **[README_GREENFOOT.md](README_GREENFOOT.md)** - Full setup guide, controls, and troubleshooting
- **[MIGRATION_NOTES.md](MIGRATION_NOTES.md)** - Technical details about the conversion

## Project Structure

```
greenfoot-scenario/
├── PandaWorld.java          # Main game world
├── Panda.java               # Player character
├── Bamboo.java              # Collectible item
├── Enemy.java               # Enemy character  
├── Wall.java                # Obstacles
├── project.greenfoot        # Greenfoot configuration
├── README.md                # This file
├── README_GREENFOOT.md      # Detailed setup guide
├── MIGRATION_NOTES.md       # Conversion documentation
└── images/
    └── README.md            # Image guidelines
```

## Educational Value

This project teaches:
- Object-Oriented Programming (inheritance, encapsulation, polymorphism)
- Game development basics (game loop, collision detection, user input)
- Problem-solving and algorithm design

Perfect for AP Computer Science A students! 🎓

## Need Help?

- See **[README_GREENFOOT.md](README_GREENFOOT.md)** for troubleshooting
- Check inline code comments in .java files
- Visit [greenfoot.org/doc](https://www.greenfoot.org/doc) for Greenfoot documentation

---

**Have fun coding and playing!** 🐼🎋
