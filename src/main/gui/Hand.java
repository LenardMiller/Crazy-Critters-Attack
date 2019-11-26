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
    private boolean implacable;
    public int price;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        implacable = false;
    }

    public void main() {
        checkPlaceable();
        displayHeld();
        if (inputHandler.leftMousePressedPulse && !implacable) {
            place();
        }
        if (inputHandler.rightMousePressedPulse) {
            remove();
        }
        //todo: unselect by clicking off screen
        //todo: break by right clicking
        //todo: highlight upgrades
    }

    private void checkPlaceable() {
        implacable = false;
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (tile == null) implacable = true;
        else if (!held.equals("wall")) implacable = (tile.tower != null);
        else implacable = (tile.tower != null && tile.tower.turret);
        if (!implacable) implacable = (price > money);
    }

    private void displayHeld() { //shows whats held at ~1/2 opacity
        if (!held.equals("null") && alive) {
            //red if implacable
            if (implacable) p.tint(255, 0, 0, 150);
            else p.tint(255, 150);
            p.image(heldSprite, (roundTo(p.mouseX, 50)) - (25f / 2) - offset.x + 13, roundTo(p.mouseY, 50) - (25f / 2) - offset.y + 13);
            p.tint(255);
        }
    }

    public void setHeld(String heldSet) { //swaps whats held
        if (!heldSet.equals("null")) {
            switch (heldSet) {
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
            held = heldSet;
        }
    }

    private void remove() {
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        if (held.equals("wall")) {
            if (tile.tower != null && !tile.tower.turret) tile.tower.sell();
        }
    }

    private void place() { //puts down tower and subtracts price
        Tile tile = tiles.get((roundTo(p.mouseX, 50) / 50) + 1, (roundTo(p.mouseY, 50) / 50) + 1);
        boolean changeHeld = true;
        if (held.equals("slingshot") && alive) tile.tower = new Slingshot(p, tile);
        else if (held.equals("crossbow") && alive) tile.tower = new Crossbow(p, tile);
        else if (held.equals("miscCannon") && alive) tile.tower = new RandomCannon(p, tile);
        else if (held.equals("energyBlaster") && alive) tile.tower = new EnergyBlaster(p, tile);
        else if (held.equals("magicMissleer") && alive) tile.tower = new MagicMissileer(p, tile);
        else if (held.equals("wall") && alive) {
            if (tile.tower != null && !tile.tower.turret) {
                if (tile.tower.hp < tile.tower.maxHp) tile.tower.repair();
                else if (tile.tower.nextLevelOne < tile.tower.upgradeIcons.length && money >= tile.tower.price) {
                    money -= tile.tower.price;
                    tile.tower.upgrade(0);
                }
                money += price; //cancel out price change later
            }
            else tile.tower = new Wall(p, tile);
            changeHeld = false;
        }
        money -= price;
        if (changeHeld) held = "null";
//        path.nodeCheckObs();
    }
}