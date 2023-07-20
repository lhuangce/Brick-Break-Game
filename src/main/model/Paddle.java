package model;

/*
 * Represents the paddle.
 */
public class Paddle {
    protected static int SIZE_X = 26;
    protected static int SIZE_Y = 10;
    protected static int X_POS = BrickBreakGame.WIDTH / 2;
    protected static int Y_POS = BrickBreakGame.HEIGHT - 40;
    protected static int STEP_SIZE = 5;

    private int xcoord;

    // EFFECTS: creates paddle with position (X_POS, Y_POS)
    public Paddle() {
        this.xcoord = X_POS;
    }

    // MODIFIES: this
    // EFFECTS: moves paddle left
    public void moveLeft() {
        xcoord -= STEP_SIZE;
    }

    // MODIFIES: this
    // EFFECTS: moves paddle right
    public void moveRight() {
        xcoord += STEP_SIZE;
    }

    public int getX() {
        return xcoord;
    }

    public int getyPos() {
        return Y_POS;
    }
}