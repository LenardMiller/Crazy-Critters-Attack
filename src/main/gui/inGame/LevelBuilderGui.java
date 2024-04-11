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
        placeButton(0, 0, "deadTreeTLOb");
        placeButton(1, 0, "deadTreeTROb");
        placeButton(2, 0, "smallDeadTreeOb");
        placeButton(3, 0, "veryDeadDandelionsBr");

        placeButton(0, 1, "deadTreeBLOb");
        placeButton(1, 1, "deadTreeBROb");
        placeButton(2, 1, "flowerVeryDeadCyanBr");
        placeButton(3, 1, "rockBr");

        placeButton(0, 2, "deadGrassCornerBR_De");
        placeButton(1, 2, "deadGrassCornerBL_De");
        placeButton(2, 2, "deadGrassBa");
        placeButton(3, 2, "smallRockBr");

        placeButton(0, 3, "deadGrassCornerTR_De");
        placeButton(1, 3, "deadGrassCornerTL_De");
        placeButton(2, 3, "dirtBa");

        placeButton(0, 17, "Na");
        placeButton(1, 17, "Ma");
        placeButton(2, 17, "invisibleOb");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
