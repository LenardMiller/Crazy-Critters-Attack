package main.towers;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.spritesAnimH;
import static main.Main.spritesH;

public class Wall extends Tower{
    public Wall(PApplet p, Tile tile) {
        super(p,tile);

        name = "woodWall";
        size = new PVector(50,50);
        maxHp = 50;
        hp = maxHp;
        hit = false;
        sprite = spritesH.get("woodWallTW");
        debrisType = "wood";
        price = 25;
        value = price;
        nextLevelOne = 0;

        upgradePrices = new int[4];
        upgradeHealth = new int[4];
        upgradeNames = new String[4];
        upgradeDebris = new String[4];
        upgradeTitles = new String[4];
        upgradeIcons = new PImage[4];
        upgradeSprites = new PImage[4];
        setUpgrades();
    }

    private void setUpgrades(){
        //price
        upgradePrices[0] = 50;
        upgradePrices[1] = 100;
        upgradePrices[2] = 225;
        upgradePrices[3] = 500;
        //heath
        upgradeHealth[0] = 75;
        upgradeHealth[1] = 125;
        upgradeHealth[2] = 250;
        upgradeHealth[3] = 500;
        //names
        upgradeNames[0] = "stoneWall";
        upgradeNames[1] = "metalWall";
        upgradeNames[2] = "crystalWall";
        upgradeNames[3] = "ultimateWall";
        //debris
        upgradeDebris[0] = "stone";
        upgradeDebris[1] = "metal";
        upgradeDebris[2] = "crystal";
        upgradeDebris[3] = "ultimate";
        //titles
        upgradeTitles[0] = "Stone";
        upgradeTitles[1] = "Metal";
        upgradeTitles[2] = "Crystal";
        upgradeTitles[3] = "Titanium";
        //desc line one
        upgradeDescOne[0] = "+75 HP";
        upgradeDescOne[1] = "+125 HP";
        upgradeDescOne[2] = "+250 HP";
        upgradeDescOne[3] = "+500 HP";
        //desc line two
        upgradeDescTwo[0] = "";
        upgradeDescTwo[1] = "";
        upgradeDescTwo[2] = "";
        upgradeDescTwo[3] = "";
        //desc line three
        upgradeDescThree[0] = "";
        upgradeDescThree[1] = "";
        upgradeDescThree[2] = "";
        upgradeDescThree[3] = "";
        //icons
        upgradeIcons[0] = spritesAnimH.get("upgradeIC")[1];
        upgradeIcons[1] = spritesAnimH.get("upgradeIC")[2];
        upgradeIcons[2] = spritesAnimH.get("upgradeIC")[3];
        upgradeIcons[3] = spritesAnimH.get("upgradeIC")[4];
        //sprites
        upgradeSprites[0] = spritesH.get("stoneWallTW");
        upgradeSprites[1] = spritesH.get("metalWallTW");
        upgradeSprites[2] = spritesH.get("crystalWallTW");
        upgradeSprites[3] = spritesH.get("ultimateWallTW");
    }
}