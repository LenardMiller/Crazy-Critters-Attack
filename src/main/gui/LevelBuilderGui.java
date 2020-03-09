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
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.main();
    }

    public void build() {
        //bgA
        placeButton(0,0,"dirtBGA");
        placeButton(1,0,"grassBGA");
        //bgB
        placeButton(2,0,"dirtPatchBGB");
        placeButton(3,0,"grassPatchBGB");
        //bgC
        placeButton(0,1,"rockBGC");
        placeButton(1,1,"smallRockBGC");
        placeButton(2,1,"leavesBGC");
        placeButton(3,1,"dandelionsBGC");
        //obstacles
        placeButton(2,2,"smallTreeOb");
        placeButton(0,3,"treeBLOb");
        placeButton(1,3,"treeBROb");
        placeButton(0,2,"treeTLOb");
        placeButton(1,2,"treeTROb");
        //machine
        placeButton(3,2,"Ma");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(p,925+(x*50),25+(y*50),type,true));
    }
}
