package main.gui.inGame;

import processing.core.PApplet;

import static main.Main.*;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

public class TowerInfo {

    public TowerInfo() {}

    private static int loadStyle(PApplet p) {
        p.textAlign(LEFT);
        p.textFont(h4);
        p.fill(0, 254);
        return 910;
    }

    public static void displayTurretInfo(PApplet p, Class<?> turretClass) {
        p.image(staticSprites.get("towerBuyPn"), 900, 212);

        String pid;
        String description;
        char shortcut = '`';
        String title1;
        String title2;
        int price;
        try {
            pid = (String) turretClass.getField("pid").get(null);
            description = (String) turretClass.getField("description").get(null);
            shortcut = (char) turretClass.getField("shortcut").get(null);
            title1 = (String) turretClass.getField("title1").get(null);
            title2 = (String) turretClass.getField("title2").get(null);
            price = (int) turretClass.getField("price").get(null);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            System.out.println("Something bad happened in TurretInfo: " + ex);
            return;
        }
        if (pid == null || description == null || shortcut == '`' || title1 == null || price == 0) {
            return;
        }

        //pid
        p.textFont(monoSmall);
        p.textAlign(LEFT);
        p.fill(0);
        p.text(pid, 910, 235);

        //CCA
        p.textAlign(RIGHT);
        p.text("CCA", 1080, 235);

        //title
        p.stroke(0);
        p.strokeWeight(2);
        p.line(910, 245, 1080, 245);
        p.textAlign(CENTER);
        p.textFont(h2);
        p.text(title1, 1000, 268);
        if (title2 != null) {
            p.text(title2, 1000, 291);
            p.line(910, 298, 1080, 298);
        } else {
            p.line(910, 275, 1080, 275);
        }

        //price
        p.textAlign(LEFT);
        p.textFont(h3);
        p.text("Price", 910, 320);
        p.textAlign(RIGHT);
        p.textFont(monoMedium);
        p.text("$" + nfc(price), 1080, 320);
        p.line(910, 325, 1080, 325);

        //shortcut
        p.textAlign(LEFT);
        p.textFont(h3);
        p.text("Shortcut", 910, 345);
        p.textAlign(RIGHT);
        p.textFont(monoMedium);
        p.text(shortcut, 1080, 345);
        p.line(910, 350, 1080, 350);

        //description
        p.textAlign(LEFT);
        p.textFont(h3);
        p.text("Description", 910, 370);
        p.line(910, 375, 1080, 375);
        p.textFont(pg);
        p.text(description, 910, 380, 170, 500);

        p.strokeWeight(1);
    }

    private static int space(int lineNumber) {
        return 330 + lineNumber * (23);
    }

    public static void slingshotInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a single pebble", x, space(0));
        p.text("at the nearest", x, space(1));
        p.text("critter.  Decent", x, space(2));
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
        p.text("nearest critter.", x, space(2));
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
        p.text("critter in its range.", x, space(2));
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
        p.text("critter.  Moderate", x, space(2));
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
        p.text("unglued critter.", x, space(2));
        p.text("Slows movement", x, space(3));
        p.text("and attack speed,", x, space(4));
        p.text("but does no", x, space(5));
        p.text("damage.  More", x, space(6));
        p.text("strongly affects", x, space(7));
        p.text("flying critters.", x, space(8));
        p.textAlign(CENTER);
        p.text("[S]", 1000, space(10));
    }

    public static void seismicInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Sends a shockwave", x, space(0));
        p.text("towards the nearest", x, space(1));
        p.text("critter.  Does low", x, space(2));
        p.text("damage,  but can", x, space(3));
        p.text("hit several critters.", x, space(4));
        p.text("Stuns burrowing", x, space(5));
        p.text("critters,  but can't", x, space(6));
        p.text("hit flying critters.", x, space(7));
        p.textAlign(CENTER);
        p.text("[X]", 1000, space(9));
    }

    public static void energyBlasterInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires an explosive", x, space(0));
        p.text("energy ball at the", x, space(1));
        p.text("toughest critter in its", x, space(2));
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
        p.text("the nearest critter.", x, space(2));
        p.text("Has very short range", x, space(3));
        p.text("but very high DPS,", x, space(4));
        p.text("and some damage", x, space(5));
        p.text("over time.", x, space(6));
        p.textAlign(CENTER);
        p.text("[D]", 1000, space(8));
    }

    public static void teslaTowerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Creates an electric", x, space(0));
        p.text("arc that can jump", x, space(1));
        p.text("from critter to", x, space(2));
        p.text("critter.  Short range,", x, space(3));
        p.text("but arcs can jump", x, space(4));
        p.text("quite far.", x, space(5));
        p.textAlign(CENTER);
        p.text("[C]", 1000, space(8));
    }

    public static void boosterInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Boosts the stats of", x, space(0));
        p.text("nearby towers.", x, space(1));
        p.text("Effect does not", x, space(2));
        p.text("stack.", x, space(3));
        p.textAlign(CENTER);
        p.text("[R]", 1000, space(5));
    }

    public static void iceTowerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Freezes the nearest", x, space(0));
        p.text("critter in a block", x, space(1));
        p.text("of ice.  Smaller", x, space(2));
        p.text("critters are", x, space(3));
        p.text("completely", x, space(4));
        p.text("immobilized.  Ice", x, space(5));
        p.text("melts over time.", x, space(6));
        p.text("Effective against", x, space(7));
        p.text("flying critters.", x, space(8));
        p.textAlign(CENTER);
        p.text("[F]", 1000, space(10));
    }

    public static void magicMissileerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires three magic", x, space(0));
        p.text("missiles that home", x, space(1));
        p.text("in on different", x, space(2));
        p.text("critters.  Short", x, space(3));
        p.text("range and low", x, space(4));
        p.text("firerate but missiles", x, space(5));
        p.text("can travel very far.", x, space(6));
        p.textAlign(CENTER);
        p.text("[V]", 1000, space(8));
    }

    public static void railgunInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires a hypersonic", x, space(0));
        p.text("bolt of plasma at", x, space(1));
        p.text("the toughest critter", x, space(2));
        p.text("onscreen,  doing", x, space(3));
        p.text("extreme damage.", x, space(4));
        p.textAlign(CENTER);
        p.text("[T]", 1000, space(6));
    }

    public static void nightmareInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Unleashes a flurry of", x, space(0));
        p.text("cursed needles that", x, space(1));
        p.text("cause critters to", x, space(2));
        p.text("turn to ash.", x, space(3));
        p.textAlign(CENTER);
        p.text("[G]", 1000, space(5));
    }

    public static void waveMotionInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Creates an", x, space(0));
        p.text("enormous beam of", x, space(1));
        p.text("energy, cutting a", x, space(2));
        p.text("path through critter", x, space(3));
        p.text("ranks.", x, space(4));
        p.textAlign(CENTER);
        p.text("[B]", 1000, space(6));
    }
}
