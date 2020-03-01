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
        //bgA
        tileSelectButtons.add(new TileSelect(p,925,25,"dirtBGA",true));
        tileSelectButtons.add(new TileSelect(p,975,25,"grassBGA",true));
        //bgB
        tileSelectButtons.add(new TileSelect(p,1025,25,"dirtPatchBGB",true));
        tileSelectButtons.add(new TileSelect(p,1075,25,"grassPatchBGB",true));
        //bgC
        tileSelectButtons.add(new TileSelect(p,925,75,"rockBGC",true));
        tileSelectButtons.add(new TileSelect(p,975,75,"smallRockBGC",true));
        //obstacles
        tileSelectButtons.add(new TileSelect(p,1025,75,"smallTreeOb",true));
        tileSelectButtons.add(new TileSelect(p,925,175,"treeBLOb",true));
        tileSelectButtons.add(new TileSelect(p,975,175,"treeBROb",true));
        tileSelectButtons.add(new TileSelect(p,925,125,"treeTLOb",true));
        tileSelectButtons.add(new TileSelect(p,975,125,"treeTROb",true));
    }
}
