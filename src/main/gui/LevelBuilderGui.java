package main.gui;

import main.gui.guiObjects.buttons.TileSelect;
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
        p.rect(900, 0, 200, 900);
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.main();
    }

    public void build() {
        //bgA
        placeButton(0, 0, "dirtBGA");
        placeButton(1, 0, "grassBGA");
        placeButton(2, 4, "sandBGA");
        //bgB
        placeButton(0, 1, "dirtPatchBGB");
        placeButton(1, 1, "grassPatchBGB");
        placeButton(2, 0, "grassCornerBR_BGB");
        placeButton(3, 0, "grassCornerBL_BGB");
        placeButton(2, 1, "grassCornerTR_BGB");
        placeButton(3, 1, "grassCornerTL_BGB");
        //bgC
        placeButton(0, 2, "rockBGC");
        placeButton(1, 2, "smallRockBGC");
        placeButton(2, 2, "leavesBGC");
        placeButton(3, 2, "dandelionsBGC");
        //obstacles
        placeButton(2, 3, "smallTreeOb");
        placeButton(0, 4, "treeBLOb");
        placeButton(1, 4, "treeBROb");
        placeButton(0, 3, "treeTLOb");
        placeButton(1, 3, "treeTROb");
        //machine
        placeButton(3, 3, "Ma");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(p, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
