package main.gui;

import processing.core.PApplet;

import static main.Main.mediumFont;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

public class TowerInfo {

    public TowerInfo() {}

    private static int loadStyle(PApplet p) {
        p.textAlign(LEFT);
        p.textFont(mediumFont);
        return 910;
    }

    private static int space(int lineNumber) {
        return 330 + lineNumber * (23);
    }

    public static void slingshotInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a single pebble", x, space(0));
        p.text("at the nearest", x, space(1));
        p.text("enemy.  Decent", x, space(2));
        p.text("mid-range tower,", x, space(3));
        p.text("and very", x, space(4));
        p.text("cost-efficient.", x, space(5));
        p.textAlign(CENTER);
        p.text("[Q]", 1000, space(7));
    }

    public static void randomCannonInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Rapidly fires old", x, space(0));
        p.text("luggage at the", x, space(1));
        p.text("nearest enemy.", x, space(2));
        p.text("Potentially very high", x, space(3));
        p.text("DPS,  but has short", x, space(4));
        p.text("range.", x, space(5));
        p.textAlign(CENTER);
        p.text("[A]", 1000, space(7));
    }

    public static void crossbowInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a powerful bolt", x, space(0));
        p.text("at the farthest", x, space(1));
        p.text("enemy in its range.", x, space(2));
        p.text("Very high damage", x, space(3));
        p.text("and range with some", x, space(4));
        p.text("piercing,  but low", x, space(5));
        p.text("rate of fire.", x, space(6));
        p.textAlign(CENTER);
        p.text("[Z]", 1000, space(8));
    }

    public static void cannonInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a cannonball", x, space(0));
        p.text("at the nearest", x, space(1));
        p.text("enemy.  Moderate", x, space(2));
        p.text("range and firerate,", x, space(3));
        p.text("good damage with", x, space(4));
        p.text("a bit of splash.", x, space(5));
        p.textAlign(CENTER);
        p.text("[W]", 1000, space(7));
    }

    public static void gluerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a glob of", x, space(0));
        p.text("glue at the nearest", x, space(1));
        p.text("unglued enemy.", x, space(2));
        p.text("Slows movement", x, space(3));
        p.text("and attack speed,", x, space(4));
        p.text("does no damage.", x, space(5));
        p.textAlign(CENTER);
        p.text("[S]", 1000, space(7));
    }

    public static void seismicInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Sends a shockwave", x, space(0));
        p.text("towards the nearest", x, space(1));
        p.text("enemy.  Does low", x, space(2));
        p.text("damage,  but can", x, space(3));
        p.text("hit several enemies.", x, space(4));
        p.textAlign(CENTER);
        p.text("[X]", 1000, space(6));
    }

    public static void energyBlasterInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires an explosive", x, space(0));
        p.text("energy ball at the", x, space(1));
        p.text("farthest enemy in its", x, space(2));
        p.text("range.  Has long", x, space(3));
        p.text("range,  some splash", x, space(4));
        p.text("and high damage,", x, space(5));
        p.text("but a long reload", x, space(6));
        p.text("time.", x, space(7));
        p.textAlign(CENTER);
        p.text("[E]", 1000, space(9));
    }

    public static void flamethrowerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Sprays an unending", x, space(0));
        p.text("column of fire at", x, space(1));
        p.text("the nearest enemy.", x, space(2));
        p.text("Has very short range", x, space(3));
        p.text("but very high DPS,", x, space(4));
        p.text("and some damage", x, space(5));
        p.text("over time.", x, space(6));
        p.textAlign(CENTER);
        p.text("[D]", 1000, space(8));
    }
}
