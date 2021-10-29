package main.misc;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static main.misc.Utilities.*;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;

public class CollisionBox {

    private final PVector OFFSET;
    private final PVector SIZE;

    private final PApplet P;

    /**
     * @param offset top left of collision box RELATIVE TO OBJECT POSITION
     * @param size length and width of collision box
     */
    public CollisionBox(PApplet p, PVector offset, PVector size) {
        P = p;
        OFFSET = offset;
        SIZE = size;
    }

    public void display(PVector position) {
        P.noFill();
        P.stroke(Color.MAGENTA.getRGB());
        P.rectMode(PConstants.CORNER);
        P.rect(position.x + OFFSET.x, position.y + OFFSET.y, SIZE.x, SIZE.y);
    }

    public float getRightEdge() {
        return OFFSET.x + SIZE.x;
    }

    public float getLeftEdge() {
        return OFFSET.x;
    }

    public float getBottomEdge() {
        return OFFSET.y + SIZE.y;
    }

    public float getTopEdge() {
        return OFFSET.y;
    }

    /**
     * Buffer should be positive.
     * Left to right, top to bottom.
     */
    public IntVector[] getCornerGridPositions(PVector position, float buffer) {
        int ceilBuffer = (int) Math.ceil(buffer);
        return new IntVector[]{
          worldPositionToGridPosition(
            new PVector(position.x + OFFSET.x + ceilBuffer, position.y + OFFSET.y + ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + OFFSET.x + SIZE.x - ceilBuffer, position.y + OFFSET.y + ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + OFFSET.x + ceilBuffer, position.y + OFFSET.y + SIZE.y - ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + OFFSET.x + SIZE.x - ceilBuffer, position.y + OFFSET.y + SIZE.y - ceilBuffer
            ))
        };
    }

    /**
     * Check if a point is inside the box
     * @param position position of box
     * @param point point to check
     * @return if inside
     */
    public boolean pointIsInsideBox(PVector position, PVector point) {
        boolean left = point.x > position.x + getLeftEdge();
        boolean right = point.x < position.x + getRightEdge();
        boolean top = point.y > position.y + getTopEdge();
        boolean bottom = point.y < position.y + getBottomEdge();
        return left && right && top && bottom;
    }
}
