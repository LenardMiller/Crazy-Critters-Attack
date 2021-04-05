package main.misc;

import main.enemies.Enemy;
import main.pathfinding.Node;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;

import static main.Main.*;
import static main.pathfinding.UpdateClearance.updateClearance;
import static main.pathfinding.UpdateNode.updateNode;

public class Utilities {

    /**
     * For pathfinding.
     * Refreshes all pathfinding nodes
     */
    public static void updateNodes() {
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                node.setNotEnd((int) (node.position.x / NODE_SIZE), (int) (node.position.y / NODE_SIZE));
                node.checkTile();
            }
        }
        updateClearance();
        updateNode(start, null);
        enemies.forEach(Enemy::setCombatPoints);
    }

    /**
     * For pathfinding.
     * Calculates the maximum cost of a path.
     * @return the path's maximum cost
     */
    public static float maxCost() {
        float maxCost = 0;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost > maxCost && (node.isOpen || node.isClosed)) maxCost = node.totalCost;
            }
        }
        return maxCost;
    }

    /**
     * For pathfinding.
     * Calculates the minimum cost of a path.
     * @return the path's minimum cost
     */
    public static float minCost(float maxCost) {
        float minCost = maxCost;
        for (Node[] nodes : nodeGrid) {
            for (Node node : nodes) {
                if (node.totalCost < minCost && (node.isOpen || node.isClosed)) minCost = node.totalCost;
            }
        }
        return minCost;
    }

    /**
     * Finds the slope of a line.
     * @param p1 the first position
     * @param p2 the second position
     * @return the slope between the positions
     */
    public static float findSlope(PVector p1, PVector p2) {
        float m = (p2.y - p1.y) / (p2.x - p1.x);
        return m * -1;
    }

    /**
     * https://forum.processing.org/one/topic/pvector-anglebetween.html <p></p>
     * DOES NOT return the angle of a line between two points.
     * idk what this does.
     * @param p1 the first position
     * @param p2 the second position
     * @return something
     */
    public static float findAngleBetween(PVector p1, PVector p2) {
        float a = atan2((p1.y - p2.y), p1.x - p2.x);
        if (a < 0) a += TWO_PI;
        return a;
    }

    /**
     * Rounds down to a multiple of the rounder. Only works left of the decimal.
     * @param input number to be rounded
     * @param rounder number input will be rounded to a multiple of
     * @return input but rounded by rounder
     */
    public static int roundTo(float input, int rounder) {
        return ((int) (input / rounder)) * rounder;
    }

    /**
     * Rounds to a multiple of the rounder. Only works right of the decimal.
     * @param input number to be rounded
     * @param rounder number input will be a multiple of, must be less than 1
     * @return input rounded by the rounder
     */
    public static float roundTo(float input, float rounder) {
        return (round(input / rounder)) * rounder;
    }

    /**
     * Finds a random place for an enemy to spawn just offscreen.
     * @param p the PApplet
     * @return a random spawnpoint
     */
    public static PVector randomSpawnPosition(PApplet p) {
        int side = (int) p.random(4); //clockwise from top
        float centerOfBuffer = ((GRID_HEIGHT / 2f) - (BOARD_HEIGHT / 2f)) / 2f;
        float distAlongSide = p.random(-centerOfBuffer, BOARD_HEIGHT + centerOfBuffer);
        if (side == 0) return new PVector(distAlongSide, -centerOfBuffer); //top
        if (side == 1) return new PVector(BOARD_WIDTH + centerOfBuffer, distAlongSide); //right
        if (side == 2) return new PVector(distAlongSide, BOARD_HEIGHT + centerOfBuffer); //bottom
        return new PVector(-centerOfBuffer, distAlongSide); //left
    }

    /**
     * Returns the angle of a line between two points.
     * Compensates for Processing y being inverted.
     * @param v1 the first position
     * @param v2 the second position
     * @return the angle of a line between the two positions
     */
    public static float findAngle(PVector v1, PVector v2) {
        float angle = 0;
        PVector ratio = PVector.sub(v2, v1);
        if (v1.x == v2.x) { //if on the same x
            if (v1.y >= v2.y) { //if below target or on same y, angle right
                angle = 0;
            } else if (v1.y < v2.y) { //if above target, angle left
                angle = PI;
            }
        } else if (v1.y == v2.y) { //if on same y
            if (v1.x > v2.x) { //if  right of target, angle down
                angle = 3 * HALF_PI;
            } else if (v1.x < v2.x) { //if left of target, angle up
                angle = HALF_PI;
            }
        } else {
            if (v1.x < v2.x && v1.y > v2.y) { //if to left and below
                angle = (atan(abs(ratio.x) / abs(ratio.y)));
            } else if (v1.x < v2.x && v1.y < v2.y) { //if to left and above
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + HALF_PI;
            } else if (v1.x > v2.x && v1.y < v2.y) { //if to right and above
                angle = (atan(abs(ratio.x) / abs(ratio.y))) + PI;
            } else if (v1.x > v2.x && v1.y > v2.y) { //if to right and below
                angle = (atan(abs(ratio.y) / abs(ratio.x))) + 3 * HALF_PI;
            }
        }
        return angle;
    }

    /**
     * Finds the angle of a line from the origin to a position.
     * @param v the position to draw a line to
     * @return the angle of the line
     */
    public static float findAngle(PVector v) {
        return findAngle(new PVector(0, 0), v);
    }

    /**
     * Converts an angle to be between 0 and TWO_PI.
     * @param a the angle to convert
     * @return an angle between 0 and TWO_PI
     */
    public static float clampAngle(float a) {
        return a - TWO_PI * floor(a / TWO_PI);
    }

    /**
     * Finds the difference between two angles.
     * @param target angle to compare to
     * @param current angle to be compared
     * @return the angle between target and current
     */
    public static float angleDifference(float target, float current) {
        float diffA = -(current - target);
        float diffB = diffA - TWO_PI;
        float diffC = clampAngle(diffB);
        float f = min(abs(diffA), abs(diffB), abs(diffC));
        if (f == abs(diffA)) return diffA;
        if (f == abs(diffB)) return diffB;
        if (f == abs(diffC)) return diffC;
        return 0;
    }

    /**
     * A better tint function that can brighten images.
     * BEWARE OF SHALLOW COPIES
     * @param image image to be tinted
     * @param tintColor what color it should be tinted
     * @param magnitude how much it should be tinted, 0-1
     * @return the new tinted image
     */
    public static PImage superTint(PImage image, Color tintColor, float magnitude) {
        image.loadPixels();
        for (int i = 0; i < image.pixels.length; i++) {
            if (image.pixels[i] != 0) {
                Color pixel = new Color(image.pixels[i]);
                float m = abs(magnitude - 1);
                pixel = new Color (
                        calcColor(pixel.getRed(), m, tintColor.getRed()),
                        calcColor(pixel.getGreen(), m, tintColor.getGreen()),
                        calcColor(pixel.getBlue(), m, tintColor.getBlue()),
                        calcColor(pixel.getAlpha(), m, tintColor.getAlpha())
                );
                image.pixels[i] = pixel.getRGB();
            }
        }
        return image;
    }

    /**
     * Calculates the value of one color channel for <tt>superTint()</tt>
     * @param p input value
     * @param m magnitude of the change
     * @param t target value
     * @return the value of a single channel
     */
    private static int calcColor(float p, float m, float t) {
        float d = t - p;
        float d2 = m * d;
        float c = p + d2;
        return (int) c;
    }

    /**
     * Finds the distance between two points.
     * @param p1 the first point
     * @param p2 the second point
     * @return the distance between the two points
     */
    public static float findDistBetween(PVector p1, PVector p2) {
        PVector p0 = new PVector(p1.x - p2.x, p1.y - p2.y);
        return sqrt(sq(p0.x) + sq(p0.y));
    }

    /**
     * Displays text with a rectangle backing.
     * @param p the PApplet
     * @param text text to be displayed
     * @param position position for text to be displayed
     * @param textColor color of text, RGBA
     * @param highlightColor color of highlight, RGBA
     * @param textSize size of text
     * @param textAlign what alignment to use, defaults to center
     */
    public static void highlightedText(PApplet p, String text, PVector position, Color textColor, Color highlightColor,
                                       float textSize, int textAlign) {
        if (textAlign != LEFT && textAlign != RIGHT) textAlign = CENTER;

        p.rectMode(CENTER);
        p.noStroke();
        p.textAlign(textAlign);
        p.textSize(textSize);

        float textWidth = p.textWidth(text);
        float padding = textSize/6;
        PVector highlightPosition = new PVector(position.x, position.y);
        if (textAlign == LEFT) highlightPosition = new PVector(position.x + (textWidth/2), position.y);
        if (textAlign == RIGHT) highlightPosition = new PVector(position.x - (textWidth/2), position.y);

        p.fill(highlightColor.getRGB(), highlightColor.getAlpha());
        p.rect(highlightPosition.x, highlightPosition.y - ((textSize/2) - padding), textWidth + padding, textSize);
        p.fill(textColor.getRGB(), textColor.getAlpha());
        p.text(text, position.x, position.y);

        p.rectMode(CORNER);
    }

    /**
     * Displays text with a rectangle backing.
     * @param p the PApplet
     * @param text text to be displayed
     * @param position position for text to be displayed
     * @param textAlign what alignment to use, defaults to center
     */
    public static void highlightedText(PApplet p, String text, PVector position, int textAlign) {
        highlightedText(p, text, position, new Color(255, 255, 255), new Color(0, 0, 0, 175), 25, textAlign);
    }

    /**
     * Displays text with a strikethrough.
     * @param p the PApplet
     * @param text text to display
     * @param position where text is displayed
     * @param textColor color of text and strikethrough, RGB
     * @param textSize size of text
     * @param textAlign what alignment to use, defaults to center
     */
    public static void strikethroughText(PApplet p, String text, PVector position, Color textColor, float textSize,
                                         int textAlign) {
        if (textAlign != LEFT && textAlign != RIGHT) textAlign = CENTER;

        p.textAlign(textAlign);
        p.textSize(textSize);
        p.strokeWeight(textSize/10);

        float textWidth = p.textWidth(text);
        PVector center = new PVector(position.x, position.y);
        if (textAlign == LEFT) center = new PVector(position.x + (textWidth/2), position.y);
        if (textAlign == RIGHT) center = new PVector(position.x - (textWidth/2), position.y);
        PVector leftPoint = new PVector(center.x - (textWidth/2), center.y - textSize/2);
        PVector rightPoint = new PVector(center.x + (textWidth/2), center.y - textSize/2);

        p.fill(textColor.getRGB());
        p.stroke(textColor.getRGB());
        p.text(text, position.x, position.y);
        p.line(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y);

        p.strokeWeight(1);
        p.noStroke();
    }

    /**
     * Displays text with a slight 3d effect
     * @param p the PApplet
     * @param text text to be displayed
     * @param position where text will be displayed
     * @param lightColor main text color, RGBA
     * @param darkColor shadow color, RGBA
     * @param textSize size of text
     * @param textAlign alignment of text
     */
    public static void shadowedText(PApplet p, String text, PVector position, Color lightColor, Color darkColor,
                                    float textSize, int textAlign) {
        p.textAlign(textAlign);
        p.textSize(textSize);
        int offset = 2;

        p.fill(darkColor.getRGB(), darkColor.getAlpha());
        p.text(text, position.x + offset, position.y + offset);
        p.fill(lightColor.getRGB(), lightColor.getAlpha());
        p.text(text, position.x, position.y);
    }

    /**
     * @return seconds * framerate
     */
    public static int secondsToFrames(float numberOfSeconds) {
        return (int) (numberOfSeconds * FRAMERATE);
    }

    /**
     * @return (frames / 60) * framerate
     */
    public static int down60ToFramerate(float frames) {
        return (int) ((frames / 60f) * FRAMERATE);
    }

    /**
     * @return frames * (60 / framerate)
     */
    public static int up60ToFramerate(float frames) {
        return (int) (frames * (60 / (float) FRAMERATE));
    }

    /**
     * Adds by to f, approaching target without overshooting it.
     * @param input starting value
     * @param by amount to increment, must be positive
     * @param target value to approach
     * @return a value closer to but not overshooting the target
     */
    public static float incrementByTo(float input, float by, float target) {
        if (input == target) return target;
        if (input < target) {
            return Math.min(input + by, target);
        } if (input > target) {
            return Math.max(input - by, target);
        } return target;
    }

    /**
     * Increments one color to another.
     * @param input starting color
     * @param target target color
     * @param by amount to increment by, must be positive
     * @return input a little closer to target
     */
    public static Color incrementColorTo(Color input, int by, Color target) {
        int red = input.getRed();
        int green = input.getGreen();
        int blue = input.getBlue();
        red = (int) incrementByTo(red, by, target.getRed());
        green = (int) incrementByTo(green, by, target.getGreen());
        blue = (int) incrementByTo(blue, by, target.getBlue());
        return new Color(red, green, blue);
    }

    /**
     * randomizes the input by a proportion.
     * @param p the PApplet
     * @param by proportion to randomize by
     */
    public static float randomizeBy(PApplet p, float input, float by) {
        return input + p.random(-input * by, input * by);
    }

    /**
     * Stops and starts playing the given sound.
     * @param sound sound to play, won't crash if null
     * @param speed speed and to pitch to play at, 1 for normal
     * @param volume volume to play at, 1 for normal
     */
    public static void playSound(SoundFile sound, float speed, float volume) {
        if (sound != null) {
            sound.stop();
            sound.play(speed, volume);
        }
    }

    /**
     * Plays the given sound with a 20% pitch and speed variation.
     * @param p the PApplet
     * @param sound sound to play, won't crash if null
     * @param volume volume to play at, 1 for normal
     */
    public static void playSoundRandomSpeed(PApplet p, SoundFile sound, float volume) {
        playSound(sound, p.random(0.8f, 1.2f), volume);
    }
}
