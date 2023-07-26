package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Ball class
 */

public class TestBall {
    Ball testBall;
    int testX = BrickBreakGame.WIDTH / 2;
    int Y0 = BrickBreakGame.HEIGHT - 5 * Ball.SIZE;
    int thicknessX = Brick.SIZE_X / 2;
    int thicknessY = Brick.SIZE_Y / 2;
    int radius = Ball.SIZE / 2;

    @BeforeEach
    void runBefore() {
        testBall = new Ball(testX, Y0);
    }

    @Test
    void testConstructor() {
        assertEquals(testX, testBall.getX());
        assertEquals(Y0, testBall.getY());
        assertEquals(2, testBall.getDy());
        assertEquals(2, testBall.getDy());
    }

    @Test
    void testMove() {
        testBall.move();
        assertEquals(testX + 2, testBall.getX());
        assertEquals(Y0 + 2, testBall.getY());
        testBall.move();
        testBall.move();
        assertEquals(testX + 6, testBall.getX());
        assertEquals(Y0 + 6, testBall.getY());
    }

    @Test
    void testCollideWithPaddle() {
        Paddle p = new Paddle(Paddle.X_POS);

        Ball b = new Ball(0, 0);
        assertFalse(b.collideWithPaddle(p));

        b = new Ball(p.getX(), Paddle.Y_POS);
        assertTrue(b.collideWithPaddle(p));

        b = new Ball(p.getX() + Paddle.SIZE_X / 2 + Ball.SIZE / 2 - 1, Paddle.Y_POS);
        assertTrue(b.collideWithPaddle(p));

        b = new Ball(p.getX() + Paddle.SIZE_X / 2 + radius, Paddle.Y_POS);
        assertFalse(b.collideWithPaddle(p));

        b = new Ball(p.getX() - Paddle.SIZE_X / 2 - radius + 1, Paddle.Y_POS);
        assertTrue(b.collideWithPaddle(p));

        b = new Ball(p.getX() - Paddle.SIZE_X / 2 - radius, Paddle.Y_POS);
        assertFalse(b.collideWithPaddle(p));

        b = new Ball(p.getX(), Paddle.Y_POS - Paddle.SIZE_Y / 2 - radius + 1);
        assertTrue(b.collideWithPaddle(p));

        b = new Ball(p.getX(), Paddle.Y_POS - Paddle.SIZE_Y / 2 - radius);
        assertFalse(b.collideWithPaddle(p));
    }

    @Test
    void testCollideWithBrickWidth() {
        Brick brick = new Brick(BrickBreakGame.WIDTH / 2, BrickBreakGame.HEIGHT / 2);
        Ball b = new Ball(0, 0);

        assertFalse(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() - thicknessY - radius - 1);
        assertFalse(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() - thicknessY - radius);
        assertFalse(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() - thicknessY - radius + 1);
        assertTrue(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() + thicknessY + radius + 1);
        assertFalse(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() + thicknessY + radius);
        assertTrue(b.collideWithBrickWidth(brick));

        b = new Ball(brick.getX(), brick.getY() + thicknessY + radius - 1);
        assertTrue(b.collideWithBrickWidth(brick));
    }

    @Test
    void testCollideWithBrickHeight() {
        Brick brick = new Brick(BrickBreakGame.WIDTH / 2, BrickBreakGame.HEIGHT / 2);
        Ball b = new Ball(0, 0);

        assertFalse(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() - thicknessX - radius - 1, brick.getY());
        assertFalse(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() - thicknessX - radius, brick.getY());
        assertFalse(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() - thicknessX - radius + 1, brick.getY());
        assertTrue(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() + thicknessX + radius + 1, brick.getY());
        assertFalse(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() + thicknessX + radius, brick.getY());
        assertTrue(b.collideWithBrickHeight(brick));

        b = new Ball(brick.getX() + thicknessX + radius - 1, brick.getY());
        assertTrue(b.collideWithBrickHeight(brick));
    }

    @Test
    void testHandleBoundary() {
        Ball b = new Ball(BrickBreakGame.WIDTH - radius - 1, Y0);

        b.handleBoundary();
        assertEquals(BrickBreakGame.WIDTH - radius - 1, b.getX());
        assertEquals(2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(BrickBreakGame.WIDTH - radius, Y0);
        b.handleBoundary();
        assertEquals(BrickBreakGame.WIDTH - radius, b.getX());
        assertEquals(2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(BrickBreakGame.WIDTH - radius + 1, Y0);
        b.handleBoundary();
        assertEquals(BrickBreakGame.WIDTH - radius, b.getX());
        assertEquals(-2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(Ball.SIZE / 2 + 1, Y0);
        b.bounceOffVertical();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2 + 1, b.getX());
        assertEquals(-2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(Ball.SIZE / 2, Y0);
        b.bounceOffVertical();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2, b.getX());
        assertEquals(-2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(Ball.SIZE / 2 - 1, Y0);
        b.bounceOffVertical();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2, b.getX());
        assertEquals(2, b.getDx());
        assertEquals(2, b.getDy());

        b = new Ball(BrickBreakGame.WIDTH / 2, Ball.SIZE / 2 + 1);
        b.bounceOffHorizontal();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2 + 1, b.getY());
        assertEquals(2, b.getDx());
        assertEquals(-2, b.getDy());

        b = new Ball(BrickBreakGame.WIDTH / 2, Ball.SIZE / 2);
        b.bounceOffHorizontal();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2, b.getY());
        assertEquals(2, b.getDx());
        assertEquals(-2, b.getDy());

        b = new Ball(BrickBreakGame.WIDTH / 2, Ball.SIZE / 2 - 1);
        b.bounceOffHorizontal();
        b.handleBoundary();
        assertEquals(Ball.SIZE / 2, b.getY());
        assertEquals(2, b.getDx());
        assertEquals(2, b.getDy());
    }
}
