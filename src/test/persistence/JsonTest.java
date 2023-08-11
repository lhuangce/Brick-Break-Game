package persistence;

import model.Brick;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Superclass for JSON reader and writer test classes. Based on JsonSerializationDemo project: WorkroomApp
 * (<a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git">link</a>).
 */
public class JsonTest {
    protected void checkBrick(int x, int y, Brick b) {
        assertEquals(x, b.getX());
        assertEquals(y, b.getY());
    }
}
