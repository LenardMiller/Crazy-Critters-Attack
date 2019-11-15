package main.gui;

import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Hand { //what is selected, eg: slingshot

    private PApplet p;

    public String held;
    private PImage heldSprite;
    public PVector size;
    private PVector offset;
    private boolean implaceable;
    public int price;

    public Hand(PApplet p) {
        this.p = p;

        held = "null";
        size = new PVector(25,25);
        offset = new PVector(0,0);
        implaceable = false;
    }

    public void main() {
        checkPlaceable();
        displayHeld();
        if (p.mousePressed && !implaceable){
            place();
        }
    }

    private void checkPlaceable() {
        //checks if on screen
        implaceable = !((10 * (ceil(p.mouseX / 10))) <= BOARD_WIDTH - (size.x) && (10 * (ceil(p.mouseX / 10))) >= (size.x) && (10 * (ceil(p.mouseY / 10))) <= BOARD_HEIGHT - (size.y) && (10 * (ceil(p.mouseY / 10))) >= (size.y));
        for (int i = towers.size()-1; i >= 0; i--){ //checks if too near tower
            Tower tower = towers.get(i);
            float dx = (tower.position.x - tower.size.x/2) - (10*(ceil(p.mouseX/10)));
            float dy = (tower.position.y - tower.size.y/2) - (10*(ceil(p.mouseY/10)));
            if (dy < size.y + tower.size.y/2 && dy > -(tower.size.y/2) - size.y && dx < size.x + tower.size.x/2 && dx > -(tower.size.x/2) - size.x){ //if touching tower
                implaceable = true;
            }
        }
        if (price > money){ //check if enough money
            implaceable = true;
        }
    }

    private void displayHeld() { //shows whats held at ~1/2 opacity
        if (held != "null" && alive){
            if (implaceable){ //red if implacable
                p.tint(255, 0, 0, 150);
            }
            else{
                p.tint(255,150);
            }
            p.image(heldSprite,(10*(ceil(p.mouseX/10)))-(size.x)-offset.x,(10*(ceil(p.mouseY/10)))-(size.y)-offset.y);
            p.tint(255);
        }
    }

    public void setHeld(String setHeld) { //swaps whats held
        switch (setHeld) {
            case "slingshot":
                heldSprite = spritesH.get("slingshotFullTR");
                size = new PVector(25, 25);
                offset = new PVector(0, 0);
                held = setHeld;
                price = 50;
                break;
            case "crossbow":
                heldSprite = spritesH.get("crossbowFullTR");
                size = new PVector(25, 25);
                offset = new PVector(2, 2);
                held = setHeld;
                price = 100;
                break;
            case "miscCannon":
                heldSprite = spritesH.get("miscCannonFullTR");
                size = new PVector(25, 25);
                offset = new PVector(0, 0);
                held = setHeld;
                price = 100;
                break;
            case "energyBlaster":
                heldSprite = spritesH.get("energyBlasterFullTR");
                size = new PVector(25, 25);
                offset = new PVector(11, 11);
                held = setHeld;
                price = 150;
                break;
            case "magicMissleer":
                heldSprite = spritesH.get("magicMissleerFullTR");
                size = new PVector(25, 25);
                offset = new PVector(0, 0);
                held = setHeld;
                price = 150;
                break;
            case "wall":
                heldSprite = spritesH.get("woodWallTW");
                size = new PVector(60, 18.5f);
                offset = new PVector(0, 0);
                held = setHeld;
                price = 25;
                break;
        }
    }

    private void place() { //puts down tower and subtracts price
        if (held.equals("slingshot") && alive){
            money -= 50;
            towers.add(new Slingshot(p,(10*(ceil(p.mouseX/10)))+(25),(10*(ceil(p.mouseY/10)))+(25)));
            path.nodeCheckObs();
        }
        else if (held.equals("crossbow") && alive){
            money -= 100;
            towers.add(new Crossbow(p,(10*(ceil(p.mouseX/10)))+(25),(10*(ceil(p.mouseY/10)))+(25)));
            path.nodeCheckObs();
        }
        else if (held.equals("miscCannon") && alive){
            money -= 100;
            towers.add(new RandomCannon(p,(10*(ceil(p.mouseX/10)))+(25),(10*(ceil(p.mouseY/10)))+(25)));
            path.nodeCheckObs();
        }
        else if (held.equals("energyBlaster") && alive){
            money -= 150;
            towers.add(new EnergyBlaster(p,(10*(ceil(p.mouseX/10)))+(25),(10*(ceil(p.mouseY/10)))+(25)));
            path.nodeCheckObs();
        }
        else if (held.equals("magicMissleer") && alive){
            money -= 150;
            towers.add(new MagicMissileer(p,(10*(ceil(p.mouseX/10)))+(25),(10*(ceil(p.mouseY/10)))+(25)));
            path.nodeCheckObs();
        }
        else if (held.equals("wall") && alive){
            money -= 25;
            towers.add(new Wall(p,(float)((10*(ceil(p.mouseX/10)))+(60)),(float)((10*(ceil(p.mouseY/10)))+(18.5))));
            path.nodeCheckObs();
        }
        held = "null";
    }
}