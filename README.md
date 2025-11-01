# PandaQuest

Welcome to PandaQuest — a beginner-friendly 2D platformer/adventure game built with Greenfoot. This README is written for someone new to programming and to the game. It explains what PandaQuest is, how to run and play it using Greenfoot, and gives a gentle introduction to the code so you can explore, learn, and contribute.

If you are brand new to coding: this project is intentionally simple and designed to teach programming concepts through hands-on play and small edits. If you're new to Greenfoot: don't worry — step-by-step instructions are included.

Table of contents
- About the game
- What you'll need
- How to open and run PandaQuest in Greenfoot
- Game controls & UI overview
- Gameplay and objectives
- Project structure (for absolute beginners)
- How to make small changes (mods) — edit one file to see results
- Debugging tips
- Contributing
- License
- Further learning resources

About the game
PandaQuest is an accessible 2D platformer where you control a panda exploring levels, collecting items, avoiding hazards, and solving small puzzles. It focuses on clean visuals, simple mechanics, and educational value — ideal for beginners learning Java and game programming.

What you'll need
- Greenfoot (recommended version: Greenfoot 3.x or the version matched to the project). Download: https://www.greenfoot.org
- Java (Greenfoot bundles a Java runtime for most installs, but ensure your system can run Greenfoot)
- This repository checked out locally (or downloaded as a ZIP) and opened inside Greenfoot

How to open and run PandaQuest in Greenfoot
1. Install Greenfoot and start the application.
2. Download/clone this repository or the PandaQuest project folder.
3. In Greenfoot, choose "Open Project..." and select the PandaQuest folder (the folder that contains src/, images/, sounds/ and the Greenfoot scenario files).
4. The project will open showing the world and actors in the Greenfoot UI (described next).
5. Click the "Play" (Act) or "Run" (Run) button in Greenfoot to run the game.
   - Use "Act" to step frame-by-frame.
   - Use "Run" to play continuously.

Greenfoot UI overview (quick tour)
- Scenario / World view: the central panel shows the game world and where actors (panda, enemies, items) are placed.
- Class diagram (left/right pane depending on version): shows classes (World, Actors) and lets you inspect code or create new instances.
- Editor: opens Java source files for each class. You can edit, compile, and test changes directly.
- Controls: the Greenfoot toolbar has Run, Act, Pause, Reset, Compile buttons. There's also a menu for scenario settings.
- Inspector: select an object in the world and inspect its properties (if implemented).

Game controls
- Movement: Arrow keys or WASD (project may be configured for one or both).
  - Left / A: move left
  - Right / D: move right
  - Up / W or Space: jump (or interact)
- Interact / Action: E, Enter, or Space (project may vary)
- Pause / Menu: Esc (if implemented)
Check the Controls.java or Player.java file in the source for the exact key bindings used.

Gameplay and objectives
- Objective: Guide the panda through the level to reach the exit or complete the level-specific goal.
- Collectibles: Items like stars, bamboo, or keys may unlock doors, increase score, or grant bonuses.
- Hazards: Spikes, pits, enemies that cause the player to lose health or restart the level.
- Puzzles: Simple switches, moving platforms, and keys/doors.
- Lives & Score: The project may implement lives, health, and a score — check the HUD class or GameStatus to see how they're tracked.

Project structure (simple explanation)
- src/ or the package root: Java source files (.java). Main classes you’ll see:
  - MyWorld (or GameWorld) — sets up the level and background.
  - Player (or Panda) — handles input, movement, collisions, animations.
  - Enemy — base for enemy behavior.
  - Platform, MovingPlatform — platform objects, static or dynamic.
  - Collectible (Star, Bamboo, Key) — things the panda can pick up.
  - HUD / ScoreBoard — displays score, lives, and other UI.
  - LevelManager / LevelLoader — handles level progression (if present).
- images/: contains sprite sheets and images used in the game.
- sounds/: sound effects and music.
- scenario file(s): Greenfoot scenario metadata.

Core programming concepts you'll see
- Classes and objects: Each game element (panda, enemy, platform) is a class/object.
- Methods: Functions that define behavior (act(), move(), jump()).
- Variables (fields): store state like position, speed, health.
- Inheritance: Common behaviors extracted into base classes (Enemy -> FlyingEnemy, GroundEnemy).
- Events and the act() loop: Each frame Greenfoot calls act() on actors to update them, creating the game loop.
- Collisions: Use Greenfoot methods like isTouching(), getOneIntersectingObject() to detect collisions.

How to make small changes (examples)
1. Change player speed
   - Open Player.java (or Panda.java).
   - Find the variable speed or moveSpeed and change its value (e.g., from 4 to 6).
   - Compile and run. Notice the panda moves faster.

2. Change the jump height
   - In Player.java find jump() or the vertical velocity variable (often named vy or jumpStrength).
   - Increase or decrease the value, compile and run.

3. Add a new collectible image
   - Place the new image (PNG) in images/.
   - Create a new class that extends Collectible and set its image in the constructor: setImage("new-item.png").
   - Add instances to the world or to the level file.

4. Modify level layout
   - If the world sets objects in code (inside MyWorld constructor), edit the placement lines like addObject(new Platform(), x, y).
   - If levels are data-driven (text files or tile maps), edit the level file or add a new level file.

A basic example (step-by-step)
- Change the panda’s starting position:
  1. Open MyWorld.java.
  2. Locate where the Player is created, e.g., addObject(player, 100, 300);
  3. Change the coordinates to move the start position, compile, and run to test.

Debugging tips for beginners
- Compile often. Fix errors as you make small edits — it’s easier to find the cause.
- Read compile error messages carefully; they usually point to the file and line number.
- Use System.out.println("message") to print variables to the console while running to see what's happening.
- Work on one small change at a time and test it.
- If the world doesn’t update after changes, press the “Reset” button or restart Greenfoot.

Contributing
We welcome contributions, especially from learners. Here are some simple ways to help:
- Bug fixes: Try to reproduce issues and submit fixes.
- Small features: New collectibles, enemies, or a sound effect.
- Documentation: Improve or expand this README, add comments to code explaining how things work.
- Levels: Add new levels or improve level design.
When contributing:
1. Fork the repository.
2. Create a new branch for your change (feature/my-change).
3. Make your changes, commit with clear messages.
4. Open a Pull Request describing what you changed and why.

Coding style & learning suggestions
- Keep methods small and focused: do one thing per method.
- Name variables clearly: playerSpeed is clearer than s.
- Add comments to explain non-obvious logic.
- Read one class at a time: start with Player, then World, then HUD.
- Use the Greenfoot API documentation while coding: it shows useful helper methods.

Accessibility & controls customization
- If you need to change key bindings (e.g., for accessibility), update the Player input handling code to use configurable keys or add a settings screen to change them.

License
This project uses [insert license name] — check the LICENSE file for details. If there’s no license file, contact the project owner before reusing code.

Further learning resources
- Greenfoot tutorials: https://www.greenfoot.org/doc
- Java beginners: "Head First Java" or Oracle Java tutorials online
- Small code changes practice: try tweaking values, images, or level layouts and see immediate results in Greenfoot

If anything is unclear or you want a guided walkthrough of the codebase (e.g., "explain Player.java line-by-line"), tell me which class/file you want to start with and I’ll walk you through it step-by-step.
