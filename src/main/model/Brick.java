package model;

import org.json.JSONObject;

import java.awt.*;

/**
 * Represents a brick.
 */
public class Brick {
    public static final Color COLOR = new Color(240, 50, 50);
    public static final int SIZE_X = 80;
    public static final int SIZE_Y = 50;

    private final int xcoord;
    private final int ycoord;

    // EFFECTS: creates brick at position (xcoord, ycoord)
    public Brick(int x, int y) {
        xcoord = x;
        ycoord = y;
    }

    // EFFECTS: saves x and y coordinates to a JSON object
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
