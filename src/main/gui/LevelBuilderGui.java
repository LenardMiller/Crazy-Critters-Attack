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
        P.fill(InGameGui.MAIN_PANEL_COLOR.getRGB()); //big white bg
        P.rect(900, 0, 200, 900);
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.main();
    }

    public void build() {
        placeButton(0, 0, "dirtBa");
        placeButton(1, 0, "grassBa");
        placeButton(2, 0, "grassCornerBR_De");
        placeButton(3, 0, "grassCornerBL_De");
        placeButton(0, 1, "dirtPatchDe");
        placeButton(1, 1, "grassPatchDe");
        placeButton(2, 1, "grassCornerTR_De");
        placeButton(3, 1, "grassCornerTL_De");
        placeButton(0, 2, "rockBr");
        placeButton(1, 2, "smallRockBr");
        placeButton(2, 2, "leavesBr");
        placeButton(3, 2, "dandelionsBr");
        placeButton(0, 3, "treeTLOb");
        placeButton(1, 3, "treeTROb");
        placeButton(2, 3, "smallTreeOb");
        //blank
        placeButton(1, 4, "treeBROb");
        placeButton(0, 4, "treeBLOb");
        placeButton(2, 4, "sandBa");
        placeButton(3, 4, "stoneBa");
        placeButton(0, 5, "cactus0Ob");
        placeButton(1, 5, "cactus1Ob");
        placeButton(2, 5, "cactus2Ob");
        placeButton(3, 5, "glowshrooms0Br");
        placeButton(0, 6, "caveWallTLOb");
        placeButton(1, 6, "caveWallTOb");
        placeButton(2, 6, "caveWallTROb");
        placeButton(3, 6, "glowshrooms1Br");
        placeButton(0, 7, "caveWallLOb");
        placeButton(1, 7, "caveWallBaseOb");
        placeButton(2, 7, "caveWallROb");
        placeButton(3, 7, "glowshrooms2Br");
        placeButton(0, 8, "caveWallBLOb");
        placeButton(1, 8, "caveWallBOb");
        placeButton(2, 8, "caveWallBROb");
        placeButton(3, 8, "lichenDe");
        placeButton(0, 9, "caveWallBRCOb");
        placeButton(1, 9, "caveWallBLCOb");
        placeButton(2, 9, "caveWallBRDOb");
        placeButton(3, 9, "caveWallBLDOb");
        placeButton(0, 10, "caveWallTRCOb");
        placeButton(1, 10, "caveWallTLCOb");
        placeButton(2, 10, "caveWallTRDOb");
        placeButton(3, 10, "caveWallTLDOb");
        placeButton(0, 11, "caveWallPillarOb");
        placeButton(1, 11, "yellowGrassBa");
        placeButton(2, 11, "smallYellowTreeOb");
        placeButton(3, 11, "yellowLeavesBr");
        placeButton(0, 12, "deadDandelionsBr");
        placeButton(1, 12, "yellowGrassPatchDe");
        placeButton(2, 12, "deadCactus0Ob");
        placeButton(3, 12, "deadCactus1Ob");
        placeButton(0, 13, "deadCactus2Ob");
        placeButton(1, 13, "deadGlowshrooms0Br");
        placeButton(2, 13, "deadGlowshrooms1Br");
        placeButton(3, 13, "deadGlowshrooms2Br");
        placeButton(0, 14, "deadLichenDe");
        placeButton(1, 14, "snowBa");

        placeButton(0, 17, "Na");
        placeButton(1, 17, "Ma");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
