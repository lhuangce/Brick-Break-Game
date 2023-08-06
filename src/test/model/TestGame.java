package model;

import model.exceptions.GameResumeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BrickBreakGame class
 */
public class TestGame {
    // TODO: add tests for new methods
    BrickBreakGame testGame;
    Ball b;
    Paddle p;
    ArrayList<Brick> bricks;

    @BeforeEach
    void runBefore() {
        testGame = new BrickBreakGame(10);
        b = testGame.getBall();
        p = testGame.getPaddle();
        bricks = testGame.getBricks();
    }

    @Test
    void testConstructor() {
        assertEquals(BrickBreakGame.Y0, b.getY());
        assertEquals(Paddle.X_POS, p.getX());
        assertEquals(10, bricks.size());
        assertFalse(testGame.isPaused());
        assertFalse(testGame.gameOver());

        BrickBreakGame g = new BrickBreakGame(9);
        bricks = g.getBricks();
        assertEquals(9, bricks.size());

        g = new BrickBreakGame(11);
        bricks = g.getBricks();
        assertEquals(11, bricks.size());

        g = new BrickBreakGame(19);
        bricks = g.getBricks();
        assertEquals(19, bricks.size());

        g = new BrickBreakGame(20);
        bricks = g.getBricks();
        assertEquals(20, bricks.size());

        g = new BrickBreakGame(21);
        bricks = g.getBricks();
        assertEquals(21, bricks.size());
    }

    @Test
    void testUpdate() {
        int x0 = b.getX();

        assertEquals(x0, b.getX());
        assertEquals(BrickBreakGame.Y0, b.getY());

        testGame.update();
        assertEquals(x0 + b.getDx(), b.getX());
        assertEquals(BrickBreakGame.Y0 + b.getDy(), b.getY());

        testGame.update();
        assertEquals(x0 + 2 * b.getDx(), b.getX());
        assertEquals(BrickBreakGame.Y0 + 2 * b.getDy(), b.getY());
    }

    @Test
    void testPaddleBounce() {
        b = new Ball(BrickBreakGame.WIDTH / 2, Paddle.Y_POS - Ball.SIZE / 2 - Ball.defaultDy + 1, Ball.defaultDx, Ball.defaultDy);
        testGame.addBall(b);
        testGame.update();
        assertEquals(-Ball.defaultDy, b.getDy());
    }

    @Test
    void testBrickHitGameOver() {
        // test brick hit (horizontal), then game over
        b.bounceOffHorizontal();
        for (int n = 0; n < 2 * BrickBreakGame.HEIGHT; n++) {
            testGame.update();
        }
        assertEquals(Ball.defaultDy, b.getDy());
        assertEquals(9, bricks.size());
        assertTrue(testGame.gameOver());
    }

    @Test
    void testKeyEvent() throws GameResumeException {
        testGame.gameAction(KeyEvent.VK_LEFT);
        assertEquals(Paddle.X_POS - Paddle.STEP_SIZE, p.getX());

        testGame.gameAction(KeyEvent.VK_RIGHT);
        testGame.gameAction(KeyEvent.VK_RIGHT);
        assertEquals(Paddle.X_POS + Paddle.STEP_SIZE, p.getX());

        testGame.gameAction(KeyEvent.VK_SPACE);
        assertTrue(testGame.isPaused());

        testGame.gameAction(KeyEvent.VK_SPACE);
        assertFalse(testGame.isPaused());

        testGame.gameAction(KeyEvent.VK_E);
        assertEquals(Paddle.X_POS + Paddle.STEP_SIZE, p.getX());

        for (int i = 0; i < BrickBreakGame.HEIGHT; i++) {
            testGame.update();
        }
        BrickBreakGame g = new BrickBreakGame(30);
        b = g.getBall();
        p = g.getPaddle();
        bricks = g.getBricks();

        assertEquals(BrickBreakGame.Y0, b.getY());
        assertEquals(Paddle.X_POS, p.getX());
        assertEquals(30, bricks.size());
        assertFalse(g.isPaused());
        assertFalse(g.gameOver());
    }
}
