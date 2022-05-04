package main.towers.turrets;

import main.projectiles.CannonBall;
import main.projectiles.Dynamite;
import main.projectiles.FragBall;
import main.misc.Tile;
import main.particles.MiscParticle;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;
import static main.misc.Utilities.randomizeDelay;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Cannon extends Turret {

    private int effectRadius;
    private boolean frags;
    private boolean dynamite;

    public Cannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "cannon";
        offset = 5;
        delay = randomizeDelay(p, 3.2f);
        pjSpeed = 850;
        betweenFireFrames = down60ToFramerate(1);
        damage = 40;
        range = 250;
        effectRadius = 25;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("smallExplosion");
        material = "stone";
        value = CANNON_PRICE;
        fireParticle = "smoke";
        barrelLength = 29;
        titleLines = new String[]{"Cannon"};
        infoDisplay = (o) -> selection.setTextPurple("Small splash", o);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        for (int i = 0; i < p.random(3, 5); i++) {
            midParticles.add(new MiscParticle(p, position.x, position.y,
              angle + radians(p.random(-45, 45)), "smoke"));
        }
        if (frags) projectiles.add(new FragBall(p,position.x,position.y, angle, this, getDamage(), effectRadius));
        else if (dynamite) projectiles.add(new Dynamite(p,position.x,position.y, angle, this, getDamage(), effectRadius));
        else projectiles.add(new CannonBall(p,position.x,position.y, angle, this, getDamage(), effectRadius));
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 250;
        upgradePrices[1] = 400;
        upgradePrices[2] = 850;
        upgradePrices[3] = 150;
        upgradePrices[4] = 250;
        upgradePrices[5] = 1250;
        //titles
        upgradeTitles[0] = "Stronger shot";
        upgradeTitles[1] = "Powerful shot";
        upgradeTitles[2] = "Dynamite";

        upgradeTitles[3] = "Extra range";
        upgradeTitles[4] = "Rapid reload";
        upgradeTitles[5] = "Frags";
        //description
        upgradeDescA[0] = "boost";
        upgradeDescB[0] = "damage";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "boost";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "some more";

        upgradeDescA[2] = "Creates";
        upgradeDescB[2] = "huge";
        upgradeDescC[2] = "explosions";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Bursts into";
        upgradeDescB[5] = "shrapnel";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[23];
        upgradeIcons[3] = animatedSprites.get("upgradeIC")[5];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[24];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    damage += 20;
                    break;
                case 1:
                    damage += 40;
                    break;
                case 2:
                    damage += 400;
                    effectRadius = 60;
                    pjSpeed = 600;
                    dynamite = true;
                    fireSound = sounds.get("slingshot");
                    name = "dynamiteLauncher";
                    material = "wood";
                    fireParticle = null;
                    damageSound = sounds.get("woodDamage");
                    breakSound = sounds.get("woodBreak");
                    placeSound = sounds.get("woodPlace");
                    barrelLength = 0;
                    titleLines = new String[]{"Dynamite", "Flinger"};
                    infoDisplay = (o) -> selection.setTextPurple("Large splash", o);
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 35;
                    break;
                case 4:
                    delay -= 0.8f;
                    break;
                case 5:
                    range += 30;
                    delay -= 1;
                    frags = true;
                    name = "fragCannon";
                    material = "metal";
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    titleLines = new String[]{"Frag Cannon"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Small splash", o);
                        selection.setTextPurple("Shrapnel", o);
                    };
                    loadSprites();
                    break;
            }
        }
    }
}
