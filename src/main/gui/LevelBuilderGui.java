package main.gui;

import main.guiObjects.buttons.TileSelect;
import processing.core.PApplet;

import static main.Main.tileSelectButtons;

public class LevelBuilderGui {

    private PApplet p;

    public LevelBuilderGui(PApplet p) {
        this.p = p;
        build();
    }

    public void display() {
        p.fill(235); //big white bg
        p.rect(900,0,200,900);
        for (TileSelect tileSelectButton : tileSelectButtons) {
            tileSelectButton.main();
        }
    }

    public void build() {
        tileSelectButtons.add(new TileSelect(p,925,25,"dirtBGA",true));
        tileSelectButtons.add(new TileSelect(p,975,25,"grassBGA",true));
        tileSelectButtons.add(new TileSelect(p,1025,25,"rockBGC",true));
        tileSelectButtons.add(new TileSelect(p,1075,25,"smallRockBGC",true));
    }
}
