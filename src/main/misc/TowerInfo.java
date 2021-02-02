package main.misc;

import processing.core.PApplet;

import static main.Main.mediumFont;
import static processing.core.PConstants.LEFT;

public class TowerInfo {

    public TowerInfo() {}

    private static void loadStyle(PApplet p) {
        p.textAlign(LEFT);
        p.textFont(mediumFont);
    }

    private static int space(int lineNumber) {
        return 330 + lineNumber * (23);
    }

    public static void slingshotInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Fires a single pebble", x, space(0));
        p.text("at the nearest", x, space(1));
        p.text("enemy.  Decent", x, space(2));
        p.text("mid-range tower,", x, space(3));
        p.text("and the most", x, space(4));
        p.text("cost-efficient.", x, space(5));
    }

    public static void randomCannonInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Rapidly fires old", x, space(0));
        p.text("luggage at the", x, space(1));
        p.text("nearest enemy.", x, space(2));
        p.text("Potentially very high", x, space(3));
        p.text("DPS,  but has short", x, space(4));
        p.text("range.", x, space(5));
    }

    public static void crossbowInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Fires a powerful bolt", x, space(0));
        p.text("at the furthest enemy", x, space(1));
        p.text("at a long distance.", x, space(2));
        p.text("Very high damage", x, space(3));
        p.text("and range,  but low", x, space(4));
        p.text("rate of fire.", x, space(5));
    }

    public static void cannonInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Fires a cannonball", x, space(0));
        p.text("at the nearest", x, space(1));
        p.text("enemy.  Moderate", x, space(2));
        p.text("range and firerate,", x, space(3));
        p.text("good damage with", x, space(4));
        p.text("a bit of splash.", x, space(5));
    }

    public static void gluerInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Fires a glob of", x, space(0));
        p.text("glue at the nearest", x, space(1));
        p.text("unglued enemy.", x, space(2));
        p.text("Slows movement", x, space(3));
        p.text("and attack speed,", x, space(4));
        p.text("does no damage.", x, space(5));
    }

    public static void seismicInfo(PApplet p) {
        loadStyle(p);
        int x = 910;
        p.text("Sends a shockwave", x, space(0));
        p.text("towards the nearest", x, space(1));
        p.text("enemy.  Does low", x, space(2));
        p.text("damage,  but can", x, space(3));
        p.text("hit large numbers", x, space(4));
        p.text("of enemies.", x, space(5));
    }
}
