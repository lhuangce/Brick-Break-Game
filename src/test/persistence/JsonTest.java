package persistence;

import model.Brick;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBrick(int x, int y, Brick b) {
        assertEquals(x, b.getX());
        assertEquals(y, b.getY());
    }
}
