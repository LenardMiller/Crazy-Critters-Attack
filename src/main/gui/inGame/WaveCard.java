package main.gui.inGame;

import main.Main;
import main.enemies.Enemy;
import main.enemies.BurrowingEnemy;
import main.enemies.FlyingEnemy;
import main.enemies.ShootingEnemy;
import main.sound.SoundUtilities;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static processing.core.PConstants.CENTER;

public class WaveCard {

    public static final float MAX_SPEED = 10;
    public static final float ACCELERATION = 1;

    private final PApplet p;
    private final Color fillColor, accentColor, textColor;
    private final String[] title;
    private final PImage burrowingIcon;
    private final PImage flyingIcon;
    private final PImage shootingIcon;

    public float speed;
    public PVector position;

    private boolean hasBurrowing;
    private boolean hasFlying;
    private boolean hasShooting;
    private boolean isCurrentWave;
    private boolean overrideFillAlpha;
    private int fillAlpha;
    private int strokeAlpha = 255;

    public WaveCard(PApplet p, Color fillColor, Color accentColor, Color textColor, String[] title) {
        this.p = p;
        this.fillColor = fillColor;
        this.accentColor = accentColor;
        this.textColor = textColor;
        this.title = title;

        burrowingIcon = staticSprites.get("burrowingIc");
        flyingIcon = staticSprites.get("flyingIc");
        shootingIcon = staticSprites.get("shootingIc");

        position = new PVector(-200, WINDOW_HEIGHT);
    }

    public void addEnemyType(Enemy enemy) {
        if (enemy instanceof FlyingEnemy) hasFlying = true;
        if (enemy instanceof BurrowingEnemy) hasBurrowing = true;
        if (enemy instanceof ShootingEnemy) hasShooting = true;
    }

    public void display(int waveNum, int maxWave) {
        if (waveNum - 1 == levels[currentLevel].currentWave && isPlaying) {
            if (!isCurrentWave && !overrideFillAlpha) fillAlpha = 255;
            isCurrentWave = true;
        } else {
            if (isCurrentWave && !isPaused) strokeAlpha = max(strokeAlpha - 10, 0);
        }

        float centerX = position.x + 100;

        p.tint(fillColor.getRGB());
        p.image(staticSprites.get("wavePrimaryIc"), position.x, position.y);
        p.tint(accentColor.getRGB());
        p.image(staticSprites.get("waveSecondaryIc"), position.x, position.y);
        p.tint(255);

        p.fill(textColor.getRGB());
        p.textAlign(CENTER);
        //wave number
        p.textFont(h4);
        p.text("Wave " + waveNum + "/" + maxWave, centerX, position.y + 25);
        //title
        p.textFont(Main.h1);
        if (title.length == 1) {
            p.text(title[0], centerX, position.y + 75);
        } else {
            p.text(title[0], centerX, position.y + 60);
            p.text(title[1], centerX, position.y + 90);
        }
        //enemy types
        int typeCount = 0;
        if (hasBurrowing) typeCount++;
        if (hasFlying) typeCount++;
        if (hasShooting) typeCount++;

        int separation = 15;

        float burrowingPos = switch (typeCount) {
            case 2 -> centerX - separation;
            case 3 -> centerX - separation * 2;
            default -> centerX;
        };

        float flyingPos = centerX;
        if (hasShooting && !hasBurrowing) flyingPos = centerX - separation;
        else if (!hasShooting && hasBurrowing) flyingPos = centerX + separation;

        float shootingPos = switch (typeCount) {
            case 2 -> centerX + separation;
            case 3 -> centerX + separation * 2;
            default -> centerX;
        };

        p.imageMode(CENTER);

        if (hasBurrowing) p.image(burrowingIcon, burrowingPos, position.y + 105);
        if (hasFlying)    p.image(flyingIcon,    flyingPos, position.y + 105);
        if (hasShooting)  p.image(shootingIcon,  shootingPos, position.y + 105);

        p.imageMode(DEFAULT_MODE);

        if (isCurrentWave) {
            p.fill(255, fillAlpha);
            if (!isPaused) fillAlpha = max(fillAlpha - 10, 0);
            p.strokeWeight(2);
            p.stroke(255, strokeAlpha);
            p.rect(position.x + 1, position.y + 1, 198, 123);
            p.strokeWeight(1);
            p.noStroke();
        }
    }

    public void slide(float target) {
        position.y -= speed;
        if (position.y <= target) {
            if (speed > 0) {
                SoundUtilities.playSound(sounds.get("uiBonk"), 1, 1);
            }
            speed = 0;
            position.y = target;
        }
        else speed = Math.min(speed + WaveCard.ACCELERATION, WaveCard.MAX_SPEED);
    }

    public void preset(PVector position, boolean overrideFillAlpha) {
        this.position = position;
        this.overrideFillAlpha = overrideFillAlpha;
    }
}
