package model;

import model.exceptions.GameResumeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the state of a brick break game. Based on CPSC 210 Lab 3 PaddleBall Project.
 */
public class BrickBreakGame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAX_BRICKS = 30;

    protected static final int BALL_Y0 = BrickBreakGame.HEIGHT / 2;
    protected static final int BRICK_X0 = Brick.SIZE_X / 2;
    protected static final int BRICK_Y0 = Brick.SIZE_Y * 3 / 2;

    private static final Random RND = new Random();

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private int score;
    private boolean paused;
    private boolean isGameOver;

    // REQUIRES: brickCount is on [1, MAX_BRICKS]
    // EFFECTS: creates new game in default state with given number of bricks; DEFAULT CONSTRUCTOR
    public BrickBreakGame(int brickCount) {
        setUp(brickCount);
    }

    // EFFECTS: creates new empty game with statuses of game paused and game not over
    public BrickBreakGame() {
        ball = null;
        paddle = null;
        bricks = new ArrayList<>();
        score = 0;
        paused = true;
        isGameOver = false;
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
    // EFFECTS: creates new game with ball at position (random x, yStart), given number of bricks placed in rows of up
    // to 10 in the top half of the screen, with statuses of game not paused and game not over
    private void setUp(int brickCount) {
        ball = new Ball(RND.nextInt(WIDTH / 3) + WIDTH / 3, BALL_Y0);
        paddle = new Paddle();
        bricks = new ArrayList<>();
        score = 0;
        paused = false;
        isGameOver = false;

        for (int count = 0; count < brickCount; count++) {
            if (count < 10) {
                bricks.add(new Brick(BRICK_X0 + Brick.SIZE_X * count, BRICK_Y0));
            } else if (count < 20) {
                bricks.add(new Brick(BRICK_X0 + Brick.SIZE_X * (count - 10), BRICK_Y0 + Brick.SIZE_Y));
            } else {
                bricks.add(new Brick(BRICK_X0 + Brick.SIZE_X * (count - 20), BRICK_Y0 + 2 * Brick.SIZE_Y));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given ball to game
    public void addBall(Ball b) {
        ball = b;
    }

    // MODIFIES: this
    // EFFECTS: adds given paddle to game
    public void addPaddle(Paddle p) {
        paddle = p;
    }

    // MODIFIES: this
    // EFFECTS: adds given brick to game bricks
    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    // MODIFIES: this
    // EFFECTS: sets score to given score
    public void setScore(int score) {
        this.score = score;
    }

    // MODIFIES: this
    // EFFECTS: bounces ball if it collides with an object (paddle or brick), removes brick if hit
    private void checkCollision() {
        if (ball.collideWithPaddle(paddle)) {
            ball.bounceOffPaddle();
            ball.move();
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick current = bricks.get(i);
            if (ball.collideWithBrickWidth(current)) {
                ball.bounceOffHorizontal();
                bricks.remove(current);
                score++;
            } else if (ball.collideWithBrickHeight(current)) {
                ball.bounceOffVertical();
                bricks.remove(current);
                score++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if ball has hit ground or all bricks are cleared, game is marked as over
    protected void checkGameOver() {
        if (ball.getY() > HEIGHT || bricks.isEmpty()) {
            isGameOver = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: keeps current ball, paddle, and brick states when paused, resumes on key press; exits on key press
    public void gameAction(int keyCode) throws GameResumeException {
        if (keyCode == KeyEvent.VK_SPACE && !paused) {
            paused = true;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            paused = false;
            throw new GameResumeException();
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

    // EFFECTS: saves game data as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ballX", ball.getX());
        json.put("ballY", ball.getY());
        json.put("ballDx", ball.getDx());
        json.put("ballDy", ball.getDy());
        json.put("paddleX", paddle.getX());
        json.put("bricks", bricksToJson());
        json.put("score", score);
        return json;
    }

    // EFFECTS: returns bricks in this game as a JSON array
    private JSONArray bricksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Brick b : bricks) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
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

    public int getScore() {
        return score;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean gameOver() {
        return isGameOver;
    }
}
