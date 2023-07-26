package model;

import java.awt.*;

/**
 * Represents a ball; code based on PaddleBall lab example
 */

public class Ball {
    protected static int SIZE = 20;
    //public static final Color COLOR = new Color(255, 150, 28);

    private int xcoord;
    private int ycoord;
    private int dx;
    private int dy;

    // EFFECTS: creates ball at position (x, y), and x and y speeds of 2; DEFAULT CONSTRUCTOR
    public Ball(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
        dx = 2;
        dy = 2;
    }

    // EFFECTS: creates ball at position (x, y), moving at speeds dx and dy; for loading save state
    public Ball(int x, int y, int dx, int dy) {
        this.xcoord = x;
        this.ycoord = y;
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: changes ball position by speed units
    public void move() {
        xcoord += dx;
        ycoord += dy;

        handleBoundary();
    }

    // MODIFIES: this
    // EFFECTS: bounces ball off horizontal surface of object by changing direction of movement
    public void bounceOffHorizontal() {
        dy *= -1;
    }

    // MODIFIES: this
    // EFFECTS: bounces ball off vertical surface of object by changing direction of movement
    public void bounceOffVertical() {
        dx *= -1;
    }

    // EFFECTS:  returns true if this ball has collided with paddle,
    //           false otherwise
    public boolean collideWithPaddle(Paddle p) {
        Rectangle ballBoundingRectangle = new Rectangle(getX() - SIZE / 2, getY() - SIZE / 2, SIZE, SIZE);
        Rectangle paddleBoundingRectangle = new Rectangle(p.getX() - Paddle.SIZE_X / 2,
                Paddle.Y_POS - Paddle.SIZE_Y / 2, Paddle.SIZE_X, Paddle.SIZE_Y);
        return ballBoundingRectangle.intersects(paddleBoundingRectangle);
    }

    // EFFECTS:  returns true if this ball has collided with brick top or bottom
    //           false otherwise
    public boolean collideWithBrickWidth(Brick b) {
        Rectangle ballBoundingRectangle = new Rectangle(getX() - SIZE / 2, getY() - SIZE / 2, SIZE, SIZE);
        Rectangle brickTop = new Rectangle(b.getX() - Brick.SIZE_X / 2,
                b.getY() - Brick.SIZE_Y / 2, Brick.SIZE_X, 1);
        Rectangle brickBottom = new Rectangle(b.getX() - Brick.SIZE_X / 2,
                b.getY() + Brick.SIZE_Y / 2, Brick.SIZE_X, 1);
        return ballBoundingRectangle.intersects(brickTop) || ballBoundingRectangle.intersects(brickBottom);
    }

    // EFFECTS:  returns true if this ball has collided with brick sides,
    //           false otherwise
    public boolean collideWithBrickHeight(Brick b) {
        Rectangle ballBoundingRectangle = new Rectangle(getX() - SIZE / 2, getY() - SIZE / 2, SIZE, SIZE);
        Rectangle brickLeft = new Rectangle(b.getX() - Brick.SIZE_X / 2,
                b.getY() - Brick.SIZE_Y / 2, 1, Brick.SIZE_Y);
        Rectangle brickRight = new Rectangle(b.getX() + Brick.SIZE_X / 2,
                b.getY() - Brick.SIZE_Y / 2, 1, Brick.SIZE_Y);
        return ballBoundingRectangle.intersects(brickLeft) || ballBoundingRectangle.intersects(brickRight);
    }

    // MODIFIES: this
    // EFFECTS: ball is constrained to bounce off top and vertical walls
    // from PaddleBall
    protected void handleBoundary() {
        if (getX() - SIZE / 2 < 0) { // touch left
            xcoord = SIZE / 2;
            dx *= -1;
            System.out.println("Bounced off wall!");
        } else if (getX() + SIZE / 2 > BrickBreakGame.WIDTH) { // touch right
            xcoord = BrickBreakGame.WIDTH - SIZE / 2;
            dx *= -1;
            System.out.println("Bounced off wall!");
        } else if (getY() - SIZE / 2 < 0) { // touch top
            ycoord = SIZE / 2;
            dy *= -1;
            System.out.println("Bounced off wall!");
        }
    }

    public int getX() {
        return xcoord;
    }

    public int getY() {
        return ycoord;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
