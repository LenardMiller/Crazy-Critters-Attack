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
        placeButton(0, 0, "sandstoneRockBr");
        placeButton(1, 0, "smallSandstoneRockBr");
        placeButton(2, 0, "blueFlowersBr");
        placeButton(3, 0, "orangeFlowersBr");

        placeButton(0, 1, "cactus0Ob");
        placeButton(1, 1, "cactus1Ob");
        placeButton(2, 1, "cactus2Ob");
        placeButton(3, 1, "cactus3Ob");

        placeButton(0, 2, "cactus4Ob");
        placeButton(1, 2, "cactus5Ob");
        placeButton(2, 2, "bushOb");
        placeButton(3, 2, "yellowGrassPatchDe");

        placeButton(0, 3, "deadBlueFlowersBr");
        placeButton(1, 3, "deadDandelionsBr");
        placeButton(2, 3, "deadOrangeFlowersBr");
        placeButton(3, 3, "deadCactus0Ob");

        placeButton(0, 4, "deadCactus1Ob");
        placeButton(1, 4, "deadCactus2Ob");
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
        placeButton(1, 8, "lilyPad0Br");
        placeButton(2, 8, "lilyPad1Br");
        placeButton(3, 8, "lilyPad2Br");

        placeButton(0, 9, "flowerDeadCyanBr");
        placeButton(1, 9, "deadLilyPad0Br");
        placeButton(2, 9, "deadLilyPad1Br");
        placeButton(3, 9, "deadLilyPad2Br");

        placeButton(0, 10, "deadGrassBa");
        placeButton(1, 10, "flowerVeryDeadCyanBr");
        placeButton(2, 10, "veryDeadDandelionsBr");
        placeButton(3, 10, "deadLeavesBr");

        placeButton(0, 11, "veryDeadLilyPad0Br");
        placeButton(1, 11, "veryDeadLilyPad1Br");
        placeButton(2, 11, "veryDeadLilyPad2Br");
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
