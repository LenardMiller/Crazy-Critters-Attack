package main.towers.turrets;

import main.projectiles.Bolt;
import main.projectiles.ReinforcedBolt;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.randomizeDelay;

public class Crossbow extends Turret {

    private boolean multishot;
    private boolean reinforced;

    public Crossbow(PApplet p, Tile tile) {
        super(p,tile);
        offset = 2;
        name = "crossbow";
        delay = randomizeDelay(p, 4.5f);
        pjSpeed = 1400;
        range = 320;
        damage = 30;
        pierce = 1;
        damageSound = sounds.get("woodDamage");
        breakSound = sounds.get("woodBreak");
        placeSound = sounds.get("woodPlace");
        fireSound = sounds.get("crossbow");
        material = "wood";
        basePrice = CROSSBOW_PRICE;
        priority = Priority.Far;
        titleLines = new String[]{"Crossbow"};
    }

    @Override
    protected void spawnProjectiles(PVector position, float angle) {
        if (multishot) {
            float offset = 0.07f;
            int count = 7;
            float a = angle - (floor(count / 2f) * offset);
            for (int i = 0; i < count; i++) {
                projectiles.add(new Bolt(p, position.x, position.y, a, this, getDamage(), pierce));
                a += offset;
            }
        } else {
            if (reinforced) projectiles.add(new ReinforcedBolt(p,position.x, position.y, angle, this, getDamage(), pierce));
            else projectiles.add(new Bolt(p,position.x, position.y, angle, this, getDamage(), pierce));
        }
    }

    @Override
    protected void setUpgrades(){
        //price
        upgradePrices[0] = 75;
        upgradePrices[1] = 175;
        upgradePrices[2] = 750;
        upgradePrices[3] = 100;
        upgradePrices[4] = 150;
        upgradePrices[5] = 650;
        //titles
        upgradeTitles[0] = "Pointier";
        upgradeTitles[1] = "Sharper";
        upgradeTitles[2] = "Reinforced";

        upgradeTitles[3] = "Increase range";
        upgradeTitles[4] = "Faster Firing";
        upgradeTitles[5] = "Barrage";
        //description
        upgradeDescA[0] = "Increase";
        upgradeDescB[0] = "piercing";
        upgradeDescC[0] = "";

        upgradeDescA[1] = "+20";
        upgradeDescB[1] = "damage";
        upgradeDescC[1] = "";

        upgradeDescA[2] = "+300";
        upgradeDescB[2] = "damage,";
        upgradeDescC[2] = "+piercing";

        upgradeDescA[3] = "Increase";
        upgradeDescB[3] = "range";
        upgradeDescC[3] = "";

        upgradeDescA[4] = "Increase";
        upgradeDescB[4] = "firerate";
        upgradeDescC[4] = "";

        upgradeDescA[5] = "Fire";
        upgradeDescB[5] = "multiple";
        upgradeDescC[5] = "bolts";
        //icons
        upgradeIcons[0] = animatedSprites.get("upgradeIC")[9];
        upgradeIcons[1] = animatedSprites.get("upgradeIC")[8];
        upgradeIcons[2] = animatedSprites.get("upgradeIC")[18];

        upgradeIcons[3] = animatedSprites.get("upgradeIC")[6];
        upgradeIcons[4] = animatedSprites.get("upgradeIC")[7];
        upgradeIcons[5] = animatedSprites.get("upgradeIC")[19];
    }

    @Override
    protected void upgradeEffect(int id) {
        if (id == 0) {
            switch (nextLevelA) {
                case 0:
                    pierce += 2;
                    break;
                case 1:
                    damage += 20;
                    break;
                case 2:
                    damage += 300;
                    pierce += 5;
                    reinforced = true;
                    titleLines = new String[]{"Reinforced", "Crossbow"};
                    name = "crossbowReinforced";
                    loadSprites();
                    break;
            }
        } if (id == 1) {
            switch (nextLevelB) {
                case 3:
                    range += 30;
                    break;
                case 4:
                    delay -= 1.1f;
                    break;
                case 5:
                    multishot = true;
                    name = "crossbowMultishot";
                    fireSound = sounds.get("shotbow");
                    titleLines = new String[]{"Shotbow"};
                    infoDisplay = (o) -> selection.setTextPurple("Seven bolts", o);
                    loadSprites();
                    break;
            }
        }
    }

    public void updateSprite() {}
}