# Creating Simple Placeholder Images

If you want to create very basic placeholder images quickly, you can use simple colored rectangles until you have proper game art.

## Using ImageMagick (if available)

If ImageMagick is installed, you can create simple colored squares:

```bash
# Panda - gray square
convert -size 50x50 xc:gray panda.png

# Bamboo - green square
convert -size 40x40 xc:green bamboo.png

# Enemy - red square
convert -size 50x50 xc:red enemy.png

# Wall - brown square
convert -size 50x50 xc:brown wall.png
```

## Using GIMP or Paint

1. Open GIMP, Photoshop, Paint, or any image editor
2. Create a new image (50x50 pixels)
3. Fill with a solid color:
   - Panda: Gray or black & white
   - Bamboo: Green
   - Enemy: Red
   - Wall: Brown or gray
4. Save as PNG with transparency (for actors) or JPG (for walls)

## Using Online Tools

Free online image editors:
- [Pixlr.com](https://pixlr.com/) - Free online photo editor
- [Photopea.com](https://www.photopea.com/) - Like Photoshop, runs in browser
- [Piskel](https://www.piskelapp.com/) - Pixel art sprite editor

## Text-Based ASCII Art (Last Resort)

If you absolutely cannot create images, you can modify the Java code to use text:

In each Actor constructor, replace `setImage("filename.png");` with:

```java
// For Panda.java
GreenfootImage img = new GreenfootImage("P", 24, Color.BLACK, new Color(0,0,0,0));
setImage(img);

// For Bamboo.java
GreenfootImage img = new GreenfootImage("B", 24, Color.GREEN, new Color(0,0,0,0));
setImage(img);

// For Enemy.java
GreenfootImage img = new GreenfootImage("E", 24, Color.RED, new Color(0,0,0,0));
setImage(img);

// For Wall.java
GreenfootImage img = new GreenfootImage("#", 24, Color.DARK_GRAY, Color.DARK_GRAY);
setImage(img);
```

This creates text-based representations of each actor.

## Recommended: Use Greenfoot's Built-in Images

**The easiest solution is to use Greenfoot's built-in image library** as described in README_GREENFOOT.md. This requires no image creation and works immediately!
