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

public class Cannon extends Turret {

    private int effectRadius;
    private boolean frags;
    private boolean dynamite;
    private boolean hasTrail;

    public Cannon(PApplet p, Tile tile) {
        super(p,tile);
        name = "cannon";
        offset = 5;
        delay = 3.2f;
        pjSpeed = 750;
        betweenFireFrames = down60ToFramerate(1);
        damage = 40;
        range = 250;
        effectRadius = 25;
        damageSound = sounds.get("stoneDamage");
        breakSound = sounds.get("stoneBreak");
        placeSound = sounds.get("stonePlace");
        fireSound = sounds.get("smallExplosion");
        material = Material.stone;
        basePrice = CANNON_PRICE;
        fireParticle = "smoke";
        barrelLength = 29;
        titleLines = new String[]{"Cannon"};
        infoDisplay = (o) -> selection.setTextPurple("Small splash", o);
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (!dynamite) {
            for (int i = 0; i < p.random(3, 5); i++) {
                midParticles.add(new MiscParticle(p, position.x, position.y,
                        angle + radians(p.random(-45, 45)), "smoke"));
            }
        }
        if (frags) projectiles.add(new FragBall(p,position.x,position.y, angle, this, getDamage(), effectRadius, pjSpeed, hasTrail));
        else if (dynamite) projectiles.add(new Dynamite(p,position.x,position.y, angle, this, getDamage(), effectRadius, pjSpeed));
        else projectiles.add(new CannonBall(p,position.x,position.y, angle, this, getDamage(), effectRadius, pjSpeed, hasTrail));
    }

    @Override
    protected void setUpgrades() {
        //price
        upgradePrices[0] = 200;
        upgradePrices[1] = 300;
        upgradePrices[2] = 1000;
        upgradePrices[3] = 150;
        upgradePrices[4] = 200;
        upgradePrices[5] = 1500;
        //titles
        upgradeTitles[0] = "Lead Shot";
        upgradeTitles[1] = "Osmium Shot";
        upgradeTitles[2] = "Dynamite";

        upgradeTitles[3] = "Extra Range";
        upgradeTitles[4] = "Rapid Reload";
        upgradeTitles[5] = "Frags";
        //description
        upgradeDescA[0] = "Boost";
        upgradeDescB[0] = "damage";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "Boost";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "even more";

        upgradeDescA[2] = "Creates";
        upgradeDescB[2] = "huge";
        upgradeDescC[2] = "explosions";


        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Burst into";
        upgradeDescB[5] = "shrapnel";
        upgradeDescC[5] = "";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[13];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[49];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[23];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[48];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[24];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0 -> damage += 20;
                case 1 -> {
                    damage += 40;
                    hasTrail = true;
                } case 2 -> {
                    damage += 400;
                    effectRadius = 75;
                    pjSpeed -= 250;
                    dynamite = true;
                    fireSound = sounds.get("slingshot");
                    name = "dynamiteLauncher";
                    material = Material.wood;
                    fireParticle = null;
                    damageSound = sounds.get("woodDamage");
                    breakSound = sounds.get("woodBreak");
                    placeSound = sounds.get("woodPlace");
                    barrelLength = 0;
                    titleLines = new String[]{"Dynamite", "Flinger"};
                    infoDisplay = (o) -> selection.setTextPurple("Large splash", o);
                    loadSprites();
                }
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3 -> {
                    range += 35;
                    pjSpeed += 200;
                } case 4 -> delay -= 0.8f;
                case 5 -> {
                    range += 30;
                    pjSpeed += 200;
                    delay -= 1;
                    frags = true;
                    name = "fragCannon";
                    material = Material.metal;
                    placeSound = sounds.get("metalPlace");
                    damageSound = sounds.get("metalDamage");
                    breakSound = sounds.get("metalBreak");
                    titleLines = new String[]{"Frag Cannon"};
                    infoDisplay = (o) -> {
                        selection.setTextPurple("Small splash", o);
                        selection.setTextPurple("Shrapnel", o);
                    };
                    loadSprites();
                }
            }
        }
    }
}
