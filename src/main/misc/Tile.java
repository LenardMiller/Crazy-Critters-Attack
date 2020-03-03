package main.misc;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Tile {

    private PApplet p;

    public PVector position;
    public int id;
    public Tower tower;
    public PImage bgA;
    public String bgAName;
    public PImage bgW;
    public PImage bgB;
    public String bgBName;
    public PImage bgC;
    public String bgCName;
    public PImage obstacle;
    public String obstacleName;
    private int obstacleShadowLength;
    public PImage[] bgAEdges;
    public PImage[] bgWEdges;
    private PImage[] bgWOCorners;
    private String[] bgWOCornerNames;
    private PImage[] bgWICorners;
    public String bgWName;
    private boolean drawMain;

    public Tile(PApplet p, PVector position, int id) {
        this.p = p;

        this.position = position;
        this.id = id;
        obstacleShadowLength = 0;
        bgAEdges = new PImage[4];
        bgWEdges = new PImage[4];
        bgWOCorners = new PImage[4];
        bgWOCornerNames = new String[4];
        bgWICorners = new PImage[4];
    }

    public void main() {
        if (tower != null) tower.main();
    }

    public void displayA() {
        if (bgA != null) p.image(bgA, position.x, position.y);
        tileBgA();
        if (bgB != null) p.image(bgB, position.x, position.y);
        if (bgWEdges != null) connectBgWEdges();
        if (bgWName != null) {
            if (!isConcrete(bgWName)) {
                if (debug) p.tint(255,0,255);
                p.image(bgW, position.x, position.y);
                if (debug) p.tint(255);
            }
        }
//        if (bgC != null) p.image(bgC, position.x, position.y);
    }

    public void displayB() {
        if (bgC != null) p.image(bgC, position.x, position.y);
        if (obstacle != null) {
            p.tint(0, 60);
            p.image(obstacle, position.x + obstacleShadowLength, position.y + obstacleShadowLength);
            p.tint(255);
        }
    }

    public void displayC() {
        if (obstacle != null) p.image(obstacle, position.x, position.y);
    }

    private void tileBgA() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (bgAName != null && !bgAName.equals(tile.bgAName) && tile.bgAEdges[0] != null)
                p.image(tile.bgAEdges[0], position.x, position.y);
        }
        if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (bgAName != null && !bgAName.equals(tile.bgAName) && tile.bgAEdges[3] != null)
                p.image(tile.bgAEdges[3], position.x, position.y);
        }
        if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (bgAName != null && !bgAName.equals(tile.bgAName) && tile.bgAEdges[2] != null)
                p.image(tile.bgAEdges[2], position.x, position.y);
        }
        if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (bgAName != null && !bgAName.equals(tile.bgAName) && tile.bgAEdges[1] != null)
                p.image(tile.bgAEdges[1], position.x, position.y);
        }
    }

    private void connectBgWEdges() {
        if (debug) p.tint(0,255,0);
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (isConnected(0, tile)) p.image(tile.bgWEdges[0], position.x, position.y);
            if (bgWEdges[0] != null && isConcrete(tile.bgWName)) p.image(bgWEdges[0], position.x, position.y);
        } if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (isConnected(3, tile)) p.image(tile.bgWEdges[3], position.x, position.y);
            if (bgWEdges[3] != null && isConcrete(tile.bgWName)) p.image(bgWEdges[3], position.x, position.y);
        } if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (isConnected(2, tile)) p.image(tile.bgWEdges[2], position.x, position.y);
            if (bgWEdges[2] != null && isConcrete(tile.bgWName)) p.image(bgWEdges[2], position.x, position.y);
        } if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (isConnected(1, tile)) p.image(tile.bgWEdges[1], position.x, position.y);
            if (bgWEdges[1] != null && isConcrete(tile.bgWName)) p.image(bgWEdges[1], position.x, position.y);
        }
        if (debug) p.tint(255);
    }

    private boolean isConnected(int i, Tile tile) {
        if (bgWName != null && tile.bgWName != null) {
            if (!isConcrete(bgWName)) {
                return bgWName.equals(tile.bgWName) && tile.bgWEdges[i] != null;
            } else return tile.bgWEdges[i] != null;
        } else return false;
    }

    public void connectBgWICorners() {
        bgWICorners = new PImage[4];
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        drawMain = true;
        if (x != 18 && y != 0) {
            if (doubleDiagonalIn(x, y, 1, -1, bgWName)) { //tri
                bgWICorners[0] = spritesH.get(bgWName + "BGW_TRI_TL");
                drawMain = false;
            }
        } if (x != 18 && y != 18) {
            if (doubleDiagonalIn(x, y, 1, 1, bgWName)) { //bri
                bgWICorners[1] = spritesH.get(bgWName + "BGW_BRI_TL");
                drawMain = false;
            }
        } if (x != 0 && y != 18) {
            if (doubleDiagonalIn(x, y, -1, 1, bgWName)) { //bli
                bgWICorners[2] = spritesH.get(bgWName + "BGW_BLI_TL");
                drawMain = false;
            }
        } if (x != 0 && y != 0) {
            if (doubleDiagonalIn(x, y, -1, -1, bgWName)) { //tli
                bgWICorners[3] = spritesH.get(bgWName + "BGW_TLI_TL");
                drawMain = false;
            }
        }
        String s = checkMissing(x,y, bgWName);
        if (s != null) { //s
            int i = 0;
            if (s.equals("BRI")) i = 1;
            if (s.equals("BLI")) i = 2;
            if (s.equals("TLI")) i = 3;
            bgWICorners[i] = spritesH.get(bgWName + "BGW_" + s + "_TL");
            drawMain = false;
        }
        if (countTouchingVN(x,y) > 2) drawMain = true;
    }

    public void drawBgWICorners() {
        if (isConcrete(bgWName)) {
            if (drawMain) p.image(bgW, position.x, position.y);
            for (int i = 0; i < 4; i++) {
                if (bgWICorners[i] != null) {
                    if (debug) p.tint(255,0,0);
                    p.image(bgWICorners[i], position.x, position.y);
                    if (debug) p.tint(255);
                }
            }
        }
    }

    public void drawBgWOCorners(String name) {
        for (int i = 0; i < 4; i++) {
            if (bgWOCorners[i] != null) {
                if (bgWOCornerNames[i].equals(name)) {
                    if (debug) p.tint(0,0,255);
                    p.image(bgWOCorners[i], position.x, position.y);
                    if (debug) p.tint(255);
                }
            }
        }
    }

    public void connectBgWOCorners() {
        bgWOCorners = new PImage[4];
        bgWOCornerNames = new String[4];
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (debug) p.tint(0,0,255);
        if (x != 18 && y != 0) { //tro
            String n = doubleDiagonalOut(x,y,1,-1);
            if (n != null) {
                bgWOCorners[0] = spritesH.get(n + "BGW_TRO_TL");
                bgWOCornerNames[0] = n;
            }
        } if (x != 18 && y != 18) { //bro
            String n = doubleDiagonalOut(x,y,1,1);
            if (n != null) {
                bgWOCorners[1] = spritesH.get(n + "BGW_BRO_TL");
                bgWOCornerNames[1] = n;
            }
        } if (x != 0 && y != 18) { //blo
            String n = doubleDiagonalOut(x,y,-1,1);
            if (n != null) {
                bgWOCorners[2] = spritesH.get(n + "BGW_BLO_TL");
                bgWOCornerNames[2] = n;
            }
        } if (x != 0 && y != 0) { //tlo
            String n = doubleDiagonalOut(x,y,-1,-1);
            if (n != null) {
                bgWOCorners[3] = spritesH.get(n + "BGW_TLO_TL");
                bgWOCornerNames[3] = n;
            }
        }
        if (debug) p.tint(255);
    }

    private String checkMissing(int x, int y, String name) {
        int numAccounted = 0;
        boolean tr;
        boolean br;
        boolean bl;
        boolean tl;
        if (!(x > 0 && x < 18 && y > 0 && y < 18)) return null;
        bl = account(x,y,1,-1,name);
        tl = account(x,y,1,1,name);
        tr = account(x,y,-1,1,name);
        br = account(x,y,-1,-1,name);
        if (bl) numAccounted++;
        if (tl) numAccounted++;
        if (tr) numAccounted++;
        if (br) numAccounted++;
        if (numAccounted < 3 || numAccounted == 4) return null;
        if (!tr) return "TRI";
        if (!br) return "BRI";
        if (!bl) return "BLI";
        return "TLI";
    }

    private boolean account(int x, int y, int dx, int dy, String name) {
        Tile tileM = tiles.get(x+dx,y+dy);
        Tile tileX = tiles.get(x+dx,y);
        Tile tileY = tiles.get(x,y+dy);
        if (tileX.bgWName != null && tileX.bgWName.equals(name)) return true;
        if (tileY.bgWName != null && tileY.bgWName.equals(name)) return true;
        return tileM.bgWName != null && tileM.bgWName.equals(name);
    }

    private int countTouchingVN(int x, int y) {
        int r = 0;
        if (y > 0) {
            Tile tile = tiles.get(x,y-1);
            if (tile.bgWName != null && tile.bgWName.equals(bgWName)) r++;
        } if (x > 0) {
            Tile tile = tiles.get(x-1,y);
            if (tile.bgWName != null && tile.bgWName.equals(bgWName)) r++;
        } if (x < 17) {
            Tile tile = tiles.get(x+1,y);
            if (tile.bgWName != null && tile.bgWName.equals(bgWName)) r++;
        } if (y < 17) {
            Tile tile = tiles.get(x,y+1);
            if (tile.bgWName != null && tile.bgWName.equals(bgWName)) r++;
        }
        return r;
    }

    private String doubleDiagonalOut(int x, int y, int dx, int dy) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        if (!isConcrete(tileA.bgWName)) return null;
        if (!isConcrete(tileA.bgWName)) return null;
        if (!tileA.bgWName.equals(tileB.bgWName)) return null;
        else return tileA.bgWName;
    }

    private boolean doubleDiagonalIn(int x, int y, int dx, int dy, String name) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        Tile tileC = null;
        int cx = x - dx;
        int cy = y - dy;
        if (cx <= 18 && cx >= 0 && cy <= 18 && cy >= 0) tileC = tiles.get(cx, cy);
        if (tileA.bgWName == null) return false;
        if (tileB.bgWName == null) return false;
        if (!tileA.bgWName.equals(name)) return false;
        if (tileC != null && tileC.bgWName != null && tileC.bgWName.equals(tileA.bgWName)) return false;
        return tileB.bgWName.equals(name);
    }

    private boolean isConcrete(String name) {
        if (name == null) return false;
        boolean m = name.equals("metalWall");
        boolean c = name.equals("crystalWall");
        boolean t = name.equals("titaniumWall");
        return m || c || t;
    }

    public void setBgA(String name) {
        name = name.replace("BGA_TL", "");
        bgAName = name;
        bgA = spritesH.get(name + "BGA_TL");
        bgAEdges[0] = spritesH.get(name + "BGA_T_TL");
        bgAEdges[1] = spritesH.get(name + "BGA_R_TL");
        bgAEdges[2] = spritesH.get(name + "BGA_B_TL");
        bgAEdges[3] = spritesH.get(name + "BGA_L_TL");
    }

    public void setBgW(String name) {
        bgWEdges = new PImage[4];
        if (name == null) {
            bgW = null;
            bgWName = null;
        } else {
            if (bgCName != null) {
                if (!bgCName.contains("Debris")) {
                    bgC = null;
                    bgCName = null;
                }
            }
            name = name.replace("BGW_TL", "");
            name = name.replace("ultimate", "titanium");
            bgWName = name;
            if (spritesH.get(name + "BGW_TL") != bgW) {
                bgW = spritesH.get(name + "BGW_TL");
                if (name.contains("woodWall") || name.contains("stoneWall")) {
                    bgWEdges[0] = spritesH.get(name + "BGW_T_TL");
                    bgWEdges[1] = spritesH.get(name + "BGW_R_TL");
                    bgWEdges[2] = spritesH.get(name + "BGW_B_TL");
                    bgWEdges[3] = spritesH.get(name + "BGW_L_TL");
                }
            } else {
                bgW = null;
                bgWName = null;
            }
        }
    }

    public void setBgB(String name) {
        if (spritesH.get(name) != bgB) {
            bgB = spritesH.get(name);
            bgBName = name;
        } else {
            bgB = null;
            bgBName = null;
        }
    }

    public void setBgC(String name) {
        if (name != null) name = name.replace("ultimate","titanium");
        if (spritesH.get(name) != bgC) {
            bgC = spritesH.get(name);
            bgCName = name;
        } else {
            bgC = null;
            bgCName = null;
        }
    }

    public void setObstacle(String name) {
        if (spritesH.get(name) != obstacle) {
            obstacle = spritesH.get(name);
            obstacleName = name;
            if (name.contains("smallTree")) obstacleShadowLength = 3;
            if (containsCorners(name,"tree")) obstacleShadowLength = 8;
        } else {
            obstacle = null;
            obstacleName = null;
        }
    }

    private boolean containsCorners(String name, String subName) {
        boolean bl = name.contains(subName + "BL");
        boolean br = name.contains(subName + "BR");
        boolean tl = name.contains(subName + "TL");
        boolean tr = name.contains(subName + "TR");
        return bl || br || tl || tr;
    }

    public static class TileDS {

        public TileDSItem[] items;

        public TileDS() {
            items = new TileDSItem[0];
        }

        public Tile get(int id) {
            return items[id].tile;
        }

        public Tile get(int x, int y) {
            Tile r = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) r = item.tile;
            return r;
        }

        public void add(Tile tile, int x, int y) {
            TileDSItem[] newItems = new TileDSItem[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new TileDSItem(tile, x, y);
            items = newItems;
        }

        public int size() {
            return items.length;
        }

        public void remove(int id) {
            TileDSItem removeItem = items[id];
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(int x, int y) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(Tile tile) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.tile == tile) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public static class TileDSItem {

            public Tile tile;
            public int x;
            public int y;

            public TileDSItem(Tile tile, int x, int y) {
                this.tile = tile;
                this.x = x;
                this.y = y;
            }
        }
    }
}
