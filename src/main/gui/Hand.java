package main.gui;

import main.towers.Tile;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.util.MiscMethods.roundTo;

public class Hand { //what is selected, eg: slingshot

    private PApplet p;

    public String held;
    private PImage heldSprite;
    private PVector offset;
    private boolean implaceable;
    public int price;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implaceable = false;
    }

    public void main() {
        checkPlaceable();
        displayHeld();
        if (p.mousePressed && p.mouseButton == p.LEFT && !implaceable) {
            place();
        }
        if (p.mousePressed && p.mouseButton == p.RIGHT) {
            setHeld("null");
        }
    }

    private void checkPlaceable() {
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (tile == null) {
            implaceable = true;
        } else {
            implaceable = (tile.tower != null);
        }
    }

    private void displayHeld() { //shows whats held at ~1/2 opacity
        if (!held.equals("null") && alive) {
            if (implaceable) { //red if implacable
                p.tint(255, 0, 0, 150);
            } else {
                p.tint(255, 150);
            }
            p.image(heldSprite, (roundTo(p.mouseX, 50)) - (25f / 2) - offset.x + 13, roundTo(p.mouseY, 50) - (25f / 2) - offset.y + 13);
            p.tint(255);
        }
    }

    public void setHeld(String setHeld) { //swaps whats held
        switch (setHeld) {
            case "slingshot":
                heldSprite = spritesH.get("slingshotFullTR");
                offset = new PVector(0, 0);
                price = 50;
                break;
            case "crossbow":
                heldSprite = spritesH.get("crossbowFullTR");
                offset = new PVector(2, 2);
                price = 100;
                break;
            case "miscCannon":
                heldSprite = spritesH.get("miscCannonFullTR");
                offset = new PVector(0, 0);
                price = 100;
                break;
            case "energyBlaster":
                heldSprite = spritesH.get("energyBlasterFullTR");
                offset = new PVector(11, 11);
                price = 150;
                break;
            case "magicMissleer":
                heldSprite = spritesH.get("magicMissleerFullTR");
                offset = new PVector(0, 0);
                price = 150;
                break;
            case "wall":
                heldSprite = spritesH.get("woodWallTW");
                offset = new PVector(0, 0);
                price = 25;
                break;
        }
        held = setHeld;
    }

    private void place() { //puts down tower and subtracts price
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (held.equals("slingshot") && alive) {
            money -= 50;
            tile.tower = new Slingshot(p, tile);
            held = "null";
//            path.nodeCheckObs();
        } else if (held.equals("crossbow") && alive) {
            money -= 100;
            tile.tower = new Crossbow(p, tile);
            held = "null";
//            path.nodeCheckObs();
        } else if (held.equals("miscCannon") && alive) {
            money -= 100;
            tile.tower = new RandomCannon(p, tile);
            held = "null";
//            path.nodeCheckObs();
        } else if (held.equals("energyBlaster") && alive) {
            money -= 150;
            tile.tower = new EnergyBlaster(p, tile);
            held = "null";
//            path.nodeCheckObs();
        } else if (held.equals("magicMissleer") && alive) {
            money -= 150;
            tile.tower = new MagicMissileer(p, tile);
            held = "null";
//            path.nodeCheckObs();
        } else if (held.equals("wall") && alive) {
            money -= 25;
            tile.tower = new Wall(p, tile);
//            path.nodeCheckObs();
        }
    }
}