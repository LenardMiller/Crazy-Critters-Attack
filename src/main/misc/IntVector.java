package main.misc;

import processing.core.PVector;

public class IntVector {

    public int x, y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector(int[] arr) {
        this(arr[0], arr[1]);
        if (arr.length > 2) System.out.println("IntVector must have a length of two");
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }

    public PVector toPVector() {
        return new PVector(x, y);
    }

    public IntVector sub(int amount) {
        x -= amount;
        y -= amount;
        return this;
    }

    public static IntVector sub(IntVector iv, int amount) {
        return new IntVector(iv.x - amount, iv.y - amount);
    }

    public IntVector add(int amount) {
        x += amount;
        y += amount;
        return this;
    }
}
