package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class TowerBuy extends Button {

    private String towerType;
    public boolean depressed;
    public int price;

    public TowerBuy(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        price = 0;
        position = new PVector(x, y);
        size = new PVector(35, 35);
        towerType = type;
        spriteLocation = "sprites/guiObjects/buttons/towerBuy/" + towerType + "/"; //still uses old system because it is only created at beginning of game
        spriteOne = p.loadImage(spriteLocation + "000.png");
        spriteTwo = p.loadImage(spriteLocation + "001.png");
        sprite = spriteOne;
        depressed = false;
        switch (type) {
            case "slingshot":
                price = 50;
                break;
            case "crossbow":
            case "miscCannon":
                price = 100;
                break;
            case "tesla":
            case "energyBlaster":
            case "magicMissleer":
                price = 150;
                break;
            case "nightmare":
            case "flamethrower":
            case "railgun":
                price = 200;
                break;
            case "waveMotion":
                price = 250;
                break;
        }
    }

    public void main() {
        if (active){
            hover();
            display();
        }
    }

    public void hover() { //below is if hovered or depressed
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 && p.mouseY > position.y-size.y/2 && alive && active || depressed && alive){
            sprite = spriteTwo;
            p.fill(235);
            p.noStroke();
            p.rect(900,212,200,707);
            p.textAlign(CENTER);
            p.fill(0);
            p.textFont(largeFont); //displays info about tower todo: put more info
            int x = 1000;
            switch (towerType) {
                case "slingshot":
                    p.text("Slingshot", 1000, 241);
                    p.textFont(mediumFont);
                    p.text("$50", x, 271);
                    break;
                case "crossbow":
                    p.text("Crossbow", x, 241);
                    p.textFont(mediumFont);
                    p.text("$100", x, 271);
                    break;
                case "miscCannon":
                    p.text("Luggage", x, 241);
                    p.text("Blaster", x, 266);
                    p.textFont(mediumFont);
                    p.text("$100", x, 296);
                    break;
                case "energyBlaster":
                    p.text("Energy Blaster", x, 241);
                    p.textFont(mediumFont);
                    p.text("$150", x, 271);
                    break;
                case "magicMissleer":
                    p.text("Magic Missileer", x, 241);
                    p.textFont(mediumFont);
                    p.text("$150", x, 271);
                    break;
                case "tesla":
                    p.text("Tesla Tower", x, 241);
                    p.textFont(mediumFont);
                    p.text("$150", x, 271);
                    break;
                case "nightmare":
                    p.text("Nightmare", x, 241);
                    p.text("Blaster", x, 266);
                    p.textFont(mediumFont);
                    p.text("$200", x, 296);
                    break;
                case "flamethrower":
                    p.text("Flamethrower", x, 241);
                    p.textFont(mediumFont);
                    p.text("$200", x, 271);
                    break;
                case "railgun":
                    p.text("Railgun", x, 241);
                    p.textFont(mediumFont);
                    p.text("$200", x, 271);
                    break;
                case "waveMotion":
                    p.text("Wave Motion", x, 241);
                    p.text("Tower", x, 266);
                    p.textFont(mediumFont);
                    p.text("$250", x, 296);
                    break;
            }
            //if pressed
            if (inputHandler.leftMousePressedPulse && alive) action();
        }
        else sprite = spriteOne;
    }

    public void action() {
        depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(towerType)) hand.setHeld("null");
        //if not, do
        else if (depressed) hand.setHeld(towerType);
    }
}