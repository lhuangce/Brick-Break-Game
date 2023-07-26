package model;

import org.json.JSONObject;

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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("brickX", xcoord);
        json.put("brickY", ycoord);
        return json;
    }

    public int getX() {
        return xcoord;
    }

    public int getY() {
        return ycoord;
    }
}
