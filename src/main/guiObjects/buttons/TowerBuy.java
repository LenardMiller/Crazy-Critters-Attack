package main.guiObjects.buttons;

import main.guiObjects.GuiObject;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

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
                price = 100;
                break;
            case "miscCannon":
                price = 100;
                break;
        }
    }

    public void main(ArrayList<GuiObject> guiObjects, int i){
        if (active){
            hover();
            display();
        }
    }

    public void hover(){ //below is if hovered or depressed
        if (p.mouseX < position.x+size.x/2 && p.mouseX > position.x-size.x/2 && p.mouseY < position.y+size.y/2 && p.mouseY > position.y-size.y/2 && alive && active || depressed && alive){
            sprite = spriteTwo;
            p.fill(235);
            p.noStroke();
            p.rect(700,211,200,707);
            p.textAlign(CENTER);
            p.fill(0);
            p.textFont(largeFont); //displays info about tower todo: put more info
            switch (towerType) {
                case "slingshot":
                    p.text("Slingshot", 800, 241);
                    p.textFont(mediumFont);
                    p.text("$50", 800, 271);
                    break;
                case "crossbow":
                    p.text("Crossbow", 800, 241);
                    p.textFont(mediumFont);
                    p.text("$100", 800, 271);
                    break;
                case "miscCannon":
                    p.text("Random", 800, 241);
                    p.text("Cannon", 800, 266);
                    p.textFont(mediumFont);
                    p.text("$100", 800, 296);
                    break;
                case "energyBlaster":
                    p.text("Energy Blaster", 800, 241);
                    p.textFont(mediumFont);
                    p.text("$150", 800, 271);
                    break;
                case "magicMissleer":
                    p.text("Magic Missileer", 800, 241);
                    p.textFont(mediumFont);
                    p.text("$150", 800, 271);
                    break;
                case "wall":
                    p.text("Wooden", 800, 241);
                    p.text("Wall", 800, 266);
                    p.textFont(mediumFont);
                    p.text("$25", 800, 296);
                    break;
            }
            //if pressed
            if (inputHandler.leftMousePressedPulse && alive) action();
        }
        else{
            sprite = spriteOne;
        }
    }

    public void action(){
        depressed = !depressed; //invert depression
        if (hand.held.equals(towerType)){ //if already holding, stop
            hand.setHeld("null");
        }
        else if (depressed){ //if not, do
            hand.setHeld(towerType);
        }
    }
}