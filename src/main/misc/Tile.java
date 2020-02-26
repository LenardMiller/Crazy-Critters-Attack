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
    public PImage bgW;
    public PImage bgB;
    public PImage bgC;
    public PImage[] bgAEdges;
    public PImage[] bgWEdges;
    private String bgAname;
    public String bgWname;

    public Tile(PApplet p, PVector position, int id) {
        this.p = p;

        this.position = position;
        this.id = id;
        bgAEdges = new PImage[4];
        bgWEdges = new PImage[4];
    }

    public void main() {
        if (tower != null) tower.main();
    }

    public void display() {
        if (bgA != null) p.image(bgA, position.x, position.y);
        tileBgA();
        if (bgB != null) p.image(bgB, position.x, position.y);
        if (bgWEdges != null) tileBgWEdges();
        if (bgWname != null) {
            if (!bgWname.equals("woodWall") && !bgWname.equals("stoneWall")) tileBgWICorners();
            else p.image(bgW, position.x, position.y);
        }
        tileBgWOCorners();
        if (bgC != null) p.image(bgC, position.x, position.y);
    }

    private void tileBgA() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[0] != null)
                p.image(tile.bgAEdges[0], position.x, position.y);
        }
        if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[3] != null)
                p.image(tile.bgAEdges[3], position.x, position.y);
        }
        if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[2] != null)
                p.image(tile.bgAEdges[2], position.x, position.y);
        }
        if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[1] != null)
                p.image(tile.bgAEdges[1], position.x, position.y);
        }
    }

    private void tileBgWEdges() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (debug) p.tint(0,255,0);
        if (y != 0) {
            Tile tile = tiles.get(x, y - 1);
            if (isConnected(0, tile)) p.image(tile.bgWEdges[0], position.x, position.y);
        } if (x != 0) {
            Tile tile = tiles.get(x - 1, y);
            if (isConnected(3, tile)) p.image(tile.bgWEdges[3], position.x, position.y);
        } if (y != 18) {
            Tile tile = tiles.get(x, y + 1);
            if (isConnected(2, tile)) p.image(tile.bgWEdges[2], position.x, position.y);
        } if (x != 18) {
            Tile tile = tiles.get(x + 1, y);
            if (isConnected(1, tile)) p.image(tile.bgWEdges[1], position.x, position.y);
        }
        if (debug) p.tint(255);
    }

    private void tileBgWICorners() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        boolean drawMain = true;
        if (debug) p.tint(255,0,0);
        if (x != 18 && y != 0) {
            if (doubleDiagonalIn(x, y, 1, -1, bgWname)) {
                p.image(spritesH.get(bgWname + "BGW_TRI_TL"), position.x, position.y);
                drawMain = false;
            }
        } if (x != 18 && y != 18) {
            if (doubleDiagonalIn(x, y, 1, 1, bgWname)) {
                p.image(spritesH.get(bgWname + "BGW_BRI_TL"), position.x, position.y);
                drawMain = false;
            }
        } if (x != 0 && y != 18) {
            if (doubleDiagonalIn(x, y, -1, 1, bgWname)) {
                p.image(spritesH.get(bgWname + "BGW_BLI_TL"), position.x, position.y);
                drawMain = false;
            }
        } if (x != 0 && y != 0) {
            if (doubleDiagonalIn(x, y, -1, -1, bgWname)) {
                p.image(spritesH.get(bgWname + "BGW_TLI_TL"), position.x, position.y);
                drawMain = false;
            }
        }
        String s = checkMissing(x,y,bgWname);
        if (s != null) {
            p.image(spritesH.get(bgWname + "BGW_" + s + "_TL"), position.x, position.y);
            drawMain = false;
        }
        if (countTouchingVN(x,y) > 2) drawMain = true;
        if (debug) p.tint(255);
        if (drawMain) p.image(bgW, position.x, position.y);
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
        if (tileX.bgWname != null && tileX.bgWname.equals(name)) return true;
        if (tileY.bgWname != null && tileY.bgWname.equals(name)) return true;
        return tileM.bgWname != null && tileM.bgWname.equals(name);
    }

    private void tileBgWOCorners() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        if (debug) p.tint(0,0,255);
        if (x != 18 && y != 0) {
            String n = doubleDiagonalOut(x,y,1,-1);
            if (n != null) p.image(spritesH.get(n + "BGW_TRO_TL"), position.x, position.y);
        } if (x != 18 && y != 18) {
            String n = doubleDiagonalOut(x,y,1,1);
            if (n != null) p.image(spritesH.get(n + "BGW_BRO_TL"), position.x, position.y);
        } if (x != 0 && y != 18) {
            String n = doubleDiagonalOut(x,y,-1,1);
            if (n != null) p.image(spritesH.get(n + "BGW_BLO_TL"), position.x, position.y);
        } if (x != 0 && y != 0) {
            String n = doubleDiagonalOut(x,y,-1,-1);
            if (n != null) p.image(spritesH.get(n + "BGW_TLO_TL"), position.x, position.y);
        }
        if (debug) p.tint(255);
    }

    private int countTouchingVN(int x, int y) {
        int r = 0;
        if (y > 0) {
            Tile tile = tiles.get(x,y-1);
            if (tile.bgWname != null && tile.bgWname.equals(bgWname)) r++;
        } if (x > 0) {
            Tile tile = tiles.get(x-1,y);
            if (tile.bgWname != null && tile.bgWname.equals(bgWname)) r++;
        } if (x < 17) {
            Tile tile = tiles.get(x+1,y);
            if (tile.bgWname != null && tile.bgWname.equals(bgWname)) r++;
        } if (y < 17) {
            Tile tile = tiles.get(x,y+1);
            if (tile.bgWname != null && tile.bgWname.equals(bgWname)) r++;
        }
        return r;
    }

    private String doubleDiagonalOut(int x, int y, int dx, int dy) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        if (tileA.bgWname == null || tileA.bgWname.equals("woodWall") || tileA.bgWname.equals("stoneWall")) return null;
        if (tileB.bgWname == null || tileB.bgWname.equals("woodWall") || tileB.bgWname.equals("stoneWall")) return null;
        if (!tileA.bgWname.equals(tileB.bgWname)) return null;
        else return tileA.bgWname;
    }

    private boolean diagonalIn(int x, int y, int dx, int dy, String name) {
        if ((x + dx > -1 && x + dx > 19 && y + dy > -1 && y + dy < 19)) return false;
        Tile tile = tiles.get(x + dx, y + dy);
        if (tile.bgWname == null) return false;
        return tile.bgWname.equals(name);
    }

    private boolean doubleDiagonalIn(int x, int y, int dx, int dy, String name) {
        Tile tileA = tiles.get(x + dx, y);
        Tile tileB = tiles.get(x, y + dy);
        Tile tileC = null;
        int cx = x - dx;
        int cy = y - dy;
        if (cx <= 18 && cx >= 0 && cy <= 18 && cy >= 0) tileC = tiles.get(cx, cy);
        if (tileA.bgWname == null) return false;
        if (tileB.bgWname == null) return false;
        if (!tileA.bgWname.equals(name)) return false;
        if (tileC != null && tileC.bgWname != null && tileC.bgWname.equals(tileA.bgWname)) return false;
        return tileB.bgWname.equals(name);
    }

    private boolean isConnected(int i, Tile tile) {
        if (bgWname != null && tile.bgWname != null) {
            if (bgWname.equals("woodWall") || tile.bgWname.equals("woodWall")) {
                return bgWname.equals(tile.bgWname) && tile.bgWEdges[i] != null;
            } else return tile.bgWEdges[i] != null;
        } else return false;
    }

    public void setBgA(String s) {
        s = s.replace("BGA_TL", "");
        bgAname = s;
        bgA = spritesH.get(s + "BGA_TL");
        bgAEdges[0] = spritesH.get(s + "BGA_T_TL");
        bgAEdges[1] = spritesH.get(s + "BGA_R_TL");
        bgAEdges[2] = spritesH.get(s + "BGA_B_TL");
        bgAEdges[3] = spritesH.get(s + "BGA_L_TL");
    }

    public void setBgW(String s) {
        bgWEdges = new PImage[4];
        if (s == null) {
            bgW = null;
            bgWname = null;
        } else {
            s = s.replace("BGW_TL", "");
            s = s.replace("ultimate", "titanium");
            bgWname = s;
            if (spritesH.get(s + "BGW_TL") != bgW) {
                bgW = spritesH.get(s + "BGW_TL");
                if (s.contains("woodWall") || s.contains("stoneWall")) {
                    bgWEdges[0] = spritesH.get(s + "BGW_T_TL");
                    bgWEdges[1] = spritesH.get(s + "BGW_R_TL");
                    bgWEdges[2] = spritesH.get(s + "BGW_B_TL");
                    bgWEdges[3] = spritesH.get(s + "BGW_L_TL");
                }
            } else {
                bgW = null;
                bgWname = null;
            }
        }
    }

    public void setBgB(String s) {
        if (spritesH.get(s) != bgB) bgB = spritesH.get(s);
        else bgB = null;
    }

    public void setBgC(String s) {
        if (spritesH.get(s) != bgC) bgC = spritesH.get(s);
        else bgC = null;
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
