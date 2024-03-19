package main.gui.guiObjects.buttons;

import main.misc.IntVector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.gui.inGame.TowerInfo.*;
import static main.sound.SoundUtilities.playSound;

public class TowerBuy extends Button {

    private final String towerName;
    private final Class<?> turretClass;
    private final PImage spriteInvalid;

    public boolean depressed;
    public int price;

    public TowerBuy(PApplet p, float x, float y, String name, Class<?> turretClass, boolean active) {
        super(p, x, y, name, active);
        price = 0;
        position = new PVector(x, y);
        size = new PVector(35, 35);
        towerName = name;
        spriteLocation = "sprites/gui/buttons/towerBuy/" + towerName + "/"; //still uses old system because it is only created at beginning of game
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        spriteHover = p.loadImage(spriteLocation + "002.png");
        spriteInvalid = p.loadImage(spriteLocation + "003.png");
        sprite = spriteIdle;
        depressed = false;
        this.turretClass = turretClass;
        if (turretClass != null) {
            try {
                price = (int) turretClass.getField("price").get(null);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }
    }

    @Override
    public void display() {
        if (!active) return;
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
        if (hovered()) displayTurretInfo(p, turretClass);
    }

    @Override
    public void update() {
        if (!active) return;
        if (!towerName.equals("null")) hover();
        if (money < price) {
            p.tint(255, 0, 0, 200);
            p.image(sprite, position.x - size.x / 2, position.y - size.y / 2);
            p.tint(255);
        }
    }

    private boolean hovered() {
        if (towerName.equals("null")) return false;
        IntVector d = new IntVector(1, 1);
        boolean matchX = boardMousePosition.x < (position.x+size.x/2)+d.x && boardMousePosition.x > (position.x-size.x/2)-d.x-1;
        boolean matchY = boardMousePosition.y < (position.y+size.y/2)+d.y && boardMousePosition.y > (position.y-size.y/2)-d.y-1;
        boolean matchPosition = matchX && matchY && active;
        return ((matchPosition && !paused) || depressed) && alive;
    }

    @Override
    public void hover() {
        if (hovered()) {
            if (depressed) sprite = spritePressed;
            else sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse && !depressed) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMousePressedPulse && alive && !paused) {
                pressIn();
                if (money >= price) sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (money < price) {
            if (spriteInvalid != null) sprite = spriteInvalid;
            else sprite = spritePressed;
        }
    }

    @Override
    public void pressIn() {
        if (money < price) depressed = false;
        else depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(towerName)) {
            hand.setHeld("null");
        }
        //if not, do
        else if (depressed) {
            hand.setHeld(towerName);
            hand.heldClass = turretClass;
        }
    }
}