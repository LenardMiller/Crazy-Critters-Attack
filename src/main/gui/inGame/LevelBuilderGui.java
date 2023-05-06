package main.gui.inGame;

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
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.display();
    }

    public void update() {
        for (TileSelect tileSelectButton : tileSelectButtons) tileSelectButton.update();
    }


    public void build() {
        placeButton(0, 0, "caveWallMossyTBOb");
        placeButton(1, 0, "caveWallMossyTLBOb");
        placeButton(2, 0, "caveWallMossyTLDBOb");

        placeButton(0, 1, "caveWallMossyBRDGOb");
        placeButton(1, 1, "caveWallMossyRGOb");
        placeButton(2, 1, "caveWallMossyTGOb");
        placeButton(3, 1, "caveWallMossyTRGOb");

        placeButton(0, 2, "caveWallMossyBLDPOb");
        placeButton(1, 2, "caveWallMossyBPOb");
        placeButton(2, 2, "caveWallMossyBRPOb");

        placeButton(0, 3, "caveWallShroomyTOb");
        placeButton(1, 3, "caveWallShroomyT2Ob");
        placeButton(2, 3, "caveWallShroomyBLDOb");
        placeButton(3, 3, "caveWallShroomyTRDOb");

        placeButton(0, 4, "caveWallShroomyPillarOb");
        placeButton(1, 4, "caveWallMossyPillarOb");
        placeButton(2, 4, "deadCactus3Ob");
        placeButton(3, 4, "deadCactus4Ob");

        placeButton(0, 5, "deadCactus5Ob");
        placeButton(1, 5, "deadBushOb");
        placeButton(2, 5, "yellowLeavesBr");
        placeButton(3, 5, "deadDandelionsBr");

        placeButton(0, 6, "waterBa");
        placeButton(1, 6, "yellowGrassBa");
        placeButton(2, 6, "yellowGrassCornerBR_De");
        placeButton(3, 6, "yellowGrassCornerBL_De");

        placeButton(0, 7, "dirtyWaterBa");
        placeButton(1, 7, "yellowGrassPatchDe");
        placeButton(2, 7, "yellowGrassCornerTR_De");
        placeButton(3, 7, "yellowGrassCornerTL_De");

        placeButton(0, 8, "flowerCyanBr");
        placeButton(1, 8, "lilyPad0De");
        placeButton(2, 8, "lilyPad1De");
        placeButton(3, 8, "lilyPad2De");

        placeButton(0, 9, "flowerDeadCyanBr");
        placeButton(1, 9, "deadLilyPad0De");
        placeButton(2, 9, "deadLilyPad1De");
        placeButton(3, 9, "deadLilyPad2De");

        placeButton(0, 10, "deadGrassBa");
        placeButton(1, 10, "flowerVeryDeadCyanBr");
        placeButton(2, 10, "veryDeadDandelionsBr");
        placeButton(3, 10, "deadLeavesBr");

        placeButton(0, 11, "veryDeadLilyPad0De");
        placeButton(1, 11, "veryDeadLilyPad1De");
        placeButton(2, 11, "veryDeadLilyPad2De");
        placeButton(3, 11, "smallDeadTreeOb");

        placeButton(0, 12, "deadGrassCornerBR_De");
        placeButton(1, 12, "deadGrassCornerBL_De");
        placeButton(2, 12, "deadTreeTLOb");
        placeButton(3, 12, "deadTreeTROb");

        placeButton(0, 13, "deadGrassCornerTR_De");
        placeButton(1, 13, "deadGrassCornerTL_De");
        placeButton(2, 13, "deadTreeBLOb");
        placeButton(3, 13, "deadTreeBROb");

        placeButton(0, 14, "brownGrassCornerBR_De");
        placeButton(1, 14, "brownGrassCornerBL_De");
        placeButton(2, 14, "brownTreeTLOb");
        placeButton(3, 14, "brownTreeTROb");

        placeButton(0, 15, "brownGrassCornerTR_De");
        placeButton(1, 15, "brownGrassCornerTL_De");
        placeButton(2, 15, "brownTreeBLOb");
        placeButton(3, 15, "brownTreeBROb");

        placeButton(0, 16, "brownGrassBa");
        placeButton(1, 16, "smallBrownTreeOb");
        placeButton(2, 16, "sludgeBa");

        placeButton(0, 17, "Na");
        placeButton(1, 17, "Ma");
        placeButton(2, 17, "invisibleOb");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
