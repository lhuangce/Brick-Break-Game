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
    void testBrickHitHorizontal() {
        BrickBreakGame g = new BrickBreakGame(1);
        Ball b = new Ball(BrickBreakGame.XLOC_0, BrickBreakGame.YLOC_0 + Brick.SIZE_Y / 2 + Ball.SIZE / 2,
                -Ball.defaultDx, -Ball.defaultDy);
        Brick current = g.getBricks().get(0);
        g.addBall(b);

        g.update();
        assertEquals(Ball.defaultDy, g.getBall().getDy());
        assertEquals(0, g.getBricks().size());
        assertFalse(g.getBricks().contains(current));
    }

    @Test
    void testBrickHitVertical() {
        BrickBreakGame g = new BrickBreakGame(1);
        Ball b = new Ball(BrickBreakGame.XLOC_0 + Brick.SIZE_X / 2 + Ball.SIZE / 2, BrickBreakGame.YLOC_0,
                -Ball.defaultDx, -Ball.defaultDy);
        Brick current = g.getBricks().get(0);
        g.addBall(b);

        g.update();
        assertEquals(Ball.defaultDx, g.getBall().getDx());
        assertEquals(0, g.getBricks().size());
        assertFalse(g.getBricks().contains(current));
    }

    @Test
    void testOutOfBoundsGameOver() {
        Paddle p = new Paddle(Paddle.SIZE_X);
        testGame.addPaddle(p);
        for (int n = 0; n < BrickBreakGame.HEIGHT; n++) {
            testGame.update();
        }
        assertTrue(testGame.gameOver());
    }

    @Test
    void testOutOfBricksGameOver() {
        BrickBreakGame g = new BrickBreakGame();
        Ball b = new Ball(Ball.SIZE, Ball.SIZE);
        g.addBall(b);
        g.checkGameOver();
        assertTrue(g.gameOver());
    }

    @Test
    void testKeyEvent() throws GameResumeException {
        testGame.gameAction(KeyEvent.VK_LEFT);
        assertEquals(Paddle.X_POS - Paddle.STEP_SIZE, p.getX());

        testGame.gameAction(KeyEvent.VK_RIGHT);
        testGame.gameAction(KeyEvent.VK_RIGHT);
        assertEquals(Paddle.X_POS + Paddle.STEP_SIZE, p.getX());

        try {
            testGame.gameAction(KeyEvent.VK_SPACE);
            assertTrue(testGame.isPaused());
        } catch (GameResumeException e) {
            fail("Should not have caught exception");
        }

        try {
            testGame.gameAction(KeyEvent.VK_SPACE);
            assertFalse(testGame.isPaused());
            fail("Did not catch exception");
        } catch (GameResumeException e) {
            // expected
        }

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
