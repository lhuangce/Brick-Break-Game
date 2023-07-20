package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Unit test for Brick class
 */
public class TestBrick {
    private static final int XLOC = BrickBreakGame.WIDTH / 2;
    private static final int YLOC = BrickBreakGame.HEIGHT / 2;

    private Brick testBrick;


    @BeforeEach
    void runBefore() {
        testBrick = new Brick (XLOC, YLOC);
    }

    @Test
    void testConstructor() {
        assertEquals(XLOC, testBrick.getX());
        assertEquals(YLOC, testBrick.getY());
    }
}
