package main.gui;

import main.gui.guiObjects.buttons.TileSelect;
import processing.core.PApplet;

import static main.Main.tileSelectButtons;

public class LevelBuilderGui {

    private final PApplet P;

    public LevelBuilderGui(PApplet p) {
        this.P = p;
        build();
    }

    public void display() {
        P.fill(235); //big white bg
        P.rect(900, 0, 200, 900);
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.main();
    }

    public void build() {
        //bgA
        placeButton(0, 0, "dirtBGA");
        placeButton(1, 0, "grassBGA");
        placeButton(2, 4, "sandBGA");
        placeButton(3, 4, "stoneBGA");
        //bgB
        placeButton(0, 1, "dirtPatchBGB");
        placeButton(1, 1, "grassPatchBGB");
        placeButton(2, 0, "grassCornerBR_BGB");
        placeButton(3, 0, "grassCornerBL_BGB");
        placeButton(2, 1, "grassCornerTR_BGB");
        placeButton(3, 1, "grassCornerTL_BGB");
        placeButton(3, 8, "lichenBGB");
        //bgC
        placeButton(0, 2, "rockBGC");
        placeButton(1, 2, "smallRockBGC");
        placeButton(2, 2, "leavesBGC");
        placeButton(3, 2, "dandelionsBGC");
        placeButton(3, 5, "glowshrooms1BGC");
        placeButton(3, 6, "glowshrooms2BGC");
        placeButton(3, 7, "glowshrooms3BGC");
        //obstacles
        placeButton(2, 3, "smallTreeOb");
        placeButton(0, 4, "treeBLOb");
        placeButton(1, 4, "treeBROb");
        placeButton(0, 3, "treeTLOb");
        placeButton(1, 3, "treeTROb");
        placeButton(0,5, "cactus0Ob");
        placeButton(1,5, "cactus1Ob");
        placeButton(2,5, "cactus2Ob");
        placeButton(1, 7, "caveWallBaseOb");
        placeButton(0, 7, "caveWallLOb");
        placeButton(2, 7, "caveWallROb");
        placeButton(1, 6, "caveWallTOb");
        placeButton(1, 8, "caveWallBOb");
        placeButton(0, 6, "caveWallTLOb");
        placeButton(2, 6, "caveWallTROb");
        placeButton(0, 8, "caveWallBLOb");
        placeButton(2, 8, "caveWallBROb");
        placeButton(1, 10, "caveWallTLCOb");
        placeButton(0, 10, "caveWallTRCOb");
        placeButton(1, 9, "caveWallBLCOb");
        placeButton(0, 9, "caveWallBRCOb");
        placeButton(3, 10, "caveWallTLDOb");
        placeButton(2, 10, "caveWallTRDOb");
        placeButton(3, 9, "caveWallBLDOb");
        placeButton(2, 9, "caveWallBRDOb");
        placeButton(0, 11, "caveWallPillarOb");
        //machine
        placeButton(3, 3, "Ma");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
