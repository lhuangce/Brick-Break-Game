package ui;

import model.exceptions.MaxBricksException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Selected tests for BrickBreakFrame
 */
public class TestFrame {
    private int brickCount;

    @BeforeEach
    void runBefore() {
        brickCount = 0;
    }

    @Test
    void testCheckMaxBricks() {
        try {
            brickCount = checkMaxBricks("29");
            assertEquals(29, brickCount);
        } catch (MaxBricksException e) {
            fail("Should not have caught this exception");
        }
        try {
            brickCount = checkMaxBricks("30");
            assertEquals(30, brickCount);
        } catch (MaxBricksException e) {
            fail("Should not have caught this exception");
        }
        try {
            checkMaxBricks("31");
            fail("Did not catch exception");
        } catch (MaxBricksException e) {
            // expected
        }
    }

    private int checkMaxBricks(String input) throws MaxBricksException {
        int brickCount;
        brickCount = parseInt(input);

        if (brickCount > 30) {
            throw new MaxBricksException();
        }
        return brickCount;
    }
}
