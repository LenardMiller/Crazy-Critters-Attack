package main.gui.inGame;

import main.Main;
import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.enemies.flyingEnemies.FlyingEnemy;
import main.enemies.shootingEnemies.ShootingEnemy;
import processing.core.PApplet;

import java.awt.*;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class WaveCard {

    private final PApplet p;
    private final Color fillColor, accentColor, textColor;
    private final String[] title;

    private boolean hasBurrowing;
    private boolean hasFlying;
    private boolean hasShooting;

    public WaveCard(PApplet p, Color fillColor, Color accentColor, Color textColor, String[] title) {
        this.p = p;
        this.fillColor = fillColor;
        this.accentColor = accentColor;
        this.textColor = textColor;
        this.title = title;
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
