package main.misc;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.spritesH;
import static main.Main.tiles;

public class Tile {

    private PApplet p;

    public PVector position;
    public int id;
    public Tower tower;
    public PImage bgA;
    public PImage bgB;
    public PImage bgC;
    public PImage[] bgAEdges;
    private String bgAname;

    public Tile(PApplet p, PVector position, int id) {
        this.p = p;
        
        this.position = position;
        this.id = id;
        bgAEdges = new PImage[4];
    }

    public void main() {
        if (tower != null) tower.main();
    }

    public void display() {
        if (bgA != null) p.image(bgA,position.x,position.y);
        if (bgB != null) p.image(bgB,position.x,position.y);
        if (bgC != null) p.image(bgC,position.x,position.y);
        int x = (int)(position.x / 50);
        int y = (int)(position.y / 50);
        if (y != 0) {
            Tile tile = tiles.get(x, y-1);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[0] != null) p.image(tile.bgAEdges[0],position.x,position.y);
        }
        if (x != 0) {
            Tile tile = tiles.get(x-1, y);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[3] != null) p.image(tile.bgAEdges[3],position.x,position.y);
        }
        if (y != 18) {
            Tile tile = tiles.get(x, y+1);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[2] != null) p.image(tile.bgAEdges[2],position.x,position.y);
        }
        if (x != 18) {
            Tile tile = tiles.get(x+1, y);
            if (bgAname != null && !bgAname.equals(tile.bgAname) && tile.bgAEdges[1] != null) p.image(tile.bgAEdges[1],position.x,position.y);
        }
    }

    public void setBgA(String s) {
        s = s.replace("BGA_TL","");
        bgAname = s;
        bgA = spritesH.get(s+"BGA_TL");
        bgAEdges[0] = spritesH.get(s+"BGA_T_TL");
        bgAEdges[1] = spritesH.get(s+"BGA_R_TL");
        bgAEdges[2] = spritesH.get(s+"BGA_B_TL");
        bgAEdges[3] = spritesH.get(s+"BGA_L_TL");
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
            TileDSItem[] newItems = new TileDSItem[items.length+1];
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
