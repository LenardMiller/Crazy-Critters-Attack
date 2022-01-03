package main.gui;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.strikethroughText;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

public class TowerInfo {

    public TowerInfo() {}

    private static int loadStyle(PApplet p) {
        p.textAlign(LEFT);
        p.textFont(mediumFont);
        p.fill(0, 254);
        return 910;
    }

    public static void displayTurretInfo(PApplet p, String turretType) {
        p.fill(235);
        p.noStroke();
        p.rect(900,212,200,707);
        p.textAlign(CENTER);
        p.fill(0, 254);
        p.textFont(largeFont); //displays info about tower
        int x = 1000;
        int offset = 0;
        int price = 0;
        switch (turretType) {
            case "slingshot":
                p.text("Slingshot", 1000, 241);
                slingshotInfo(p);
                price = SLINGSHOT_PRICE;
                break;
            case "miscCannon":
                p.text("Luggage", x, 241);
                p.text("Launcher", x, 266);
                offset = 25;
                randomCannonInfo(p);
                price = RANDOM_CANNON_PRICE;
                break;
            case "crossbow":
                p.text("Crossbow", x, 241);
                crossbowInfo(p);
                price = CROSSBOW_PRICE;
                break;
            case "cannon":
                p.text("Cannon", x, 241);
                cannonInfo(p);
                price = CANNON_PRICE;
                break;
            case "gluer":
                p.text("Gluer", x, 241);
                gluerInfo(p);
                price = GLUER_PRICE;
                break;
            case "seismic":
                p.text("Seismic Tower", x, 241);
                seismicInfo(p);
                price = SEISMIC_PRICE;
                break;
            case "energyBlaster":
                p.text("Energy Blaster", x, 241);
                energyBlasterInfo(p);
                price = ENERGY_BLASTER_PRICE;
                break;
            case "magicMissleer":
                p.text("Magic Missile", x, 241);
                p.text("Launcher", x, 266);
                offset = 25;
                magicMissileerInfo(p);
                price = MAGIC_MISSILEER_PRICE;
                break;
            case "tesla":
                p.text("Tesla Tower", x, 241);
                teslaTowerInfo(p);
                price = TESLA_TOWER_PRICE;
                break;
            case "nightmare":
                p.text("Nightmare", x, 241);
                p.text("Blaster", x, 266);
                nightmareInfo(p);
                offset = 25;
                price = NIGHTMARE_PRICE;
                break;
            case "flamethrower":
                p.text("Flamethrower", x, 241);
                flamethrowerInfo(p);
                price = FLAMETHROWER_PRICE;
                break;
            case "iceTower":
                p.text("Freeze Ray", x, 241);
                iceTowerInfo(p);
                price = ICE_TOWER_PRICE;
                break;
            case "booster":
                p.text("Booster", x, 241);
                boosterInfo(p);
                price = BOOSTER_PRICE;
                break;
            case "railgun":
                p.text("Railgun", x, 241);
                railgunInfo(p);
                price = RAILGUN_PRICE;
                break;
            case "waveMotion":
                p.text("Death Beam", x, 241);
                waveMotionInfo(p);
                price = WAVE_MOTION_PRICE;
                break;
        }
        displayPrice(p, price, offset, x);
    }

    private static void displayPrice(PApplet p, int price, int offset, int x) {
        p.textAlign(CENTER);
        p.textFont(mediumFont);
        if (money < price) {
            strikethroughText(p, "$" + price, new PVector(x, 271 + offset), new Color(150, 0, 0, 254),
                    mediumFont.getSize(), CENTER);
        }
        else p.text("$" + price, x, 271 + offset);
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
        p.text("but does no", x, space(5));
        p.text("damage.  More", x, space(6));
        p.text("strongly affects", x, space(7));
        p.text("flying enemies.", x, space(8));
        p.textAlign(CENTER);
        p.text("[S]", 1000, space(10));
    }

    public static void seismicInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Sends a shockwave", x, space(0));
        p.text("towards the nearest", x, space(1));
        p.text("enemy.  Does low", x, space(2));
        p.text("damage,  but can", x, space(3));
        p.text("hit several enemies.", x, space(4));
        p.text("Stuns burrowing", x, space(5));
        p.text("enemies,  but can't", x, space(6));
        p.text("hit flying enemies.", x, space(7));
        p.textAlign(CENTER);
        p.text("[X]", 1000, space(9));
    }

    public static void energyBlasterInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires an explosive", x, space(0));
        p.text("energy ball at the", x, space(1));
        p.text("toughest enemy in its", x, space(2));
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

    public static void teslaTowerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Creates an electric", x, space(0));
        p.text("arc that can jump", x, space(1));
        p.text("from enemy to", x, space(2));
        p.text("enemy.  Short range,", x, space(3));
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
        p.text("enemy in a block", x, space(1));
        p.text("of ice.  Smaller", x, space(2));
        p.text("enemies are", x, space(3));
        p.text("completely", x, space(4));
        p.text("immobilized.  Ice", x, space(5));
        p.text("melts over time.", x, space(6));
        p.text("Effective against", x, space(7));
        p.text("flying enemies.", x, space(8));
        p.textAlign(CENTER);
        p.text("[F]", 1000, space(10));
    }

    public static void magicMissileerInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Fires three magic", x, space(0));
        p.text("missiles that home", x, space(1));
        p.text("in on different", x, space(2));
        p.text("enemies.  Short", x, space(3));
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
        p.text("the toughest enemy", x, space(2));
        p.text("onscreen,  doing", x, space(3));
        p.text("extreme damage.", x, space(4));
        p.textAlign(CENTER);
        p.text("[T]", 1000, space(6));
    }

    public static void nightmareInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Unleashes a flurry of", x, space(0));
        p.text("cursed needles that", x, space(1));
        p.text("cause enemies to", x, space(2));
        p.text("turn to ash.", x, space(3));
        p.textAlign(CENTER);
        p.text("[G]", 1000, space(5));
    }

    public static void waveMotionInfo(PApplet p) {
        int x = loadStyle(p);
        p.text("Creates an", x, space(0));
        p.text("enormous beam of", x, space(1));
        p.text("energy, cutting a", x, space(2));
        p.text("path through enemy", x, space(3));
        p.text("ranks.", x, space(4));
        p.textAlign(CENTER);
        p.text("[B]", 1000, space(6));
    }
}
