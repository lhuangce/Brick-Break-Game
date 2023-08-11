package model;

import java.awt.*;

/**
 * Represents the paddle.
 */
public class Paddle {
    public static final int SIZE_X = 52;
    public static final int SIZE_Y = 10;
    public static final int X_POS = BrickBreakGame.WIDTH / 2;
    public static final int Y_POS = BrickBreakGame.HEIGHT - 40;
    public static final Color COLOR = new Color(50, 50, 255);

    protected static final int STEP_SIZE = 6;

    private int xcoord;

    // EFFECTS: creates paddle with position (X_POS, Y_POS); DEFAULT CONSTRUCTOR
    public Paddle() {
        this.xcoord = X_POS;
    }

    // EFFECTS: creates paddle with position (x, Y_POS)
    public Paddle(int x) {
        this.xcoord = x;
    }

    // MODIFIES: this
    // EFFECTS: moves paddle left
    public void moveLeft() {
        if (xcoord > SIZE_X / 2) {
            xcoord -= STEP_SIZE;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves paddle right
    public void moveRight() {
        if (xcoord < BrickBreakGame.WIDTH - SIZE_X / 2) {
            xcoord += STEP_SIZE;
        }
    }

    public int getX() {
        return xcoord;
    }
}
