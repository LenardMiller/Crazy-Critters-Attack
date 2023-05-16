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

        placeButton(0, 5, "mushroomsBLDDe");
        placeButton(1, 5, "mushroomsTRDDe");
        placeButton(2, 5, "mushroomsTDe");

        placeButton(0, 6, "mushroomsPillarADe");
        placeButton(1, 6, "mushroomsPillarBDe");
        placeButton(2, 6, "mushroomsPillarCDe");
        placeButton(3, 6, "mushroomsPillarDDe");

        placeButton(0, 7, "lichenOrangeDe");
        placeButton(1, 7, "lichenBlueDe");
        placeButton(2, 7, "lichenWhiteDe");
        placeButton(3, 7, "lichenGreenDe");

        placeButton(0, 8, "caveWallDeadMossyTOb");
        placeButton(1, 8, "caveWallDeadMossyTLOb");
        placeButton(2, 8, "caveWallDeadMossyTLDOb");

        placeButton(0, 9, "caveWallDeadMossyBRDOb");
        placeButton(1, 9, "caveWallDeadMossyROb");
        placeButton(2, 9, "caveWallDeadMossyTOb");
        placeButton(3, 9, "caveWallDeadMossyTROb");

        placeButton(0, 10, "caveWallDeadMossyBLDOb");
        placeButton(1, 10, "caveWallDeadMossyBOb");
        placeButton(2, 10, "caveWallDeadMossyBROb");

        placeButton(0, 11, "caveWallDeadShroomyTOb");
        placeButton(1, 11, "caveWallDeadShroomyT2Ob");
        placeButton(2, 11, "caveWallDeadShroomyBLDOb");
        placeButton(3, 11, "caveWallDeadShroomyTRDOb");

        placeButton(0, 12, "caveWallDeadShroomyPillarOb");
        placeButton(1, 12, "caveWallDeadMossyPillarOb");

        placeButton(0, 13, "deadMushroomsBLDDe");
        placeButton(1, 13, "deadMushroomsTRDDe");
        placeButton(2, 13, "deadMushroomsTDe");

        placeButton(0, 14, "deadMushroomsPillarADe");
        placeButton(1, 14, "deadMushroomsPillarBDe");
        placeButton(2, 14, "deadMushroomsPillarCDe");
        placeButton(3, 14, "deadMushroomsPillarDDe");

        placeButton(0, 15, "deadLichenOrangeDe");
        placeButton(1, 15, "deadLichenBlueDe");
        placeButton(2, 15, "deadLichenWhiteDe");
        placeButton(3, 15, "deadLichenGreenDe");

        placeButton(0, 17, "Na");
        placeButton(1, 17, "Ma");
        placeButton(2, 17, "invisibleOb");
    }

    private void placeButton(int x, int y, String type) {
        tileSelectButtons.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type, true));
    }
}
