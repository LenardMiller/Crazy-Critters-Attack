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
        placeButton(0, 0, "pinkSnowBa");
        placeButton(1, 0, "snowBa");
        placeButton(2, 0, "snowGhostOb");
        placeButton(3, 0, "evergreenOb");

        placeButton(0, 1, "snowCornersBRDe");
        placeButton(1, 1, "snowCornersBLDe");
        placeButton(2, 1, "dirtBa");
        placeButton(3, 1, "stoneBa");

        placeButton(0, 2, "snowCornersTRDe");
        placeButton(1, 2, "snowCornersTLDe");
        placeButton(2, 2, "deadOrangeFlowersBr");
        placeButton(3, 2, "deadBlueFlowersBr");

        placeButton(0, 3, "stoneCornersBRDe");
        placeButton(1, 3, "stoneCornersBLDe");
        placeButton(2, 3, "smallRockBr");
        placeButton(3, 3, "rockBr");

        placeButton(0, 4, "stoneCornersTRDe");
        placeButton(1, 4, "stoneCornersTLDe");
        placeButton(2, 4, "waterBa");
        placeButton(3, 4, "everbrownOb");

        placeButton(0, 5, "dirtCornersBRDe");
        placeButton(1, 5, "dirtCornersBLDe");
        placeButton(2, 5, "sludgeBa");

        placeButton(0, 6, "dirtCornersTRDe");
        placeButton(1, 6, "dirtCornersTLDe");

        placeButton(0, 7, "pinkTreeOb");
        placeButton(1, 7, "whiteTreeOb");
        placeButton(2, 7, "yellowTreeOb");
        placeButton(3, 7, "smallTreeOb");

        placeButton(0, 8, "yellowPinkTreeOb");
        placeButton(1, 8, "yellowWhiteTreeOb");
        placeButton(2, 8, "yellowYellowTreeOb");
        placeButton(3, 8, "smallYellowTreeOb");

        placeButton(0, 17, "Na");
        placeButton(1, 17, "Ma");
        placeButton(2, 17, "invisibleOb");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
