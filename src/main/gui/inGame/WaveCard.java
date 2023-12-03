package main.gui.inGame;

import main.Main;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.enemies.shootingEnemies.ShootingEnemy;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class WaveCard {

    private final PApplet p;
    private final Color fillColor, accentColor, textColor;
    private final String[] title;
    private final PImage burrowingIcon;
    private final PImage flyingIcon;
    private final PImage shootingIcon;

    private boolean hasBurrowing;
    private boolean hasFlying;
    private boolean hasShooting;

    public WaveCard(PApplet p, Color fillColor, Color accentColor, Color textColor, String[] title) {
        this.p = p;
        this.fillColor = fillColor;
        this.accentColor = accentColor;
        this.textColor = textColor;
        this.title = title;

        burrowingIcon = staticSprites.get("burrowingTypeIc");
        flyingIcon = staticSprites.get("flyingTypeIc");
        shootingIcon = staticSprites.get("shootingTypeIc");
    }

    public void addEnemyType(Enemy enemy) {
        if (enemy instanceof FlyingEnemy) hasFlying = true;
        if (enemy instanceof BurrowingEnemy) hasBurrowing = true;
        if (enemy instanceof ShootingEnemy) hasShooting = true;
    }

    public void display(int y, int waveNum, int maxWave) {
        p.tint(fillColor.getRGB());
        p.image(staticSprites.get("wavePrimaryIc"), -200, y);
        p.tint(accentColor.getRGB());
        p.image(staticSprites.get("waveSecondaryIc"), -200, y);
        p.tint(255);

        p.fill(textColor.getRGB());
        p.textAlign(CENTER);
        //wave number
        p.textFont(h4);
        p.text("Wave " + waveNum + "/" + maxWave, -100, y + 25);
        //title
        p.textFont(Main.h1);
        if (title.length == 1) {
            p.text(title[0], -100, y + 75);
        } else {
            p.text(title[0], -100, y + 60);
            p.text(title[1], -100, y + 90);
        }
        //enemy types
        int typeCount = 0;
        if (hasBurrowing) typeCount++;
        if (hasFlying) typeCount++;
        if (hasShooting) typeCount++;

        int burrowingPos = switch (typeCount) {
            case 2 -> -125;
            case 3 -> -150;
            default -> -100;
        };

        int flyingPos = -100;
        if (hasShooting && !hasBurrowing) flyingPos = -125;
        else if (!hasShooting && hasBurrowing) flyingPos = -75;

        int shootingPos = switch (typeCount) {
            case 2 -> -75;
            case 3 -> -50;
            default -> -100;
        };

        p.imageMode(CENTER);

        if (hasBurrowing) p.image(burrowingIcon, burrowingPos, y + 105);
        if (hasFlying)    p.image(flyingIcon,    flyingPos, y + 105);
        if (hasShooting)  p.image(shootingIcon,  shootingPos, y + 105);

        p.imageMode(DEFAULT_MODE);

//        p.textFont(mediumLargeFont);
//        StringBuilder enemyTypes = new StringBuilder();
//        if (hasBurrowing) {
//            if (letterCount > 1) enemyTypes.append(" B");
//            else enemyTypes.append(" Burrowing");
//        } if (hasFlying) {
//            if (letterCount > 1) {
//                if (hasBurrowing) enemyTypes.append(" &");
//                enemyTypes.append(" F");
//            }
//            else enemyTypes.append(" Flying");
//        } if (hasShooting) {
//            if (letterCount > 1) enemyTypes.append(" & S");
//            else enemyTypes.append(" Shooting");
//        }
//        p.text(enemyTypes.toString(), -100, y + 25);
    }
}
