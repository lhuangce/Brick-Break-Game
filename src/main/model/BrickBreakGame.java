package model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/*
 * Represents the state of a brick break game.
 */
public class BrickBreakGame {
    protected static int WIDTH = 800;
    protected static int HEIGHT = 600;
    protected static int Y0 = BrickBreakGame.HEIGHT - 6 * Ball.SIZE;

    private static final Random RND = new Random();
    private static final int XLOC_0 = Brick.SIZE_X / 2;
    private static final int YLOC_0 = Brick.SIZE_Y * 3 / 2;

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private boolean paused;
    private boolean isGameOver;

    // REQUIRES: brickCount is on [0, 30]
    // EFFECTS: creates new game with ball at position (random x, yStart), given number of bricks in rows of up to 10 in
    // the top half of the screen, with statuses of game not paused and game not over
    public BrickBreakGame(int brickCount) {
        setUp(brickCount);
    }

    // MODIFIES: this
    // EFFECTS: updates ball, bricks, and game over status
    public void update() {
        ball.move();

        checkCollision();
        checkGameOver();
    }

    // Sets default game state with given number of bricks
    // MODIFIES: this
    // EFFECTS: creates new ball, sets paddle to default position, resets bricks in position
    private void setUp(int brickCount) {
        ball = new Ball(RND.nextInt(WIDTH / 2) + WIDTH / 4, Y0);
        paddle = new Paddle();
        bricks = new ArrayList<>();
        paused = false;
        isGameOver = false;

        for (int count = 0; count < brickCount; count++) {
            if (count < 10) {
                bricks.add(new Brick(XLOC_0 + Brick.SIZE_X * count, YLOC_0));
            } else if (count >= 10 && count < 20) {
                bricks.add(new Brick(XLOC_0 + Brick.SIZE_X * (count - 10), YLOC_0 * 2));
            } else if (count >= 20) {
                bricks.add(new Brick(XLOC_0 + Brick.SIZE_X * (count - 20), YLOC_0 * 3));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: bounces ball if it collides with an object (paddle or brick), removes brick if hit
    private void checkCollision() {
        if (ball.collideWithPaddle(paddle)) {
            ball.bounceOffHorizontal();
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick current = bricks.get(i);
            if (ball.collideWithBrickWidth(current)) {
                ball.bounceOffHorizontal();
                bricks.remove(current);
            } else if (ball.collideWithBrickHeight(current)) {
                ball.bounceOffVertical();
                bricks.remove(current);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  if ball has hit ground, game is marked as over
    private void checkGameOver() {
        if (ball.getY() > HEIGHT) {
            isGameOver = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: resets game state on key press when game is over, number of bricks based on integer input
    public void restart(int keyCode, int brickCount) {
        if (keyCode == KeyEvent.VK_R && isGameOver) {
            setUp(brickCount);
        }
    }

    // MODIFIES: this
    // EFFECTS: keeps current ball, paddle, and brick states when paused, resumes on key press; exits on key press
    public void gameAction(int keyCode) {
        BrickBreakGame saveState = new BrickBreakGame(0);
        if (keyCode == KeyEvent.VK_SPACE && !paused) {
            paused = true;
        } else if (keyCode == KeyEvent.VK_SPACE && paused) {
            paused = false;
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            saveState = this;
            System.exit(0);
        } else {
            controlPaddle(keyCode);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates paddle position on key press
    private void controlPaddle(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
        }
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean gameOver() {
        return isGameOver;
    }
}
