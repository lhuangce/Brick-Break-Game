package model;

/**
 * Represents a brick.
 */
public class Brick {
    protected static int SIZE_X = 80;
    protected static int SIZE_Y = 50;

    private final int xcoord;
    private final int ycoord;

    public Brick(int x, int y) {
        xcoord = x;
        ycoord = y;
    }

    public int getX() {
        return xcoord;
    }

    public int getY() {
        return ycoord;
    }
}
