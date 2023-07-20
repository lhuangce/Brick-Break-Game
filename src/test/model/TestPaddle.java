package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Unit tests for Paddle class
 */
public class TestPaddle {
    private Paddle testPaddle;

    @BeforeEach
    void runBefore() {
        testPaddle = new Paddle();
    }

    @Test
    void testConstructor() {
        assertEquals(Paddle.X_POS, testPaddle.getX());
    }

    @Test
    void testMoveLeft() {
        testPaddle.moveLeft();
        assertEquals(Paddle.X_POS - Paddle.STEP_SIZE, testPaddle.getX());
        testPaddle.moveLeft();
        testPaddle.moveLeft();
        assertEquals(Paddle.X_POS - 3 * Paddle.STEP_SIZE, testPaddle.getX());
    }

    @Test
    void testMoveRight() {
        testPaddle.moveRight();
        assertEquals(Paddle.X_POS + Paddle.STEP_SIZE, testPaddle.getX());
        testPaddle.moveRight();
        testPaddle.moveRight();
        assertEquals(Paddle.X_POS + 3 * Paddle.STEP_SIZE, testPaddle.getX());
    }
}
