package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BrickBreakGame class
 */
public class TestGame {
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
        int landingX = b.getX() + (Paddle.Y_POS - b.getY()); // ball x plus distance to cover, assuming dx = dy
        int numMoves = abs(landingX - p.getX()) / Paddle.STEP_SIZE;

        for (int n = 0; n < numMoves; n++) {
            if (p.getX() < landingX) {
                p.moveRight();
            } else {
                p.moveLeft();
            }
        }

        int numUpdates = (BrickBreakGame.HEIGHT - b.getY()) / b.getDy();
        for (int n = 0; n < numUpdates; n++) {
            testGame.update();
        }
        assertEquals(-2, b.getDy());
    }

    @Test
    void testBrickHitGameOver() {
        // test brick hit (horizontal), then game over
        b.bounceOffHorizontal();
        for (int n = 0; n < 2 * BrickBreakGame.HEIGHT; n++) {
            testGame.update();
        }
        assertEquals(2, b.getDy());
        assertEquals(9, bricks.size());
        assertTrue(testGame.gameOver());
    }

    @Test
    void testKeyEvent() {
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

        testGame.restart(KeyEvent.VK_R, 30);
        assertEquals(Paddle.X_POS + Paddle.STEP_SIZE, p.getX());

        for (int i = 0; i < BrickBreakGame.HEIGHT; i++) {
            testGame.update();
        }
        testGame.restart(KeyEvent.VK_R, 30);
        b = testGame.getBall();
        p = testGame.getPaddle();
        bricks = testGame.getBricks();

        assertEquals(BrickBreakGame.Y0, b.getY());
        assertEquals(Paddle.X_POS, p.getX());
        assertEquals(30, bricks.size());
        assertFalse(testGame.isPaused());
        assertFalse(testGame.gameOver());
    }
}
